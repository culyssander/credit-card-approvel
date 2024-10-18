package br.com.santander.logsservice.listener;

import br.com.santander.logsservice.dto.EventLog;
import br.com.santander.logsservice.services.LogsServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static br.com.santander.logsservice.util.Constants.RABBIT_QUEUE_LOGGING;

@Slf4j
@Component
public class LogsListener {

    @Autowired
    private LogsServices logsServices;

    @RabbitListener(queues = RABBIT_QUEUE_LOGGING)
    public void save(Message message) {

        EventLog eventLog = EventLog.builder()
                .id(UUID.randomUUID().toString())
                .event(message.getBody().toString())
                .build();

        logsServices.save(eventLog);
    }
}
