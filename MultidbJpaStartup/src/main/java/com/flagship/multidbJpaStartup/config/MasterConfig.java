package com.flagship.multidbJpaStartup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "masterEntityManagerFactory",
        transactionManagerRef = "masterTransactionManager",
        basePackages = {"com.flagship.multidbJpaStartup.entity.master"})
public class MasterConfig {

    @Resource
    @Qualifier("masterDataSource")
    private DataSource masterDataSource;

    @Autowired
    private JpaProperties jpaProperties;

    @Bean("masterEntityManager")
    @Primary
    public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
        return entityManagerFactoryBean(builder).getObject().createEntityManager();
    }

    @Bean("masterEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(masterDataSource)
                .properties(getVendorProperties())
                .packages("com.flagship.multidbJpaStartup.entity.master")
                //持久化单元名称，当存在多个EntityManagerFactory时，需要制定此名称
                .persistenceUnit("masterPersistenceUnit")
                .build();
    }

    private Map getVendorProperties() {
        HibernateSettings hibernateSettings = new HibernateSettings();
        //  hibernateSettings.ddlAuto(ddlAuto);
        return jpaProperties.getHibernateProperties(hibernateSettings);
    }

    @Bean("masterTransactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryBean(builder).getObject());
    }
}