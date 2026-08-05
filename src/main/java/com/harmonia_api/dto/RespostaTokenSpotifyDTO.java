package com.harmonia_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RespostaTokenSpotifyDTO(
    @JsonProperty("access_token")
    String accessToken,

    @JsonProperty("token_type")
    String tokenType,

    @JsonProperty("expires_in")
    Integer expiresIn
) {}