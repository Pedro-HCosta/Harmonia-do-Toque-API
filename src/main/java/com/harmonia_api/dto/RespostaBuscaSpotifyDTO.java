package com.harmonia_api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RespostaBuscaSpotifyDTO(
    Tracks tracks
) {

    public record Tracks(
        List<Item> items
    ) {}

    public record Item(
        String name,
        Album album,
        List<Artist> artists,
        @JsonProperty("preview_url")
        String previewUrl,
        @JsonProperty("external_urls")
        ExternalUrls externalUrls
    ) {}

    public record Album(
        String name,
        List<Image> images
    ) {}

    public record Artist(
        String name
    ) {}

    public record Image(
        String url
    ) {}

    public record ExternalUrls(
        String spotify
    ) {}
}