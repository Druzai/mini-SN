package ru.app.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Data
public class WSMessage {
    private String content;

    @JsonCreator
    public WSMessage(@JsonProperty("content") String content) {
        super();
        this.content = content;
    }
}
