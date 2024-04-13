package shiv.web.socket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class MessageResponseDto {
    private String fromUser;
    private String message;
    private Date time;
}
