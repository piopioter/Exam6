package org.example.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
public class JpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean creatEmf(JpaVendorAdapter adapter, DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setBeanName("springPersistenceUnit");
        emf.setJpaVendorAdapter(adapter);
        emf.setPackagesToScan("org.example.models");
        return emf;
    }
    @Bean
    public JpaVendorAdapter createAdapter() {
        HibernateJpaVendorAdapter jva = new HibernateJpaVendorAdapter();
        jva.setGenerateDdl(true);
        jva.setShowSql(true);
        jva.setDatabase(Database.H2);
        return jva;
    }
    @Bean
    public DataSource createSource() {
        BasicDataSource data = new BasicDataSource();
        data.setUrl("jdbc:h2:mem:testdb");
        data.setUsername("sa");
        data.setPassword("");
        data.setDriverClassName("org.h2.Driver");
        return data;
    }


}
