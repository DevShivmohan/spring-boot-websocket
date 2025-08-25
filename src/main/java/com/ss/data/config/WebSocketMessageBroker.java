package com.ss.data.config;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@AllArgsConstructor
@Log4j2
public class WebSocketMessageBroker {
    private final SimpMessagingTemplate messagingTemplate;
    private final String fileName = "d46tr32tr2.jpg";

    static {
        System.setProperty("java.awt.headless", "false");
    }

    @Scheduled(fixedDelay = 5L)
    public void sendImage() {
        try {
            final Robot robot = new Robot();
            final File filePath = new File(fileName);
            Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage bufferedImage = robot.createScreenCapture(rectangle);
            ImageIO.write(bufferedImage, "jpg", filePath);
            byte[] imageBytes = Files.readAllBytes(filePath.toPath());
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            messagingTemplate.convertAndSend("/topic/ss-data", base64Image);
        } catch (Exception e) {
            log.error("Error occurred ", e);
        }
    }
}
