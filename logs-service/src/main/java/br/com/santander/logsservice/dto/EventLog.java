package br.com.santander.logsservice.dto;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("log")
@ToString
public class EventLog implements Serializable {
    private String id;
    private String event;
}

