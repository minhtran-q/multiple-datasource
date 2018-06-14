/*
 * Class: FirstDataSource
 *
 * Created on Feb 26, 2018
 *
 * (c) Copyright Swiss Post Solution, unpublished work
 * All use, disclosure, and/or reproduction of this material is prohibited
 * unless authorized in writing.  All Rights Reserved.
 * Rights in this program belong to:
 * Swiss Post Solution.
 * Floor 4-5-8, ICT Tower, Quang Trung Software City
 */
package com.sps.vn.datasource;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.tomcat.jdbc.pool.jmx.ConnectionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sps.vn.common.PropertiyConfiguration.FirstProperties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages="com.sps.vn.repository.first",
entityManagerFactoryRef="fEntityManager",
transactionManagerRef="fTransactionManager")
class FirstDataSource extends AbstractDataSourceConfiguration{
	
	@Autowired
	private FirstProperties firstProperties;
    
	@Primary
    @Override
    @Bean(name="fDataSource")
    @ConfigurationProperties(prefix= "app.custom.datasource.first")
    protected DataSource dataSource() {
		DataSource dataSource= DataSourceBuilder.create().build();
        return dataSource;
    }    

	@Primary
    @Override
    @Bean("fTransactionManager")
    protected PlatformTransactionManager transactionManager(EntityManagerFactory fEntityManager) {
        return this.getTransactionManager(fEntityManager);
    }

	
	@Primary
    @Override
    @Bean("fEntityManager")
    protected EntityManagerFactory entityManagerFactory(@Qualifier("fDataSource")DataSource fDataSource) {
        return this.getEntityManagerFactoryBean(fDataSource);
    }

    @Override
	protected ConnectionPool jmxDataSource(DataSource dataSource) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    protected String getPackagesToScan() {
        return "com.sps.vn.entities.first";
    }

    @Override
    protected Properties getProperties() {
    	Properties properties= new Properties();
    	properties.put("hibernate.dialect", firstProperties.getDatabasePlatform());
    	properties.put("hibernate.hbm2ddl.auto", firstProperties.getDdlAuto());
    	properties.put("hibernate.show_sql", firstProperties.getShowSql());
        return properties;
    }

    @Override
    protected String getUnitName() {
        return "firstUnit";
    }
}
