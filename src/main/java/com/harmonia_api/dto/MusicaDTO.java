package com.harmonia_api.dto;

public record MusicaDTO(

	    String titulo,
	    String artista,
	    String album,
	    String imagemCapa,
	    String urlPreview,
	    String urlSpotify,
	    boolean possuiPreview

	) {}