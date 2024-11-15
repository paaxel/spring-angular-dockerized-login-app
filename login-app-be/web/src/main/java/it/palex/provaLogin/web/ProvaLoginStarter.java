package it.palex.provaLogin.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
@SpringBootApplication
@ComponentScan({"it.palex.provaLogin.*"})
public class ProvaLoginStarter extends SpringBootServletInitializer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProvaLoginStarter.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        propertySourcesPlaceholderConfigurer.setNullValue("@null");

        return propertySourcesPlaceholderConfigurer;
    }
}