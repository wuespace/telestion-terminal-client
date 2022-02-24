package de.wuespace.telestion.client;

@FunctionalInterface
public interface MessageHandler<T extends JsonMessage> {
    void handle(T body);
}
