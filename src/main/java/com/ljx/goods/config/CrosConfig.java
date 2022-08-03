package com.ljx.goods.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//解决跨域问题
@Configuration
public class CrosConfig implements WebMvcConfigurer {

    @Bean
    public WebMvcConfigurer corsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry){
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
                        .maxAge(3600)
                        .allowedHeaders("*");
//                .allowCredentials(true); //是否有请求头
            }
        };
    }


}
