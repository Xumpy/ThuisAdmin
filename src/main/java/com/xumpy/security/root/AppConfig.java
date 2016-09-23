/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security.root;

import com.xumpy.security.model.UserInfo;
import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter{

    @Bean
    @Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
    public UserInfo userInfo(){
        return new UserInfo();
    }
    
    @Resource(name="userService") 
    UserService userService;
    
    @Override 
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/timesheets/**").hasAnyAuthority("USER")
                .antMatchers("/finances/**").hasAnyAuthority("USER")
                .antMatchers("/json/**").hasAnyAuthority("USER")
                .antMatchers("/admin/**").hasAnyAuthority("USER")
                .antMatchers("/register/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/styles/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                .loginPage("/login")
                .defaultSuccessUrl("/finances/overview", true)
                .failureUrl("/login?error")
                .permitAll()
            .and()
                .logout()
                .permitAll();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new Md5PasswordEncoder());
    }
}
