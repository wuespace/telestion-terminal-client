package de.wuespace.telestion.core;

import de.wuespace.telestion.client.EventBusClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) {
        var inAddress = "string-out";
        var outAddress = "string-in";
        logger.debug("inAddress: {}, outAddress: {}", inAddress, outAddress);

        // create instance
        var client = EventBusClient.connectWithTcpBridge("localhost", 7000);

        // register on publish address from application
        var counter = new AtomicInteger();
        var registration = client.register(inAddress, message -> {
            logger.info("Received string message on {}: {}", inAddress, message);

            // answer on other channel
            client.publish(outAddress, new StringMessage("Pong number: " + counter.getAndIncrement()));
        }, StringMessage.class);

        // log after connect
        client.connectedHandler(() -> logger.info("Connected"));
        // log after close
        client.closeHandler(() -> logger.info("Eventbus closed"));
        // try to connect
        client.connect();

        // TODO: Combine and rewrite this with lanterna Terminal GUI
    }

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
}
