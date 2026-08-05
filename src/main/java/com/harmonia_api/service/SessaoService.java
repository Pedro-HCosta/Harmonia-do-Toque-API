package com.harmonia_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.harmonia_api.dto.AnaliseIADTO;
import com.harmonia_api.dto.FichaDTO;
import com.harmonia_api.dto.MusicaDTO;
import com.harmonia_api.dto.RespostaSessaoDTO;
import com.harmonia_api.mapper.MusicaMapper;
import com.harmonia_api.mapper.SessaoMapper;
import com.harmonia_api.model.MusicaSessaoModel;
import com.harmonia_api.model.SessaoModel;
import com.harmonia_api.repository.SessaoRepository;

@Service
public class SessaoService {

    private final GroqService groqService;
    private final SpotifyService spotifyService;

    private final SessaoRepository sessaoRepository;

    private final SessaoMapper sessaoMapper;
    private final MusicaMapper musicaMapper;

    public SessaoService(
            GroqService groqService,
            SpotifyService spotifyService,
            SessaoRepository sessaoRepository,
            SessaoMapper sessaoMapper,
            MusicaMapper musicaMapper) {

        this.groqService = groqService;
        this.spotifyService = spotifyService;
        this.sessaoRepository = sessaoRepository;
        this.sessaoMapper = sessaoMapper;
        this.musicaMapper = musicaMapper;
    }

    @Transactional
    public RespostaSessaoDTO analisarSessao(FichaDTO ficha) {

        AnaliseIADTO analise = groqService.analisarFicha(ficha);

        List<MusicaDTO> musicas =
                spotifyService.buscarMusicas(analise);

        SessaoModel sessao =
                sessaoMapper.toModel(ficha, analise);

        for (MusicaDTO musicaDTO : musicas) {

            MusicaSessaoModel musica =
                    musicaMapper.toModel(musicaDTO, sessao);

            sessao.adicionarMusica(musica);
        }

        sessaoRepository.save(sessao);

        return new RespostaSessaoDTO(
                analise.raciocinio(),
                analise.humorIdentificado(),
                analise.bpmMin() + " - " + analise.bpmMax() + " BPM",
                analise.generos(),
                musicas
        );
    }
}