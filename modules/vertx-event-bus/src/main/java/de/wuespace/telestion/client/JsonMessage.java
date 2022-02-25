package de.wuespace.telestion.client;

import de.wuespace.telestion.client.json.DecodeException;
import de.wuespace.telestion.client.json.EncodeException;
import de.wuespace.telestion.client.json.JacksonCodec;
import de.wuespace.telestion.client.json.JsonObject;
import io.vertx.eventbusclient.Handler;
import io.vertx.eventbusclient.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h2>Description</h2>
 * The base class for all messages which are automatically encoded with the JsonMessageCodec.
 * <p>
 * All subclasses have to be valid json classes. This means that they could be encoded by {@link JacksonCodec}.
 *
 * <h2>Usage</h2>
 * <pre>
 * {@code
 * public record TimeMessage(
 *     @JsonProperty long receiveTime,
 *     @JsonProperty long sendTime
 * ) implements JsonMessage {
 * }
 * }
 * </pre>
 *
 * @author Jan von Pichowski (@jvpichowski), Cedric Boes (@cb0s), Ludwig Richter (@fussel178)
 */
public interface JsonMessage {

	///
	/// ASYNCHRONOUS DECODING SECTION
	///

	/**
	 * Asynchronous version of {@link #from(String, Class)}.
	 *
	 * @param type             the class of the target {@link JsonMessage}
	 * @param json             the JSON {@link String} that contains the necessary information to construct
	 *                         the specified {@link JsonMessage}
	 * @param handler          gets called when the conversion was successful
	 * @param exceptionHandler gets called when a {@link DecodeException} occurred during conversion
	 * @param <T>              the type of the target {@link JsonMessage}
	 * @return {@code true} when the conversion was successful
	 */
	static <T extends JsonMessage> boolean on(Class<T> type, String json, Handler<T> handler,
											  Handler<DecodeException> exceptionHandler) {
		try {
			handler.handle(from(json, type));
			return true;
		} catch (DecodeException e) {
			logger.warn("Cannot convert JSON string to JsonMessage {}:", type.getName(), e);
			exceptionHandler.handle(e);
			return false;
		}
	}

	/**
	 * Asynchronous version of {@link #from(Object, Class)}.
	 *
	 * @param type             the class of the target {@link JsonMessage}
	 * @param json             the plain {@link Object} that contains the necessary information to construct
	 *                         the specified {@link JsonMessage}
	 * @param handler          gets called when the conversion was successful
	 * @param exceptionHandler gets called when a {@link DecodeException} occurred during conversion
	 * @param <T>              the type of the target {@link JsonMessage}
	 * @return {@code true} when the conversion was successful
	 */
	static <T extends JsonMessage> boolean on(Class<T> type, Object json, Handler<T> handler,
											  Handler<DecodeException> exceptionHandler) {
		try {
			handler.handle(from(json, type));
			return true;
		} catch (DecodeException e) {
			logger.warn("Cannot convert Object to JsonMessage {}:", type.getName(), e);
			exceptionHandler.handle(e);
			return false;
		}
	}

	/**
	 * Asynchronous version of {@link #from(Message, Class)}.
	 *
	 * @param type             the class of the target {@link JsonMessage}
	 * @param message          the message which body contains the necessary information to construct
	 *                         the specified {@link JsonMessage}
	 * @param handler          gets called when the conversion was successful
	 * @param exceptionHandler gets called when a {@link DecodeException} occurred during conversion
	 * @param <T>              the type of the target {@link JsonMessage}
	 * @return {@code true} when the conversion was successful
	 */
	static <T extends JsonMessage> boolean on(Class<T> type, Message<?> message, Handler<T> handler,
											  Handler<DecodeException> exceptionHandler) {
		try {
			handler.handle(from(message, type));
			return true;
		} catch (DecodeException e) {
			logger.warn("Cannot convert Vertx Message to JsonMessage {}:", type.getName(), e);
			exceptionHandler.handle(e);
			return false;
		}
	}

	/**
	 * Like {@link #on(Class, String, Handler, Handler)} but without an exception handler.
	 */
	static <T extends JsonMessage> boolean on(Class<T> type, String json, Handler<T> handler) {
		return on(type, json, handler, e -> {
		});
	}

