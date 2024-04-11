package shiv.web.socket.model;

import java.util.Date;

public class SocketMessageDTO {
    private String message;
    private Date timestamp;

    public SocketMessageDTO(String message, Date timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "SocketObject{" +
                "id=" + message +
                ", name='" + timestamp + '\'' +
                '}';
    }
}
