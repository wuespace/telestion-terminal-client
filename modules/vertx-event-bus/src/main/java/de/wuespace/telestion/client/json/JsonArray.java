package de.wuespace.telestion.client.json;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonArray {

    public static JsonArray mapFrom(Object object) throws DecodeException {
        return new JsonArray(object);
    }

    private final List<Object> contents;

    public JsonArray(Object object) throws DecodeException {
        //noinspection unchecked
        this((List<Object>) JacksonCodec.from(object, List.class));
    }

    public JsonArray(List<Object> contents) {
        this.contents = contents;
    }

    public JsonArray() {
        this(new ArrayList<>());
    }

    public <T> T mapTo(Class<T> type) throws IllegalArgumentException {
        return JacksonCodec.from(contents, type);
    }

    public JsonArray add(Object value) {
        contents.add(value);
        return this;
    }

    /**
     * Like {@link List#add(int, Object)}.
     */
    public JsonArray add(int index, Object value) {
        contents.add(index, value);
        return this;
    }

    public JsonArray addAll(JsonArray other) {
        contents.addAll(other.contents);
        return this;
    }

    public JsonArray set(int index, Object value) {
        contents.set(index, value);
        return this;
    }

    public JsonArray remove(int index) {
        contents.remove(index);
        return this;
    }

    public JsonArray remove(Object value) {
        contents.remove(value);
        return this;
    }

    public List<Object> getContents() {
        return contents;
    }

    public Object getValue(int index) {
        return contents.get(index);
    }

    public String getString(int index) throws ClassCastException {
        return (String) contents.get(index);
    }

    public Number getNumber(int index) throws ClassCastException {
        return (Number) contents.get(index);
    }

    public Integer getInteger(int index) throws ClassCastException {
        var number = getNumber(index);
        return number instanceof Integer ? (Integer) number : Integer.valueOf(number.intValue());
    }

    public Long getLong(int index) throws ClassCastException {
        var number = getNumber(index);
        return number instanceof Long ? (Long) number : Long.valueOf(number.longValue());
    }

    public Float getFloat(int index) throws ClassCastException {
        var number = getNumber(index);
        return number instanceof Float ? (Float) number : Float.valueOf(number.floatValue());
    }

    public Double getDouble(int index) throws ClassCastException {
        var number = getNumber(index);
        return number instanceof Double ? (Double) number : Double.valueOf(number.doubleValue());
    }

    public Boolean getBoolean(int index) throws ClassCastException {
        return (Boolean) contents.get(index);
    }

    public JsonObject getJsonObject(int index) throws ClassCastException {
        var value = contents.get(index);
        return value instanceof Map<?,?> ? new JsonObject(value) : (JsonObject) value;
    }

    public JsonArray getJsonArray(int index) throws ClassCastException {
        var value = contents.get(index);
        return value instanceof List<?> ? new JsonArray(value) : (JsonArray) value;
    }

    public int size() {
        return contents.size();
    }

    public boolean contains(Object value) {
        return contents.contains(value);
    }

    public JsonArray clear() {
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
}
