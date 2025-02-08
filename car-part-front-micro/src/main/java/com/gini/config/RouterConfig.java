package com.gini.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class RouterConfig {


    @Bean
    public RouterFunction<ServerResponse> getRoute() {
        return route("car-part-core-micro")

                .route(path("/part/core-micro/**"), http("http://localhost:9090"))
                .route(path("/car/**"), http("http://localhost:9090"))
                .build();
    }

}
