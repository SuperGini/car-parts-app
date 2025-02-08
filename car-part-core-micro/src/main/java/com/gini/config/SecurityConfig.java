package com.gini.config;

import com.gini.security.filters.XcsrfFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


//https://docs.spring.io/spring-security/reference/6.0/servlet/oauth2/login/advanced.html
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
                        .requestMatchers("/**").authenticated()
                        .requestMatchers("/actuator/health/liveness").permitAll()
                        .anyRequest().authenticated()
                )
//                https://stackoverflow.com/questions/75222930/spring-boot-3-0-2-adds-continue-query-parameter-to-request-url-after-login
                .requestCache(cache -> cache.requestCache(requestCache))
                .oauth2Login(x -> x
                        .defaultSuccessUrl("/main/cars")
                        .userInfoEndpoint(userInfo -> userInfo
                                .oidcUserService(this.oidcUserService()) // was just experimenting with this
                        )


                );




        http.addFilterBefore(new XcsrfFilter(), CsrfFilter.class);


//        var x = new CsrfTokenRequestAttributeHandler();
//        x.setCsrfRequestAttributeName(null);
//
//        http.csrf(csrf -> csrf
//
//                .csrfTokenRepository(customCookieCsrfTokenRepository)
//                .csrfTokenRequestHandler(x)
//        );

        http.csrf(AbstractHttpConfigurer::disable);

        //done automatically by spring
//        http.sessionManagement(s ->
//                s.sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession));


        http.exceptionHandling(ex -> ex.accessDeniedPage("/micro-core/loginx"));


        return http.build();
    }

    //https://docs.spring.io/spring-security/reference/6.0/servlet/oauth2/login/advanced.html
    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        final OidcUserService delegate = new OidcUserService();
        return (userRequest) -> {
            // Delegate to the default implementation for loading a user
            OidcUser oidcUser = delegate.loadUser(userRequest);


//            OAuth2AccessToken accessToken = userRequest.getAccessToken();

            var idTokenAuthorities = new ArrayList<String>(userRequest.getIdToken().getClaim("authorities"))
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());


            // TODO
            // 1) Fetch the authority information from the protected resource using accessToken
            // 2) Map the authority information to one or more GrantedAuthority's and add it to mappedAuthorities

            // 3) Create a copy of oidcUser but use the mappedAuthorities instead
            ClientRegistration.ProviderDetails providerDetails = userRequest.getClientRegistration().getProviderDetails();
            String userNameAttributeName = providerDetails.getUserInfoEndpoint().getUserNameAttributeName();
            if (StringUtils.hasText(userNameAttributeName)) {
                oidcUser = new DefaultOidcUser(idTokenAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo(), userNameAttributeName);
            } else {
                oidcUser = new DefaultOidcUser(idTokenAuthorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
            }

            return oidcUser;
        };
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
