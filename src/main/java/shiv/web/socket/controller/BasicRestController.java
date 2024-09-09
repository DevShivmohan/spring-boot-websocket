package shiv.web.socket.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shiv.web.socket.util.SessionUtil;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/basic")
public class BasicRestController {

    @GetMapping("/set")
    public ResponseEntity<?> setSession() {
        final Map<String,Object> map=new HashMap<>();
        for(int i=0;i<1000;i++){
            map.put("username-id-"+i, UUID.randomUUID().toString());
        }
        SessionUtil.setSessionAttribute("usernames", map);
        System.out.println("Session saved successfully");
        return ResponseEntity.ok("Session saved");
    }


    @GetMapping("/get")
    public ResponseEntity<?> getSession() {
        final var data = SessionUtil.getSessionAttribute("usernames");
        System.out.println("Session data " + data);
        return ResponseEntity.ok(data);
    }
}
