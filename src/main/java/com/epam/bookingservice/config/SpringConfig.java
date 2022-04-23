//package com.epam.bookingservice.config;
//
//import com.epam.bookingservice.util.RestResponseStatusExceptionResolver;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.thymeleaf.spring5.SpringTemplateEngine;
//import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring5.view.ThymeleafViewResolver;
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
//public class SpringConfig implements WebMvcConfigurer {
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
