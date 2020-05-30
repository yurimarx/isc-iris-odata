package com.intersystems.iris.odata.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.intersystems.iris.odata.web.IRISODataServlet;

@Configuration
public class SpringRegistrationBeanServlet {

    @Bean
    public ServletRegistrationBean<IRISODataServlet> genericCustomServlet() {
        ServletRegistrationBean<IRISODataServlet> bean = new ServletRegistrationBean<>(new IRISODataServlet(), "/odata.svc/*");
        bean.setLoadOnStartup(1);
        return bean;
    }
}