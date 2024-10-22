package br.com.santander.logsservice.listener;

import br.com.santander.logsservice.dto.EventLog;
import br.com.santander.logsservice.services.LogsServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

import static br.com.santander.logsservice.util.Constants.RABBIT_QUEUE_LOGGING;

@Slf4j
@Component
public class LogsListener {

    @Autowired
    private LogsServices logsServices;

    @RabbitListener(queues = RABBIT_QUEUE_LOGGING)
    public void save(Message message) {
        message.getMessageProperties().setContentType("application/json");

        EventLog eventLog = EventLog.builder()
                .id(LocalDateTime.now().toString())
                .event(new String(message.getBody()))
                .build();

        logsServices.save(eventLog);
    }
}
