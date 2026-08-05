package com.harmonia_api.dto;

import java.util.List;

public record AnaliseIADTO(
    String raciocinio,
    String humorIdentificado,
    Integer bpmMin,
    Integer bpmMax,
    List<String> generos,
    List<String> artistas,
    List<String> palavrasChave
) {}