package com.test.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.test.test.util.DateFormatter;

@Configuration
public class AppConfig {
    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter();
    }
}
