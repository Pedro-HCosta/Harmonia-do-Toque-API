package com.harmonia_api.client;

import com.harmonia_api.dto.RespostaBuscaSpotifyDTO;
import com.harmonia_api.dto.RespostaTokenSpotifyDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Component
public class SpotifyClient {

    private final WebClient spotifyWebClient;
    private final WebClient spotifyApiWebClient;

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    public SpotifyClient(
            @Qualifier("spotifyWebClient") WebClient spotifyWebClient,
            @Qualifier("spotifyApiWebClient") WebClient spotifyApiWebClient) {

        this.spotifyWebClient = spotifyWebClient;
        this.spotifyApiWebClient = spotifyApiWebClient;
    }

    public String obterToken() {

        String credenciais = Base64.getEncoder()
                .encodeToString((clientId + ":" + clientSecret).getBytes());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        RespostaTokenSpotifyDTO resposta = spotifyWebClient
                .post()
                .uri("/api/token")
                .header(HttpHeaders.AUTHORIZATION, "Basic " + credenciais)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(RespostaTokenSpotifyDTO.class)
                .block();

        if (resposta == null || resposta.accessToken() == null) {
            throw new RuntimeException("Não foi possível obter o token do Spotify.");
        }

        return resposta.accessToken();
    }

    public RespostaBuscaSpotifyDTO buscar(
            String token,
            String consulta,
            int limite) {

        return spotifyApiWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", consulta)
                        .queryParam("type", "track")
                        .queryParam("limit", limite)
                        .build())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .retrieve()
                .bodyToMono(RespostaBuscaSpotifyDTO.class)
                .block();
    }
}