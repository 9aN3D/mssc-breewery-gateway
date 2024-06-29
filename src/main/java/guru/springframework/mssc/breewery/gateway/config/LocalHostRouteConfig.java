package guru.springframework.mssc.breewery.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalHostRouteConfig {

    @Bean
    public RouteLocator localHostRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route.path("/api/v1/beers*", "/api/v1/beers/*")
                        .uri("http://localhost:8080")
                        .id("beer-service"))
                .route(route -> route.path("/api/v1/customers*")
                        .uri("http://localhost:8081")
                        .id("beer-order-service"))
                .build();
    }

}
