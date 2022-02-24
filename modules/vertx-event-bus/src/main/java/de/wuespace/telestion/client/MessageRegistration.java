package de.wuespace.telestion.client;

public class MessageRegistration<T extends JsonMessage> extends Registration {

    private final Class<T> type;

    public MessageRegistration(String address, Runnable unregister, Class<T> type) {
        super(address, unregister);
        this.type = type;
    }

    public MessageRegistration(Registration base, Class<T> type) {
        super(base);
        this.type = type;
    }

    public Class<T> getMessageType() {
        return type;
    }
}
