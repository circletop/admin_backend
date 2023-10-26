package com.example.demo.config;

import com.example.demo.config.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    /***
     * addPathPatterns("/**"):拦截所有请求
     * excludePathPatterns： 不拦截的请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求， 通过判断token是否合法来决定是否需要登陆
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login", "/**/register")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/webjars/**");
    }

    /**
     * 配置静态资源访问拦截
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
