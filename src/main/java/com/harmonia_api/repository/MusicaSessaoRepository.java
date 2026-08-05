package com.harmonia_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.harmonia_api.model.MusicaSessaoModel;

public interface MusicaSessaoRepository extends JpaRepository<MusicaSessaoModel, Long> {

    List<MusicaSessaoModel> findBySessaoId(Long sessaoId);

}