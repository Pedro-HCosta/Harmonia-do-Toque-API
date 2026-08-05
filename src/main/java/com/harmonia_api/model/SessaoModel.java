package com.harmonia_api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "tb_sessao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer idade;

    private String sexo;

    private String profissao;

    private Boolean primeiraSessao;

    @Column(columnDefinition = "TEXT")
    private String sintomas;

    @Column(columnDefinition = "TEXT")
    private String descricaoSentimentos;

    @Column(columnDefinition = "TEXT")
    private String objetivoSessao;

    @Column(columnDefinition = "TEXT")
    private String generosFavoritos;

    @Column(columnDefinition = "TEXT")
    private String generosNaoGosta;

    @Column(columnDefinition = "TEXT")
    private String preferenciasMusicais;

    @Column(columnDefinition = "TEXT")
    private String raciocinioIA;

    private String humorIdentificado;

    private Integer bpmMin;

    private Integer bpmMax;

    private LocalDateTime dataSessao;

    @OneToMany(
        mappedBy = "sessao",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<MusicaSessaoModel> musicas = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        dataSessao = LocalDateTime.now();
    }

    public void adicionarMusica(MusicaSessaoModel musica) {
        musica.setSessao(this);
        musicas.add(musica);
    }
}