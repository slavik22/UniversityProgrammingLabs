package com.bozzaccio.twitterclone.config;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;

@Configuration
public class H2Config {

    @Value("${spring.datasource.h2.console.path}")
    private String path;

    @Bean()
    @Profile("dev")
    ServletRegistrationBean<WebServlet> H2BeanConfig() {

        Assert.notNull(path, "Application config Error: Required h2 path not null");

        ServletRegistrationBean<WebServlet> registrationBean = new ServletRegistrationBean<>(new WebServlet());
        registrationBean.addUrlMappings(path + "/*");
        return registrationBean;
    }
}
