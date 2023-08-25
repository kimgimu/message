package com.ap4j.bma.controller.chat;

import com.ap4j.bma.model.entity.chat.ChatMessage;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    private int connectedClients = 0; // 연결된 클라이언트 수

    @GetMapping("/chat") // 추가: /chat 경로로 접근할 때 chat. html을 보여줌
    public String chat(Model model) {
        return "/chat/chat";
    }

    @MessageMapping("/chatting")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        System.out.println(chatMessage);
        chatMessage.setClientId(chatMessage.getClientId());
        messagingTemplate.convertAndSend("/topic/connectedClients", connectedClients);
        return chatMessage;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        connectedClients++;
        messagingTemplate.convertAndSend("/topic/connectedClients", connectedClients);
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        connectedClients--;
        messagingTemplate.convertAndSend("/topic/connectedClients", connectedClients);
    }

}
