package com.powersoft.BANK_PROJECT.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.powersoft.BANK_PROJECT.model.Demande;
import com.powersoft.BANK_PROJECT.repository.Demande_Repository;



@Service
public class Demande_Service {
	@Autowired
	Demande_Repository demRepo;
	
	//Ajouter une demande
		public void ajouterDemande(Demande demande) {
			demRepo.save(demande);
		}
		
		//supprimer une Demande
		public void DeletDemande(int id) {
			demRepo.deleteById(id);
		}
		//afficher la liste des demandes
		public List<Demande> AfficherDemande() {
			 return demRepo.findAll();
		}
		
		//rechercher une demande
		public Demande rechercherDemande(int id) {
			return demRepo.findById(id).orElse(null);
		}
		//rechercher une demande en fonction du numéro de la cni
		public Demande rechercherDemandecni(String cni) {
			return demRepo.findByNumcni(cni).orElse(null);
		}
		
		
	
}
