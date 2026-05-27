package com.atlas.bank.atlas_bank.infrastructure.adapter.in.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final ChatClient chatClient;

    @PostMapping("/chat")
    public String chat(@RequestBody String userMessage){
        return chatClient.prompt()
                .user(userMessage)
                .call()
                .content();
    }

}
