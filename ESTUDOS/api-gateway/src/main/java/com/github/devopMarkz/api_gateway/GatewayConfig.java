package com.github.devopMarkz.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route(predicateSpec -> {
                    return predicateSpec.path("/produto-service/**")
                            .filters(filter -> filter.stripPrefix(1))
                            .uri("lb://produto-service");
                })
                .route(predicateSpec -> {
                    return predicateSpec.path("/cliente-service/**")
                            .filters(filter -> filter.stripPrefix(1))
                            .uri("lb://cliente-service");
                })
                .build();
    }

}
