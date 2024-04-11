package shiv.web.socket.config;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shiv.web.socket.model.SocketMessageDTO;

import java.util.Date;

@Component
public class WebSocketMessageBroker {

    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketMessageBroker(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /**
     * based on this broadcasting client will subscribe to the topic '/topic/messages'
     */
    @Scheduled(fixedRate = 5000)
    public void sendMessageToTopic() {
        final var message = new SocketMessageDTO("Shivmohan", new Date());
        System.out.println("sending message "+ message);
        messagingTemplate.convertAndSend("/topic/messages", message);
    }
}
