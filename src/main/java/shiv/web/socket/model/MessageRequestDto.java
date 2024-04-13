package shiv.web.socket.model;

import lombok.Data;

import java.util.Date;

@Data
public class MessageRequestDto {
    private String toUser;
    private String message;
    private Date time;
}
