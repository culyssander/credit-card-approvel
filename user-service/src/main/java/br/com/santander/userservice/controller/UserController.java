package br.com.santander.userservice.controller;

import br.com.santander.userservice.dto.UserDto;
import br.com.santander.userservice.dto.UserInputDto;
import br.com.santander.userservice.services.UserServices;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Users", description = "Users Service API")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping()
    public UserDto createUserRoleClient(@Valid @RequestBody UserInputDto userInputDto) {
        return userServices.createUserRoleClient(userInputDto);
    }

    @GetMapping("/email/{email}")
    public UserDto findByEmail(@PathVariable String email) {
        return userServices.findByEmail(email);
    }

}
