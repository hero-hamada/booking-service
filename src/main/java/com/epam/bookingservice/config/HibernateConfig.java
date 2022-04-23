//package com.epam.bookingservice.config;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
///**
// * Java-based Hibernate configuration class.
// *
// * @author Merey Zhanakhmetova
// * @version 1.0
// */
//
//@Configuration
//@EnableTransactionManagement
//@PropertySource("classpath:db.properties")
//public class HibernateConfig {
//
//    @Value("${jdbc.driverClassName}")
//    private static String jdbcDriverClassName;
//    @Value("${jdbc.url}")
//    private static String jdbcUrl;
//    @Value("${jdbc.username}")
//    private static String jdbcUsername;
//    @Value("${jdbc.password}")
//    private static String jdbcPassword;
//
//
//    @Bean(name = "sessionFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource());
//        sessionFactory.setPackagesToScan("com.epam.bookingservice.model");
//        sessionFactory.setHibernateProperties(hibernateProperties());
//
//        return sessionFactory;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        BasicDataSource dataSource = new BasicDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUrl("jdbc:postgresql://localhost:5432/bs_db?serverTimezone=UTC");
//        dataSource.setUsername("postgres");
//        dataSource.setPassword("admin");
//        return dataSource;
//    }
//
//    @Bean
//    public HibernateTransactionManager transactionManager(){
//        HibernateTransactionManager txManager = new HibernateTransactionManager();
//        txManager.setSessionFactory(sessionFactory().getObject());
//        return txManager;
//    }
//
//    private final Properties hibernateProperties() {
//        Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
//        hibernateProperties.setProperty("hibernate.show_sql", "true");
//        hibernateProperties.setProperty("hibernate.format_sql", "true");
//        return hibernateProperties;
//    }
//}
//
