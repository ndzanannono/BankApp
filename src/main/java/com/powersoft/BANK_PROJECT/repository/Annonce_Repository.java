package com.powersoft.BANK_PROJECT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.powersoft.BANK_PROJECT.model.Annonceretrait;

@Repository
public interface Annonce_Repository extends JpaRepository<Annonceretrait, Integer> {

	List<Annonceretrait> findAllByUserIdAndUserUsername(int iduser, String username);

	List<Annonceretrait> findAllByUserId(int iduser);

}
