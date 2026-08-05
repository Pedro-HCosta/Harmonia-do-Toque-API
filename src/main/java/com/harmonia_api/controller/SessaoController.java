package com.harmonia_api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harmonia_api.dto.FichaDTO;
import com.harmonia_api.dto.RespostaSessaoDTO;
import com.harmonia_api.service.SessaoService;

@RestController
@RequestMapping("/api/sessao")
public class SessaoController {

    private final SessaoService sessaoService;

    public SessaoController(SessaoService sessaoService) {
        this.sessaoService = sessaoService;
    }

    @PostMapping("/analisar")
    public ResponseEntity<RespostaSessaoDTO> analisar(
            @RequestBody FichaDTO ficha) {

        RespostaSessaoDTO resposta =
                sessaoService.analisarSessao(ficha);

        return ResponseEntity.ok(resposta);
    }

}