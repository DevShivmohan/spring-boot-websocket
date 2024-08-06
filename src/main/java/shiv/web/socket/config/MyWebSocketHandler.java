package shiv.web.socket.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponentsBuilder;
import shiv.web.socket.model.MessageRequestDto;
import shiv.web.socket.model.MessageResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Log4j2
@Component
public class MyWebSocketHandler extends TextWebSocketHandler {
    /**
     * assuming one userId has multiple sessions
     */
    private final Map<String, List<WebSocketSession>> sessionsMapper = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Add session into sessionsMapper when a session established
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished (WebSocketSession session) throws Exception {
        final String username = extractUsernameFromSession(session);
        sessionsMapper.computeIfAbsent(username, k -> new CopyOnWriteArrayList<>());
        sessionsMapper.get(username).add(session);
        log.info("Connection established {}", sessionsMapper.get(username));
    }

    /**
     * Sending message to a user on all active sessions
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage (WebSocketSession session, TextMessage message)
            throws Exception {
        final MessageRequestDto messageRequestDto = objectMapper.readValue(message.getPayload(),
                MessageRequestDto.class);
        if (sessionsMapper.get(messageRequestDto.getToUser()) == null) {
            log.error("Message sending failed because user does not connected {}", messageRequestDto.getToUser());
            return;
        }
        final var messageJson = objectMapper.writeValueAsString(new MessageResponseDto(extractUsernameFromSession(session), messageRequestDto.getMessage(), messageRequestDto.getTime()));
        final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        sessionsMapper.get(messageRequestDto.getToUser())
                .forEach(webSocketSession ->
                {
                    try {
                        webSocketSession.sendMessage(new TextMessage(messageJson));
                        atomicBoolean.set(true);
                    } catch (IOException e) {
                        log.error("Error in sending messages {}", e);
                    }
                });
        if (atomicBoolean.get()) {
            log.info("Message {} sent to all active sessions of user {}", messageRequestDto.getMessage(), messageRequestDto.getToUser());
        }
    }

    /**
     * remove session from sessiosMapper when a session disconnect
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed (WebSocketSession session, CloseStatus status)
            throws Exception {
        final String username = extractUsernameFromSession(session);
        if (sessionsMapper.get(username).remove(session)) {
            log.warn("Connection closed {}", session);
        }
    }

    /**
     * Extract username from session
     * @param webSocketSession
     * @return
     */
    private String extractUsernameFromSession (final WebSocketSession webSocketSession) {
        final MultiValueMap<String, String> queryParams = UriComponentsBuilder
                .fromUri(webSocketSession.getUri())
                .build()
                .getQueryParams();
        return queryParams.getFirst("username");
    }

}