	/**
	 * Like {@link #on(Class, Object, Handler, Handler)} but without an exception handler.
	 */
	static <T extends JsonMessage> boolean on(Class<T> type, Object json, Handler<T> handler) {
		return on(type, json, handler, e -> {
		});
	}

	/**
	 * Like {@link #on(Class, Message, Handler, Handler)} but without an exception handler.
	 */
	static <T extends JsonMessage> boolean on(Class<T> type, Message<?> message, Handler<T> handler) {
		return on(type, message, handler, e -> {
		});
	}

	///
	/// SYNCHRONOUS DECODING SECTION
	///

	/**
	 * Constructs a {@link JsonMessage} from a JSON {@link String}.
	 *
	 * @param json the JSON {@link String} that contains the necessary information to construct
	 *             the specified {@link JsonMessage}
	 * @param type the class of the target {@link JsonMessage}
	 * @param <T>  the type of the target {@link JsonMessage}
	 * @return the decoded message
	 * @throws DecodeException if the JSON string does not contain the necessary information to successfully
	 *                         construct the specified {@link JsonMessage}
	 */
	static <T extends JsonMessage> T from(String json, Class<T> type) throws DecodeException {
		return JacksonCodec.from(json, type);
	}

	/**
	 * Constructs a {@link JsonMessage} from a plain {@link Object}.
	 *
	 * @param json the plain {@link Object} that contains the necessary information to construct
	 *             the specified {@link JsonMessage}
	 * @param type the class of the target {@link JsonMessage}
	 * @param <T>  the type of the target {@link JsonMessage}
	 * @return the decoded message
	 * @throws DecodeException if the plain object does not contain the necessary information to successfully
	 *                         construct the specified {@link JsonMessage}
	 */
	static <T extends JsonMessage> T from(Object json, Class<T> type) throws DecodeException {
		return JacksonCodec.from(json, type);
	}

	/**
	 * Constructs a {@link JsonMessage} from a Vert.x EventBus {@link Message} body.
	 *
	 * @param message the message which body contains the necessary information to construct
	 *                the specified {@link JsonMessage}
	 * @param type    the class of the target {@link JsonMessage}
	 * @param <T>     the type of the target {@link JsonMessage}
	 * @return the decoded message
	 * @throws DecodeException if the raw message body does not contain the necessary information to successfully
	 *                         construct the specified {@link JsonMessage}
	 */
	static <T extends JsonMessage> T from(Message<?> message, Class<T> type) throws DecodeException {
		return from(message.body(), type);
	}

	///
	/// SYNCHRONOUS ENCODING SECTION
	///

	/**
	 * Constructs a {@link JsonObject} from the {@link JsonMessage}.
	 *
	 * @return the constructed JSON object
	 * @throws IllegalArgumentException if the JSON object cannot represent the type of
	 *                                  any {@link JsonMessage} property
	 */
	default JsonObject toJsonObject() throws IllegalArgumentException {
		return JsonObject.mapFrom(this);
	}

	/**
	 * Constructs a {@link String} containing the properties of the {@link JsonMessage} as JSON values.
	 *
	 * @param pretty when {@code true} the JSON output is properly formatted
	 * @return a JSON string representing the {@link JsonMessage}
	 * @throws EncodeException if the {@link JsonMessage} containing properties that cannot be represented
	 *                         by JSON values
	 * @see JacksonCodec#toString(Object, boolean)
	 */
	default String toJsonString(boolean pretty) throws EncodeException {
		return JacksonCodec.toString(this, pretty);
	}

	/**
	 * Like {@link #toJsonString(boolean)} but with space efficient JSON output.
	 */
	default String toJsonString() throws EncodeException {
		return toJsonString(false);
	}

	///
	/// OTHERS SECTION
	///

	/**
	 * Returns the simple class name of the implementing subclass.
	 *
	 * @return simple class name of subclass
	 */
	default String className() {
		return getClass().getName();
	}

	Logger logger = LoggerFactory.getLogger(JsonMessage.class);
}
