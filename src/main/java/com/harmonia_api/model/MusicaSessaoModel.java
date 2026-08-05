package com.harmonia_api.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_musica_sessao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicaSessaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String artista;

    private String album;

    @Column(length = 1000)
    private String imagemCapa;

    @Column(length = 1000)
    private String urlPreview;

    @Column(nullable = false, length = 1000)
    private String urlSpotify;

    @Column(nullable = false)
    private Boolean possuiPreview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sessao_id", nullable = false)
    private SessaoModel sessao;
}