//package shiv.web.socket.config;
//
//import lombok.AllArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//@Component
//@AllArgsConstructor
//@Log4j2
//public class WebSocketMessageBroker {
//    private final SimpMessagingTemplate messagingTemplate;
//
//
//    @Scheduled(fixedRate = 500)
//    public void broadcastRandomData() {
//        final Map<String, Object> map = new HashMap<>();
//        map.put("id", UUID.randomUUID());
//        map.put("name", "Shiv");
//        map.put("time", LocalDateTime.now());
//        messagingTemplate.convertAndSend("/topic/user", map);
//        log.info("Broadcast data {}", map);
//    }
//}
