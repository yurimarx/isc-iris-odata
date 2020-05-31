package com.intersystems.iris.odata.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.intersystems.iris.odata.web.IRISODataServlet;

@Configuration
public class SpringRegistrationBeanServlet {

    @Bean
    public ServletRegistrationBean<IRISODataServlet> genericCustomServlet() {
        ServletRegistrationBean<IRISODataServlet> bean = new ServletRegistrationBean<>(new IRISODataServlet(), "/odata.svc/*");
        bean.setLoadOnStartup(1);
        return bean;
    }
    
    @Bean
    public CorsFilter corsFilter() {
        
		CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setMaxAge(3600L);
        config.setExposedHeaders(Arrays.asList("x-auth-token"));
        config.setExposedHeaders(Arrays.asList("xsrf-token"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000", "*"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
        
    }
}