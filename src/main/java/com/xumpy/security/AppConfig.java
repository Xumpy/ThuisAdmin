/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security;

import com.xumpy.thuisadmin.model.db.Personen;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
/**
 *
 * @author nicom
 */
@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter{
        
    @Bean
    public Personen persoon(){
        return new Personen();
    }
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Override 
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/finances/**").hasAnyAuthority("USER")
                .antMatchers("/json/**").hasAnyAuthority("USER")
                .antMatchers("/admin/**").hasAnyAuthority("USER")
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/j_spring_security_check")
                .defaultSuccessUrl("/finances/overview", true)
                .failureUrl("/login?error")
                .permitAll()
            .and()
                .logout()
                    .logoutUrl("/j_spring_security_logout")
                    .logoutSuccessUrl("/login");		
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
          .ignoring()
             .antMatchers("/resources/**");
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        UserService userService = new UserService();
        userService.setSessionFactory(sessionFactory);
        userService.setPersonen(persoon());
        
        auth.userDetailsService(userService).passwordEncoder(new Md5PasswordEncoder());
    }
}
