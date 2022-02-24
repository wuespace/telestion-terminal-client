package de.wuespace.telestion.client;

public class Registration {
    private final String address;

    private final Runnable unregister;

    public Registration(String address, Runnable unregister) {
        this.address = address;
        this.unregister = unregister;
    }

    public Registration(Registration other) {
        this.address = other.address;
        this.unregister = other.unregister;
    }

    public String getAddress() {
        return address;
    }

    public void unregister() {
        unregister.run();
    }

    public <T extends JsonMessage> MessageRegistration<T> hydrate(Class<T> type) {
        return new MessageRegistration<>(this, type);
    }
}
