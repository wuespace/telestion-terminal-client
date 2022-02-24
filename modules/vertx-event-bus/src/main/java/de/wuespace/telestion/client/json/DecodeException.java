package de.wuespace.telestion.client.json;

public class DecodeException extends RuntimeException {

    public DecodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecodeException(String message) {
        super(message);
    }

    public DecodeException(Throwable cause) {
        super(cause);
    }

    public DecodeException() {
        super();
    }
}
