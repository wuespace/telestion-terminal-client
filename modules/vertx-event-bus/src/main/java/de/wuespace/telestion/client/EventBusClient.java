package de.wuespace.telestion.client;

import de.wuespace.telestion.client.json.JsonObject;
import io.vertx.eventbusclient.*;

@SuppressWarnings("ClassCanBeRecord")
public class EventBusClient {

    public static EventBusClient connectWithTcpBridge(EventBusClientOptions options) {
        return new EventBusClient(io.vertx.eventbusclient.EventBusClient.tcp(options));
    }

    public static EventBusClient connectWithTcpBridge(String host, int port) {
        var options = new EventBusClientOptions().setHost(host).setPort(port);
        return connectWithTcpBridge(options);
    }

    public static EventBusClient connectWithTcpBridge() {
        return connectWithTcpBridge(new EventBusClientOptions());
    }

    public static EventBusClient connectWithWebSocketBridge(EventBusClientOptions options) {
        return new EventBusClient(io.vertx.eventbusclient.EventBusClient.webSocket(options));
    }

    public static EventBusClient connectWithWebSocketBridge(String host, int port) {
        var options = new EventBusClientOptions().setHost(host).setPort(port);
        return connectWithWebSocketBridge(options);
    }

    public static EventBusClient connectWithWebSocketBridge() {
        return connectWithWebSocketBridge(new EventBusClientOptions());
    }

    private final io.vertx.eventbusclient.EventBusClient eventBusClient;

    public EventBusClient(io.vertx.eventbusclient.EventBusClient eventBusClient) {
        this.eventBusClient = eventBusClient;
    }

    ///
    /// CONTROL SECTION
    ///

    public EventBusClient setDefaultDeliveryOptions(DeliveryOptions defaultOptions) {
        eventBusClient.setDefaultDeliveryOptions(defaultOptions);
        return this;
    }

    public boolean isConnected() {
        return eventBusClient.isConnected();
    }

    public EventBusClient connect() {
        eventBusClient.connect();
        return this;
    }

    public EventBusClient close() {
        eventBusClient.close();
        return this;
    }

    public EventBusClient connectedHandler(Runnable connectedHandler) {
        eventBusClient.connectedHandler(handler -> {
            connectedHandler.run();
            // call given handler so the EventBus client can finish its stuff
            handler.handle(null);
        });
        return this;
    }

    public EventBusClient closeHandler(Runnable closeHandler) {
        eventBusClient.closeHandler(result -> closeHandler.run());
        return this;
    }

    public EventBusClient exceptionHandler(Handler<Throwable> exceptionHandler) {
        eventBusClient.exceptionHandler(exceptionHandler);
        return this;
    }

    ///
    /// PUBLISH SECTION
    ///

    public EventBusClient publish(String address, Object message, DeliveryOptions options) {
        this.eventBusClient.publish(address, message, options);
        return this;
    }

    public EventBusClient publish(String address, Object message) {
        return publish(address, message, new DeliveryOptions());
    }

    public EventBusClient publish(String address, JsonObject message, DeliveryOptions options) {
        return publish(address, message.getContents(), options);
    }

    public EventBusClient publish(String address, JsonObject message) {
        return publish(address, message, new DeliveryOptions());
    }

    public EventBusClient publish(String address, JsonMessage message, DeliveryOptions options) {
        return publish(address, message.toJsonObject(), options);
    }

    public EventBusClient publish(String address, JsonMessage message) {
        return publish(address, message, new DeliveryOptions());
    }

    ///
    /// SEND SECTION
    ///

    public EventBusClient send(String address, Object message, DeliveryOptions options) {
        this.eventBusClient.send(address, message, options);
        return this;
    }

    public EventBusClient send(String address, Object message) {
        return send(address, message, new DeliveryOptions());
    }

    public EventBusClient send(String address, JsonObject message, DeliveryOptions options) {
        return send(address, message.getContents(), options);
    }

    public EventBusClient send(String address, JsonObject message) {
        return send(address, message, new DeliveryOptions());
    }

    public EventBusClient send(String address, JsonMessage message, DeliveryOptions options) {
        return send(address, message.toJsonObject(), options);
    }

    public EventBusClient send(String address, JsonMessage message) {
        return send(address, message, new DeliveryOptions());
    }

    ///
    /// REQUEST SECTION
    ///

    public <T> EventBusClient request(String address, Object message, DeliveryOptions options, Handler<AsyncResult<Message<T>>> handler) {
        this.eventBusClient.request(address, message, options, handler);
        return this;
    }

    public <V extends JsonMessage> EventBusClient request(String address, Object message, DeliveryOptions options, Class<V> type, MessageHandler<V> handler) {
        return request(address, message, options, result -> {
            if (result.succeeded()) {
                JsonMessage.on(type, result.result(), handler::handle);
            }
        });
    }

    public <V extends JsonMessage, T> EventBusClient request(String address, Object message, DeliveryOptions options, Class<V> type, ExtendedMessageHandler<V, T> handler) {
        return this.<T>request(address, message, options, result -> {
            if (result.succeeded()) {
                JsonMessage.on(type, result.result(), body -> handler.handle(body, result.result()));
            }
        });
    }

    public <T> EventBusClient request(String address, Object message, Handler<AsyncResult<Message<T>>> handler) {
        return request(address, message, new DeliveryOptions(), handler);
    }

    public <V extends JsonMessage> EventBusClient request(String address, Object message, Class<V> type, MessageHandler<V> handler) {
        return request(address, message, new DeliveryOptions(), type, handler);
    }

    public <V extends JsonMessage, T> EventBusClient request(String address, Object message, Class<V> type, ExtendedMessageHandler<V, T> handler) {
        return request(address, message, new DeliveryOptions(), type, handler);
    }

    ///
    /// REGISTER SECTION
    ///

    public <T> Registration register(String address, Handler<Message<T>> handler) {
        var consumer = this.eventBusClient.consumer(address, handler);
        return new Registration(address, consumer::unregister);
    }

    public <V extends JsonMessage> MessageRegistration<V> register(String address, MessageHandler<V> handler, Class<V> type) {
        return register(address, message -> JsonMessage.on(type, message, handler::handle)).hydrate(type);
    }

    public <V extends JsonMessage, T> MessageRegistration<V> register(String address, ExtendedMessageHandler<V, T> handler, Class<V> type) {
        return this.<T>register(address, message -> JsonMessage.on(type, message, body -> handler.handle(body, message))).hydrate(type);
    }
}
