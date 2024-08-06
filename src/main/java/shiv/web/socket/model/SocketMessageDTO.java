package shiv.web.socket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class SocketMessageDTO {
    private String message;
    private Date timestamp;
}
