package kr.co.system.stock.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SerializeFail extends RuntimeException {

    private final Logger logger = LoggerFactory.getLogger(SerializeFail.class);

    private static final String ERR_MSG = "변환에 실패했습니다.";

    public SerializeFail(JsonProcessingException e) {
        super(ERR_MSG);
        logger.error(ERR_MSG, e);
    }
}
