package br.com.santander.creditcardservice.util;

public class Constants {


    public static final String EVENT_CREDIT_CARD_REQUEST_START_EXECUTION = "Request credit card start execution";
    public static final String EVENT_CREDIT_CARD_REQUEST_END_EXECUTION = "Request credit card end execution";
    public static final String EVENT_CREDIT_CARD_REQUEST_ERROR = "Request credit card error during execution";

    public static final String KEY_FIELD_CREDIT_CARD_REQUEST = "key field of request credit card";

    public static final String CORRELACTION_ID = "correlationId";

    public static final String RABBIT_QUEUE_LOGGING_EXCHANGE  = "LOGGING.MESSAGE.EXCHANGE";
    public static final String RABBIT_QUEUE_LOGGING_ROUTER    = "LOGGING.MESSAGE.KEY";
    public static final String RABBIT_QUEUE_LOGGING           = "LOGGING.MESSAGE";
}
