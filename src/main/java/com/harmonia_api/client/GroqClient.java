package com.harmonia_api.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.harmonia_api.dto.RequisicaoGroqDTO;
import com.harmonia_api.dto.RespostaGroqDTO;

@Component
public class GroqClient {

    private final WebClient groqWebClient;

    @Value("${groq.api.key}")
    private String apiKey;

    public GroqClient(@Qualifier("groqWebClient") WebClient groqWebClient) {
        this.groqWebClient = groqWebClient;
    }

    public RespostaGroqDTO gerarResposta(RequisicaoGroqDTO requisicao) {

        return groqWebClient
                .post()
                .uri("/chat/completions")
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requisicao)
                .retrieve()
                .bodyToMono(RespostaGroqDTO.class)
                .block();
    }
}