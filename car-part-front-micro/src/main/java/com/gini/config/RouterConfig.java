package com.gini.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Slf4j
@Configuration
public class RouterConfig {

    @Value("${core.micro-url}")
    private String coreMicroUrl;


    @Bean
    public RouterFunction<ServerResponse> getRoute() {

        log.info("Car-part-core-micro url is: {}", coreMicroUrl);
        return route("car-part-core-micro")

                .route(path("/part/core-micro/**"), http(coreMicroUrl + ":9090"))
                .route(path("/car/**"), http(coreMicroUrl + ":9090"))
                .build();
    }

}
