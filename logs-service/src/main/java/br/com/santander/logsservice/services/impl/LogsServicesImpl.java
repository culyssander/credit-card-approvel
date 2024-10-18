package br.com.santander.logsservice.services.impl;

import br.com.santander.logsservice.dto.EventLog;
import br.com.santander.logsservice.services.LogsServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class LogsServicesImpl implements LogsServices {

    @Autowired
    private RedisTemplate template;

    @Override
    public void save(EventLog eventLog) {
        template.opsForHash().put("log", eventLog.getId(), eventLog);
        System.out.println("TESTANDO::::: " + template.opsForHash().get("log", eventLog.getId()));
    }
}
