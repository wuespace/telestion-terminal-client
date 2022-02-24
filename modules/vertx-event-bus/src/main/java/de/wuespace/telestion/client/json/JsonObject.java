package de.wuespace.telestion.client.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class JsonObject {
    public static JsonObject mapFrom(Object object) throws IllegalArgumentException {
        return new JsonObject(object);
    }

    private final Map<String, Object> contents;

    public JsonObject(Map<String, Object> contents) {
        this.contents = contents;
    }

    public JsonObject(Object object) throws DecodeException {
        //noinspection unchecked
        this((Map<String, Object>) JacksonCodec.from(object, Map.class));
    }

    public JsonObject(String json) throws DecodeException {
        //noinspection unchecked
        this((Map<String, Object>) JacksonCodec.from(json, Map.class));
    }

    public JsonObject() {
        this(new LinkedHashMap<>());
    }

    public <T> T mapTo(Class<T> type) throws IllegalArgumentException {
        return JacksonCodec.from(contents, type);
    }

    public JsonObject put(String key, Object value) {
        contents.put(key, value);
        return this;
    }

    public JsonObject putAll(JsonObject other) {
        contents.putAll(other.contents);
        return this;
    }

    public Object remove(String key) {
        return contents.remove(key);
    }

    public Object getValue(String key) {
        return contents.get(key);
    }

    public Object getValue(String key, Object defaultValue) {
        return getOrDefault(key, defaultValue, this::getValue);
    }

    public String getString(String key) throws ClassCastException {
        return (String) contents.get(key);
    }

    public String getString(String key, String defaultValue) throws ClassCastException {
        return getOrDefault(key, defaultValue, this::getString);
    }

    public Number getNumber(String key) throws ClassCastException {
        return (Number) contents.get(key);
    }

    public Number getNumber(String key, Number defaultValue) throws ClassCastException {
        return getOrDefault(key, defaultValue, this::getNumber);
    }

    public Integer getInteger(String key) throws ClassCastException {
        var number = getNumber(key);
        return number instanceof Integer ? (Integer) number : Integer.valueOf(number.intValue());
    }

    public Integer getInteger(String key, Integer defaultValue) throws ClassCastException {
        return getOrDefault(key, defaultValue, this::getInteger);
    }

    public Long getLong(String key) throws ClassCastException {
        var number = getNumber(key);
        return number instanceof Long ? (Long) number : Long.valueOf(number.longValue());
    }

    public Long getLong(String key, Long defaultValue) throws ClassCastException {
        return getOrDefault(key, defaultValue, this::getLong);
    }

    public Float getFloat(String key) throws ClassCastException {
        var number = getNumber(key);
        return number instanceof Float ? (Float) number : Float.valueOf(number.floatValue());
    }

    public Float getFloat(String key, Float defaultValue) throws ClassCastException {
        return getOrDefault(key, defaultValue, this::getFloat);
    }

    public Double getDouble(String key) throws ClassCastException {
        var number = getNumber(key);
        return number instanceof Double ? (Double) number : Double.valueOf(number.doubleValue());
    }

    public Double getDouble(String key, Double defaultValue) throws ClassCastException {
        return getOrDefault(key, defaultValue, this::getDouble);
    }

    public Boolean getBoolean(String key) throws ClassCastException {
        return (Boolean) contents.get(key);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) throws ClassCastException {
        return getOrDefault(key, defaultValue, this::getBoolean);
    }

    public JsonObject getJsonObject(String key) throws ClassCastException {
        var value = contents.get(key);
        return value instanceof Map<?, ?> ? new JsonObject(value) : (JsonObject) value;
    }

    public JsonObject getJsonObject(String key, JsonObject defaultValue) throws ClassCastException {
        return getOrDefault(key, defaultValue, this::getJsonObject);
    }

    public JsonArray getJsonArray(String key) throws ClassCastException {
        var value = contents.get(key);
        return value instanceof List<?> ? new JsonArray(value) : (JsonArray) value;
    }

    public JsonArray getJsonArray(String key, JsonArray defaultValue) throws ClassCastException {
        return getOrDefault(key, defaultValue, this::getJsonArray);
    }

    public Set<String> getFieldNames() {
        return contents.keySet();
    }

    public Map<String, Object> getContents() {
        return contents;
    }

    public boolean containsKey(String key) {
        return contents.containsKey(key);
    }

    public JsonObject clear() {
        contents.clear();
        return this;
    }

    public boolean isEmpty() {
        return contents.isEmpty();
    }

    public String encode(boolean pretty) throws EncodeException {
        return JacksonCodec.toString(contents, pretty);
    }

    public String encode() throws EncodeException {
        return encode(false);
    }

    private <T> T getOrDefault(String key, T defaultValue, Function<String, T> getter) {
        if (contents.containsKey(key)) {
            return getter.apply(key);
        } else {
            return defaultValue;
        }
    }
}
