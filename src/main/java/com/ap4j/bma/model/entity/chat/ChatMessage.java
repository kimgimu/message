package com.ap4j.bma.model.entity.chat;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChatMessage {
    private String clientId;
    private String content;
    private String sender;
}