package com.harmonia_api.service;

import com.harmonia_api.client.SpotifyClient;
import com.harmonia_api.dto.AnaliseIADTO;
import com.harmonia_api.dto.MusicaDTO;
import com.harmonia_api.dto.RespostaBuscaSpotifyDTO;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpotifyService {

    private final SpotifyClient spotifyClient;

    public SpotifyService(SpotifyClient spotifyClient) {
        this.spotifyClient = spotifyClient;
    }

    public java.util.List<MusicaDTO> buscarMusicas(AnaliseIADTO analise) {

        String token = spotifyClient.obterToken();

        Map<String, MusicaDTO> musicas = new LinkedHashMap<>();

        analise.artistas()
                .stream()
                .limit(2)
                .forEach(artista ->
                        adicionarResultado(
                                musicas,
                                spotifyClient.buscar(
                                        token,
                                        "artist:\"" + artista + "\"",
                                        5
                                )
                        )
                );

        String consulta = montarConsulta(analise);

        adicionarResultado(
                musicas,
                spotifyClient.buscar(
                        token,
                        consulta,
                        10
                )
        );

        return musicas.values()
                .stream()
                .limit(10)
                .toList();
    }

    private String montarConsulta(AnaliseIADTO analise) {

        String generos = analise.generos()
                .stream()
                .limit(3)
                .collect(Collectors.joining(" "));

        String palavras = analise.palavrasChave()
                .stream()
                .limit(5)
                .collect(Collectors.joining(" "));

        return generos + " " + palavras;
    }

    private void adicionarResultado(
            Map<String, MusicaDTO> musicas,
            RespostaBuscaSpotifyDTO resposta) {

        if (resposta == null
                || resposta.tracks() == null
                || resposta.tracks().items() == null) {
            return;
        }

        for (RespostaBuscaSpotifyDTO.Item track : resposta.tracks().items()) {

            String artistas = track.artists()
                    .stream()
                    .map(RespostaBuscaSpotifyDTO.Artist::name)
                    .collect(Collectors.joining(", "));

            String capa = null;

            if (track.album() != null
                    && track.album().images() != null
                    && !track.album().images().isEmpty()) {

                capa = track.album().images().getFirst().url();
            }

            MusicaDTO musica = new MusicaDTO(
                    track.name(),
                    artistas,
                    track.album().name(),
                    capa,
                    track.previewUrl(),
                    track.externalUrls().spotify(),
                    track.previewUrl() != null
            );

            musicas.putIfAbsent(
                    musica.urlSpotify(),
                    musica
            );

            if (musicas.size() >= 10) {
                return;
            }
        }
    }
}