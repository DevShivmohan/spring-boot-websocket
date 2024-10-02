package shiv.web.socket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiv.web.socket.util.SessionUtil;

@RestController
@RequestMapping("/basic")
public class BasicRestController {

    @GetMapping("/set/{name}")
    public ResponseEntity<?> setSession(@PathVariable("name") String name) {
        SessionUtil.setSessionAttribute(name);
        System.out.println("Session saved successfully");
        return ResponseEntity.ok("Session saved");
    }


    @GetMapping("/get")
    public ResponseEntity<?> getSession() {
        final var data = SessionUtil.getSessionAttribute();
        System.out.println("Session data " + data);
        return ResponseEntity.ok(data);
    }
}
