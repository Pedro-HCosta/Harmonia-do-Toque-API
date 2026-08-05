package com.harmonia_api.dto;

import java.util.List;

public record RequisicaoGroqDTO(
        String model,
        List<Message> messages,
        Double temperature
) {

    public record Message(
            String role,
            String content
    ) {}
}