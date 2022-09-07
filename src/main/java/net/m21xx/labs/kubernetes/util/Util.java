package net.m21xx.labs.kubernetes.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class Util {

    private static ObjectMapper getObjectMapper() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    public static <T> T readValueFrom(String value, Class<T> clazz) {

        ObjectMapper mapper = getObjectMapper();
        T convertedValue = null;
        try {
            convertedValue = mapper.readValue(value, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return convertedValue;
    }

    public static <T> T convertStringTo(Object value, Class<T> clazz) {

        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(value, clazz);
    }

}
