package com.powersoft.BANK_PROJECT.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.powersoft.BANK_PROJECT.model.Annonceretrait;
import com.powersoft.BANK_PROJECT.repository.Annonce_Repository;

@Service
public class Annonce_Service {
	
	@Autowired
	Annonce_Repository anonce_repo;
	
	@Autowired
	User_Service userService;
	//ajouter une annonce
	public void AjouterAnnonce(Annonceretrait annonce) {
		anonce_repo.save(annonce);
	}

	//supprimer une annonce
	public void deletAnnonce(int id) {
			anonce_repo.deleteById(id);
	}
	// mettre à jour une annonce
	public void UpdateAnnonce(Annonceretrait annonce) {
		anonce_repo.save(annonce);
	}
//rechercher une annonce
	public Annonceretrait rechercherAnnonce(int id) {
		return anonce_repo.findById(id).orElse(null);
	}
	//afficher tous les annonces
	public List<Annonceretrait> afficherAnnonces() {
		return anonce_repo.findAll();
	}
	//afficher les annonces liées à un client
	public List<Annonceretrait> rechercherAnnoncesclient(int iduser) {
		return anonce_repo.findAllByUserId(iduser);
	}


	public List<Annonceretrait> rechercherAnnoncesretraitclient(int iduser) {
		// TODO Auto-generated method stub
		return anonce_repo.findAllByUserIdAndUserUsername(iduser,userService.rechercherUser(iduser).getUsername());
	}
	
	

}
