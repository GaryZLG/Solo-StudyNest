package com.garyproject.mooc.interception;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConf implements WebMvcConfigurer {

  @Autowired
  private AuthActionInterceptor authActionInterceptor;

  @Autowired
  private AuthInterceptor authInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor).excludePathPatterns("/static").addPathPatterns("/**");
    registry.addInterceptor(authActionInterceptor)
            .addPathPatterns("/accounts/profile")
            .addPathPatterns("/course/learn")
            .addPathPatterns("/course/learn/comment/commentPublish")
            .addPathPatterns("/accounts/myCourse");
    WebMvcConfigurer.super.addInterceptors(registry);
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    WebMvcConfigurer.super.configureMessageConverters(converters);
  }
}
