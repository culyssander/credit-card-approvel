package br.com.santander.logsservice.services;

import br.com.santander.logsservice.dto.EventLog;

import java.util.Map;

public interface LogsServices {

    public void save(EventLog eventLog);
}
