package de.wuespace.telestion.client.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JacksonCodec {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper prettyMapper = new ObjectMapper();

    static {
        prettyMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public static ObjectMapper getMapper() {
        return mapper;
    }

    public static ObjectMapper getPrettyMapper() {
        return prettyMapper;
    }

    public static <T> T from(String json, Class<T> type) throws DecodeException {
        try {
            return mapper.readValue(json, type);
        } catch (JsonProcessingException e) {
            throw new DecodeException(e);
        }
    }

    public static <T> T from(Object json, Class<T> type) throws DecodeException {
        try {
            return mapper.convertValue(json, type);
        } catch (IllegalArgumentException e) {
            throw new DecodeException(e);
        }
    }

    public static String toString(Object object, boolean pretty) throws EncodeException {
        try {
            return pretty ? prettyMapper.writeValueAsString(object) : mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new EncodeException(e);
        }
    }

    public static String toString(Object object) throws EncodeException {
        return toString(object, false);
    }
}
