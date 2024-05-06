package com.powersoft.BANK_PROJECT.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.powersoft.BANK_PROJECT.model.Account;
import com.powersoft.BANK_PROJECT.repository.Account_Repository;




@Service
public class Account_Service {

	@Autowired
	Account_Repository acRepo;
	
	//creer un compte
		public void CreerCompte(Account account) {
			acRepo.save(account);
			
		}
		//supprimer un compte
		public void deletCompte(int id) {
			acRepo.deleteById(id);
		}
		//metre à jour un compte
		public void updateCompte(Account account) {
			if(rechercherCompte(account.getIdcompte())!=null) {
				acRepo.save(account);
			}
		}
		//afficher la liste des comptes 
		public List<Account> afficherComptes() {
			return acRepo.findAll();
		}
		
		//rechercher un compte
		public Account rechercherCompte(int id) {
			return acRepo.findById(id).orElse(null);
		}
		
		
		//rechercher le compte d'un utilisateur
		public Account rechercherComptesClient(int id) {
			return acRepo.findByUserId(id);
		}
		//	//rechercher le compte d'un utilisateur avec so numero
		public Account rechercherComptesUtilisateurnumero(String numero) {
			return acRepo.findByNumcompte(numero);
		}
		
		
}
