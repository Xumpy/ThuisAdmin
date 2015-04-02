/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xumpy.security;

import com.xumpy.security.servlet.DispatcherConfig;
import com.xumpy.security.root.UserService;
import com.xumpy.security.root.AppConfig;
import com.xumpy.security.root.InitDatabase;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 *
 * @author nicom
 */
public class SpringConfig extends AbstractAnnotationConfigDispatcherServletInitializer{

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { InitDatabase.class, UserService.class, AppConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { DispatcherConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(new RequestContextListener());
        super.onStartup(servletContext);
    }
}
