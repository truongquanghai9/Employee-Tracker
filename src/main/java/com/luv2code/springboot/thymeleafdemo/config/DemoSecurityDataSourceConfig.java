package com.luv2code.springboot.thymeleafdemo.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages={"${spring.data.jpa.repository.packages.security}"},
						entityManagerFactoryRef= "securityEntityManagerFactory",
						transactionManagerRef="securityTransactionManager")
public class DemoSecurityDataSourceConfig {
	
	
	@Bean(name="securityDataSource")
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource securityDataSource() {
		return DataSourceBuilder.create().build();
	}
	
	@Bean(name="securityEntityManagerFactory")
	@ConfigurationProperties(prefix="spring.data.jpa.entity.packages-to-scan.security")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder, @Qualifier("securityDataSource") DataSource securityDataSource) {
		
		return builder
				.dataSource(securityDataSource)
				.packages("com.luv2code.springboot.thymeleafdemo.entity.security")
				.persistenceUnit("User")
				.persistenceUnit("Role")
				.build();
	}
	@Bean(name="securityTransactionManager")
	public PlatformTransactionManager transactionManager(@Qualifier("securityEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
}
