package com.harmonia_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harmonia_api.model.SessaoModel;

public interface SessaoRepository extends JpaRepository<SessaoModel, Long> {

	List<SessaoModel> findAllByOrderByDataSessaoDesc();
}