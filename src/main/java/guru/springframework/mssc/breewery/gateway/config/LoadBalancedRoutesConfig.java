package guru.springframework.mssc.breewery.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("local-discovery")
@Configuration
public class LoadBalancedRoutesConfig {

    @Bean
    public RouteLocator loadBalancedRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(route -> route.path("/api/v1/beers**", "/api/v1/beers/*")
                        .uri("lb://beer-service")
                        .id("beer-service"))
                .route(route -> route.path("/api/v1/customers/**")
                        .uri("lb://beer-order-service")
                        .id("beer-order-service"))
                .route(route -> route.path("/api/v1/beers/*/inventories")
                        .filters(filter -> filter.circuitBreaker(config -> config.setName("inventoryCB")
                                .setFallbackUri("forward:/inventory-failover")
                                .setRouteId("inv-failover")))
                        .uri("lb://beer-inventory-service")
                        .id("beer-inventory-service"))
                .route(route -> route.path("/inventory-failover")
                        .uri("lb://inventory-failover-service")
                        .id("inventory-failover-service"))
                .build();
    }

}
