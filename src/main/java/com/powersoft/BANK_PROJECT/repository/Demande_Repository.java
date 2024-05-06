package com.powersoft.BANK_PROJECT.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.powersoft.BANK_PROJECT.model.Demande;


@Repository
public interface Demande_Repository extends JpaRepository<Demande, Integer>{

	Optional<Demande> findByNumcni(String cni);

}
