package br.com.santander.authenticationservice.clients;

import br.com.santander.authenticationservice.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "userServiceClient", url ="${user.service.url}")
public interface UserServiceClient {

    @GetMapping("/email/{email}")
    public User findByEmail(@PathVariable String email);
}
