package com.powersoft.BANK_PROJECT.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.powersoft.BANK_PROJECT.model.Banque;
import com.powersoft.BANK_PROJECT.repository.Banque_Repository;






@Service
public class Banque_Service {
	
	@Autowired
	Banque_Repository banqRepo;
	//creer une Banque
		public void creerBanque(Banque banque) {
			banqRepo.save(banque);
		}
		
		//recuperer les banques;
		public List<Banque> findAll() {
			return banqRepo.findAll();
		}
		
		//suprimer une Banque
		public void deletBanque(int id) {
			banqRepo.deleteById(id);
		}
		//mettre à jour une Banque
		public void updateBanque(Banque banque) {
			banqRepo.save(banque);
		}
		//rechercher une Banque
		public Banque rechercherBanque(int id) {
			return banqRepo.findById(id).orElse(null);
		}

}
