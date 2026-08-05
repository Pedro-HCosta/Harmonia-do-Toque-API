package com.harmonia_api.mapper;

import org.springframework.stereotype.Component;

import com.harmonia_api.dto.AnaliseIADTO;
import com.harmonia_api.dto.FichaDTO;
import com.harmonia_api.model.SessaoModel;

@Component
public class SessaoMapper {

	public SessaoModel toModel(
	        FichaDTO ficha,
	        AnaliseIADTO analise) {

	    SessaoModel sessao = new SessaoModel();

	    sessao.setNome(ficha.nome());
	    sessao.setIdade(ficha.idade());
	    sessao.setSexo(ficha.sexo().toString());
	    sessao.setProfissao(ficha.profissao());

	    sessao.setPrimeiraSessao(ficha.primeiraSessao());

	    sessao.setSintomas(
	            String.join(", ", ficha.sintomas()));

	    sessao.setDescricaoSentimentos(
	            ficha.descricaoSentimentos());

	    sessao.setObjetivoSessao(
	            ficha.objetivoSessao());

	    sessao.setGenerosFavoritos(
	            String.join(", ", ficha.generosFavoritos()));

	    sessao.setGenerosNaoGosta(
	            String.join(", ", ficha.generosNaoGosta()));

	    sessao.setPreferenciasMusicais(
	            ficha.preferenciasMusicais());

	    sessao.setRaciocinioIA(
	            analise.raciocinio());

	    sessao.setHumorIdentificado(
	            analise.humorIdentificado());

	    sessao.setBpmMin(
	            analise.bpmMin());

	    sessao.setBpmMax(
	            analise.bpmMax());

	    return sessao;
	}

}