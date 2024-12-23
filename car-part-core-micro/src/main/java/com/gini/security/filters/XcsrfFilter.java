package com.gini.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
public class XcsrfFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        var modifiedRequest = new RequestWrapper(request);

        Optional.ofNullable(WebUtils.getCookie(request, "XSRF-TOKEN"))
                .map(Cookie::getValue)
                .ifPresentOrElse(csrfToken -> modifiedRequest.addHeader("X-XSRF-TOKEN", csrfToken),
                        () -> log.error("XSRF-TOKEN cookie not found")
                );

        filterChain.doFilter(modifiedRequest, response);
    }

}
