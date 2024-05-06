package com.powersoft.BANK_PROJECT.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.powersoft.BANK_PROJECT.model.User;
import com.powersoft.BANK_PROJECT.repository.User_Repository;



@Service
public class User_Service {
	@Autowired
	User_Repository usRepo;
	
	//ajouter un User
		public void ajouterUser(User user) {
			usRepo.save(user);
		}
		//supprimer un User
		public void deletUser(int id) {
			usRepo.deleteById(id);
		}
		//mettre à jour les données d'un User
		public void updateUser(User user) {
			if(rechercherUser(user.getId())!=null) {
				usRepo.save(user);
			}
		}
		//rechercher un User 
		public User rechercherUser(int id) {
			return 	usRepo.findById(id).orElse(null);
		}
		//rechercher un User avec le numéro de cni
		public User rechercherUsercni(String cni) {
			return 	usRepo.findByNumcni(cni).orElse(null);
		}
		//rechercher un User en fonction de la cni et du mot de pass
			public User rechercherUsercnipasswordAgence(String cni,String pass) {
				return 	usRepo.findByNumcniAndPassword(cni,pass).orElse(null);
			}
		
		//afficher la liste des Users
		public List<User> afficherUsers(){
			return usRepo.findAll();
		}
		//authentification d'un User pour se connecter
		public User authentification(String cni,String pass) {
		
			return rechercherUsercnipasswordAgence(cni,pass);
				
		}
		
		//authentification pour les transactions
		public boolean rechercherUserpass(String pass) {
			boolean found=false;
			User User=usRepo.findByPassword(pass).orElse(null);
			if(User!=null) {
				found=true;
			}
			return found;
		}
	

}
