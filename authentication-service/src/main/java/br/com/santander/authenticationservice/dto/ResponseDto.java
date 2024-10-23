package br.com.santander.authenticationservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
    String token;
}
