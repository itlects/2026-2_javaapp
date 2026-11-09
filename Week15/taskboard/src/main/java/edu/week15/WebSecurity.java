package edu.week15;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {
    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        // 개인 PC 수업용. 인증 기능은 없으며 CSRF 보호는 유지한다.
        return http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }
}
