package br.com.santander.logsservice.util;

public class Constants {

    public static final String ACCOUNT_NOT_FOUND = "Account not found";
    public static final String INVALID_CEP = "Invalid CEP";
    public static final String INVALID_SCORE = "Invalid Score";
    public static final String CPF_ALREADY_EXISTS = "Client CPF already exists";
    public static final String ERROR_TO_CONVERTING_OBJECT_TO_JSON = "Error converting object to json";

    public static final String EVENT_CREATE_NEW_ACCOUNT_START_EXECUTION = "Create new account start execution";
    public static final String EVENT_CREATE_NEW_ACCOUNT_END_EXECUTION = "Create new account end execution";
    public static final String EVENT_CREATE_NEW_ACCOUNT_ERROR = "Create new account error during execution";

    public static final String KEY_FIELD_CREATE_NEW_ACCOUNT = "key field of create new account";

    public static final String RABBIT_QUEUE_LOGGING_EXCHANGE  = "LOGGING.MESSAGE.EXCHANGE";
    public static final String RABBIT_QUEUE_LOGGING_ROUTER    = "LOGGING.MESSAGE.KEY";
    public static final String RABBIT_QUEUE_LOGGING           = "LOGGING.MESSAGE";
}
