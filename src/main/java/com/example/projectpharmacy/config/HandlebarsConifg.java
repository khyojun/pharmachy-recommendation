package com.example.projectpharmacy.config;

import com.github.jknack.handlebars.springmvc.HandlebarsViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HandlebarsConifg {


    @Bean
    public HandlebarsViewResolver viewResolver() throws Exception{

        HandlebarsViewResolver viewResolver = new HandlebarsViewResolver();
        viewResolver.setOrder(1);
        viewResolver.setPrefix("classpath:/templates"); // 기본값이 classpath:/templates 라고 되어 있긴 함
        viewResolver.setSuffix(".hbs"); // 기본값이 .hbs라고 되어 있긴 함
        viewResolver.setCache(false); // 캐시 사용 여부
        viewResolver.setBindI18nToMessageSource(true); // true라면 ResourceBundle 대신 MessageSource를 사용한다.

        return viewResolver;
    }
}
