package br.com.santander.apigateway.router;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class ValidatorRouter {

    private static  final List<String> OPEN_API_ENDPOINTS = List.of(
            "/swagger-ui/index.html",
            "/api/v1/users/create-new/role/client",
            "/api/v1/products/filters"
    );

    public Predicate<ServerHttpRequest> isSecure = request -> OPEN_API_ENDPOINTS.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

//    public Predicate<> isClientRouter = req
}
