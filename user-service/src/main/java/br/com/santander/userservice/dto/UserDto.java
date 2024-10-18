package br.com.santander.userservice.dto;

import br.com.santander.userservice.entities.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDto implements Serializable {
    private Long id;
    private String name;
    private String email;
    private boolean status;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime lastUpdated;
    private LocalDateTime lastLogin;
}
