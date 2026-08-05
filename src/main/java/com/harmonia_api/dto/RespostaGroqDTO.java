package com.harmonia_api.dto;

import java.util.List;

public record RespostaGroqDTO(
    List<Choice> choices
) {

    public record Choice(
        Message message
    ) {}

    public record Message(
        String role,
        String content
    ) {}
}