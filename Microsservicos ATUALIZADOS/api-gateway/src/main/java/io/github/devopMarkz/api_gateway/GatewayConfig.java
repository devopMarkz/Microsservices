package io.github.devopMarkz.api_gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route -> {
                    return route.path("/book-service/**")
                        .uri("lb://book-service");
                })
                .route(route -> {
                    return route.path("/exchange-service/**")
                            .uri("lb://exchange-service");
                })
                .build();
    }

}
