/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security;

import com.xumpy.thuisadmin.model.view.OverzichtGroepBedragenTotal;
import java.text.SimpleDateFormat;
import java.util.Properties;
import javax.enterprise.context.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 *
 * @author nicom
 */
@EnableWebMvc
@Configuration
@ComponentScan({ "com.xumpy.thuisadmin.*" })
@EnableTransactionManagement
public class DispatcherConfig extends WebMvcConfigurerAdapter{
 
    @Bean
    public InternalResourceViewResolver viewResolver() {
            InternalResourceViewResolver viewResolver 
                      = new InternalResourceViewResolver();
            viewResolver.setViewClass(JstlView.class);
            viewResolver.setPrefix("/WEB-INF/jsp/");
            viewResolver.setSuffix(".jsp");
            return viewResolver;
    }
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
    }
    
    @Bean
    public SimpleUrlHandlerMapping urlMapping(){
        SimpleUrlHandlerMapping urlMapping = new SimpleUrlHandlerMapping();
        urlMapping.setMappings(urlMappingProperties());
        
        return urlMapping;
    }
    
    Properties urlMappingProperties() {
        return new Properties() {
            {
                setProperty("login", "loginController");
            }
        };
    }
    
    @Bean(name = "loginController")
    public ParameterizableViewController indexController(){
        ParameterizableViewController parameterizableViewController = new ParameterizableViewController();
        parameterizableViewController.setViewName("login");
        return parameterizableViewController;
    }
    
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        
        multipartResolver.setMaxUploadSize(5000000);
        
        return multipartResolver;
    }

    @Bean
    public OverzichtGroepBedragenTotal overzichtGroepBedragenTotal(){
        return new OverzichtGroepBedragenTotal();
    }
    
    @Bean
    public SimpleDateFormat simpleDateFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }
}
