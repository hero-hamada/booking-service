//package com.epam.bookingservice.config;
//
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * Core Java-based Spring configuration class.
// *
// * @author Merey Zhanakhmetova
// * @version 1.0
// */
//
//@Configuration
////@ComponentScan(basePackages = {"com.epam.bookingservice"})
////@EnableWebMvc
////@EnableJpaRepositories(basePackages="com.epam.bookingservice.dao", entityManagerFactoryRef="sessionFactory")
////@ImportResource("classpath:applicationContext.xml")
//@EnableJpaRepositories(basePackages="com.epam.bookingservice.dao")
//@EntityScan
//public class SpringConfig implements WebMvcConfigurer {
//
//    @Bean(name="entityManagerFactory")
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//
//        return sessionFactory;
//    }
//
////
////    private final ApplicationContext applicationContext;
////
////    @Autowired
////    public SpringConfig(ApplicationContext applicationContext) {
////        this.applicationContext = applicationContext;
////    }
////
////    @Bean
////    public SpringResourceTemplateResolver templateResolver() {
////        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
////        templateResolver.setApplicationContext(applicationContext);
////        templateResolver.setPrefix("/WEB-INF/views/");
////        templateResolver.setSuffix(".html");
////        return templateResolver;
////    }
////
////    @Bean
////    public SpringTemplateEngine templateEngine() {
////        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
////        templateEngine.setTemplateResolver(templateResolver());
////        templateEngine.setEnableSpringELCompiler(true);
////        return templateEngine;
////    }
////
////    @Override
////    public void configureViewResolvers(ViewResolverRegistry registry) {
////        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
////        resolver.setTemplateEngine(templateEngine());
////        registry.viewResolver(resolver);
////    }
////
////    @Bean
////    public RestResponseStatusExceptionResolver exceptionResolver() {
////        return new RestResponseStatusExceptionResolver();
////    }
////
////    @Bean
////    public ObjectMapper objectMapper() {
////        return new ObjectMapper();
////    }
//}
