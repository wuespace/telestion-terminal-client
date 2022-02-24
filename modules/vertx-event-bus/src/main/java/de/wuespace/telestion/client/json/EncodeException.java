package de.wuespace.telestion.client.json;

public class EncodeException extends RuntimeException {

    public EncodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncodeException(String message) {
        super(message);
    }

    public EncodeException(Throwable cause) {
        super(cause);
    }

    public EncodeException() {
        super();
    }
}
