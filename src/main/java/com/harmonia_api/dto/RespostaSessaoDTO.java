package com.harmonia_api.dto;

import java.util.List;

public record RespostaSessaoDTO(
    String raciocinio,
    String humorIdentificado,
    String faixaBpm,
    List<String> generosRecomendados,
    List<MusicaDTO> musicas
) {}