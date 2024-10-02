package shiv.web.socket.bean;


import lombok.extern.log4j.Log4j2;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Log4j2
public class SessionManager {
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public static void addSession(WebSocketSession webSocketSession) {
        sessions.add(webSocketSession);
    }

    public static void removeSession(String sessionId) {
        sessions.stream().filter(session -> sessionId.equals(session.getId())).findFirst()
                .ifPresent(SessionManager::closeSession);
    }

    public static void removeSession(WebSocketSession webSocketSession) {
        closeSession(webSocketSession);
    }

    private static void closeSession(WebSocketSession session) {
        if (session.isOpen()) {
            try {
                session.close(CloseStatus.POLICY_VIOLATION);
            } catch (IOException e) {
                log.error(e.toString());
            }
        }
        sessions.remove(session);
    }
}
