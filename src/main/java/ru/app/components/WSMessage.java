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
    private String toUser;
    private String fromUser;
    private String content;

    @JsonCreator
    public WSMessage(@JsonProperty("toUser") String toUser,
                     @JsonProperty("fromUser") String fromUser,
                     @JsonProperty("content") String content) {
        super();
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.content = content;
    }
}
