package com.gini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.oauth2Login(x -> x
                .defaultSuccessUrl("/search/parts")
                .loginPage("/loginx")
        );

        http.authorizeHttpRequests(request -> request
                .requestMatchers("/test").permitAll()
                .requestMatchers("/loginx").permitAll()
                .requestMatchers("/login.css").permitAll()
                .anyRequest().authenticated()

        );

        // https://stackoverflow.com/questions/75222930/spring-boot-3-0-2-adds-continue-query-parameter-to-request-url-after-login
//        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
//        requestCache.setMatchingRequestParameterName(null);
//
//        http
//            .requestCache(cache -> cache.requestCache(requestCache));

//        http
//                // Redirect to the login page when not authenticated from the
//                // authorization endpoint
//                //we can use this or the .loginPage(...) from line 23
//                 .exceptionHandling(exceptions -> exceptions
//                        .defaultAuthenticationEntryPointFor(
//                                new LoginUrlAuthenticationEntryPoint("/loginx"),
//                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
//                        )
//                );

        http.csrf(x -> x.disable());

        http.headers(
                headers -> headers.xssProtection(h -> h.headerValue(ENABLED_MODE_BLOCK))
        );

        return http.build();

    }


}
