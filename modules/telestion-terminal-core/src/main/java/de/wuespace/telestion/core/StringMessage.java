package de.wuespace.telestion.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.client.JsonMessage;

@JsonIgnoreProperties(ignoreUnknown = true)
public record StringMessage(
        @JsonProperty String content
) implements JsonMessage {
}
