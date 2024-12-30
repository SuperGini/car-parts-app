package com.gini.config;

import com.gini.security.filters.XcsrfFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfiguration;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CookieCsrfTokenRepository customCookieCsrfTokenRepository) throws Exception {

        corsConfiguration.corsConfiguration(http);

        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll()
                )
//                https://stackoverflow.com/questions/75222930/spring-boot-3-0-2-adds-continue-query-parameter-to-request-url-after-login
                .requestCache(cache -> cache.requestCache(requestCache))
                .oauth2Login(x -> x.defaultSuccessUrl("/main/cars"));


        http.addFilterBefore(new XcsrfFilter(), CsrfFilter.class);


        var x = new CsrfTokenRequestAttributeHandler();
        x.setCsrfRequestAttributeName(null);

        http.csrf(csrf -> csrf

                .csrfTokenRepository(customCookieCsrfTokenRepository)
                .csrfTokenRequestHandler(x)
        );

        //done automatically by spring
//        http.sessionManagement(s ->
//                s.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession));


        http.exceptionHandling(ex -> ex.accessDeniedPage("/login"));


        return http.build();
    }

    @Bean
    public CookieCsrfTokenRepository customCookieCsrfTokenRepository() {
        var cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        cookieCsrfTokenRepository
                .setCookieCustomizer(x -> x
                        .httpOnly(true)
                        .secure(true)
                        .domain("localhost")
                        .sameSite("Strict")
                );

        return cookieCsrfTokenRepository;
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
