package com.ljx.goods.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {
//    @Bean
//    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
//        return new ConfigurationCustomizer() {
//            @Override
//            public void customize(MybatisConfiguration configuration) {
//                configuration.setObjectWrapperFactory(new MybatisMapWrapperFactory());
//            }
//        };
//    }

    //mybatis-plus的分页拦截器
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return mybatisPlusInterceptor;
    }

}
