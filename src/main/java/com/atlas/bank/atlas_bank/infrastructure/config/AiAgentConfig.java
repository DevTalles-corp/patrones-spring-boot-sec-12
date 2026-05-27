package com.atlas.bank.atlas_bank.infrastructure.config;

import com.atlas.bank.atlas_bank.infrastructure.adapter.in.ai.AtlasBankTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiAgentConfig {

    @Bean
    public ChatClient chatClient(
            ChatClient.Builder builder,
            AtlasBankTools atlasBankTools
    ){
        return builder
                .defaultSystem(
                "Sos un agente bancario del banco Atlas Bank. "
                        + "Ayudás a los clientes a operar con sus cuentas. "
                        + "Respondé siempre en español.")
                .defaultTools(atlasBankTools)
                .build();
    }

}
