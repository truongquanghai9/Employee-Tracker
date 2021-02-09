package com.luv2code.springboot.thymeleafdemo.config;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={"${spring.data.jpa.repository.packages.app}"},
						entityManagerFactoryRef= "appEntityManagerFactory",
						transactionManagerRef="appTransactionManager")
public class DemoAppDataSourceConfig {
	
	@Primary
	@Bean (name="appDataSource")
	@ConfigurationProperties(prefix="app.datasource")
	public DataSource appDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Primary
	@Bean (name="appEntityManagerFactory")
	@ConfigurationProperties(prefix="spring.data.jpa.entity.packages-to-scan.app")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("appDataSource")DataSource appDataSource) {
		
		return builder
				.dataSource(appDataSource)
				.packages("com.luv2code.springboot.thymeleafdemo.entity.app")
				.persistenceUnit("Employee")
				.build();
	}
	
	@Primary
	@Bean(name="appTransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("appEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
}
