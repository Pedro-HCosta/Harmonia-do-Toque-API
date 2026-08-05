package com.harmonia_api.dto;

import java.util.List;

import com.harmonia_api.model.enums.Sexo;

public record FichaDTO(
    String nome,
    Integer idade,
    Sexo sexo,
    String profissao,
    Boolean primeiraSessao,
    List<String> sintomas,
    String descricaoSentimentos,
    String objetivoSessao,
    List<String> generosFavoritos,
    List<String> generosNaoGosta,
    String preferenciasMusicais
) {}	