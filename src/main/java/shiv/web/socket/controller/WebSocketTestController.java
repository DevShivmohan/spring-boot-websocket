package shiv.web.socket.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiv.web.socket.bean.SessionManager;

@RestController
@RequestMapping("/session")
public class WebSocketTestController {

    @GetMapping("/remove/{id}")
    public ResponseEntity<?> deleteWebSocketSession(@PathVariable("id") String sessionId) {
        SessionManager.removeSession(sessionId);
        return ResponseEntity.status(HttpStatus.OK).body("Session deleted from Session Manager with id " + sessionId);
    }
}
