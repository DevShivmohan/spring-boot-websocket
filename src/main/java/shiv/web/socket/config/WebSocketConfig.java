//package shiv.web.socket.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//
//    @Override
//    public void registerWebSocketHandlers (WebSocketHandlerRegistry registry) {
//        registry.addHandler(new MyWebSocketHandler(), "/websocket")
//                .setAllowedOriginPatterns(List.of("*").toArray(new String[]{}));
//    }
//
//}
