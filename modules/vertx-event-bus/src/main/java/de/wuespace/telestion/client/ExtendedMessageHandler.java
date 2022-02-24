package de.wuespace.telestion.client;

import io.vertx.eventbusclient.Message;

@FunctionalInterface
public interface ExtendedMessageHandler<V extends JsonMessage, T> {
    void handle(V body, Message<T> message);
}
