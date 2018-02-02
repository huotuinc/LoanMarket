package com.huotu.loanmarket.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author luyuanyuan
 * @date 2017/11/7
 */
public class LocalDateTime2StringSerializer extends JsonSerializer<LocalDateTime> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator
            , SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeString(DATE_TIME_FORMATTER.format(localDateTime));
    }
}
