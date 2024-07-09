package com.example.Application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@EnableMethodSecurity
@Configuration
@EnableConfigurationProperties(value={ApiProperties.class})
public class ApiSecurityConfiguration{
    @Value("${api.security.ignored}" )
    private String[] ignored;

    @Autowired
    private ApiProperties props;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return web ->{
            for (String location :props.getIgnored()){
                web.ignoring().requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,location));
            }
        };
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(Customizer.withDefaults());
        http.authorizeHttpRequests(config -> config.anyRequest().authenticated());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
