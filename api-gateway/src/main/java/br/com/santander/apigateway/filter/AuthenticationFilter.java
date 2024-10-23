package br.com.santander.apigateway.filter;

import br.com.santander.apigateway.router.ValidatorRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private ValidatorRouter router;

    @Autowired
//    private JWTUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {

            if (router.isSecure.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                    throw new RuntimeException("Missing authorization header");

                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);

                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    authHeader = authHeader.substring(7);
                }

                try {
//                    String URL = UriComponentsBuilder.fromHttpUrl("")
//                            .path("")
//                            .toUriString();
//                    jwtUtil.validateToken(authHeader);
                } catch (RuntimeException e) {
                    throw new RuntimeException("Invalid access...");
                }
            }

            System.out.println("ROTA: " + router.isSecure.test(exchange.getRequest()));
            System.out.println("ROTA: " + exchange.getRequest().getURI().getPath());

            exchange.getRequest().mutate().header("correlationId", UUID.randomUUID().toString());

            return chain.filter(exchange);
        }));
    }

    static  class Config {}
}
