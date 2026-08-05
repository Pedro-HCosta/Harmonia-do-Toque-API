package com.harmonia_api.mapper;

import org.springframework.stereotype.Component;

import com.harmonia_api.dto.MusicaDTO;
import com.harmonia_api.model.MusicaSessaoModel;
import com.harmonia_api.model.SessaoModel;

@Component
public class MusicaMapper {

    public MusicaSessaoModel toModel(
            MusicaDTO dto,
            SessaoModel sessao) {

        MusicaSessaoModel musica = new MusicaSessaoModel();

        musica.setTitulo(dto.titulo());
        musica.setArtista(dto.artista());
        musica.setAlbum(dto.album());
        musica.setImagemCapa(dto.imagemCapa());
        musica.setUrlPreview(dto.urlPreview());
        musica.setUrlSpotify(dto.urlSpotify());
        musica.setPossuiPreview(dto.possuiPreview());

        musica.setSessao(sessao);

        return musica;
    }

}