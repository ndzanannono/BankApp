package com.powersoft.BANK_PROJECT.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.powersoft.BANK_PROJECT.model.Account;
import com.powersoft.BANK_PROJECT.model.Annonceretrait;
import com.powersoft.BANK_PROJECT.service.Account_Service;
import com.powersoft.BANK_PROJECT.service.Annonce_Service;

@Controller
public class Annonce_Controller {
	
	@Autowired
	Account_Service acService;
	@Autowired
	Annonce_Service anService;
	
	
	//creer une anonce pour retrait
		public void creeranonceretrait(String numAgent,String numbenef,double somme) {
			Model model = null;
			RedirectAttributes ra = null;
			Annonceretrait anonce=new Annonceretrait();
			Account compteagent=acService.rechercherComptesUtilisateurnumero(numAgent);
			Account comptedest=acService.rechercherComptesUtilisateurnumero(numbenef);
			
			anonce.setUser(comptedest.getUser());
			anonce.setNumdest(numAgent);
			anonce.setSomme(somme);
			anonce.setTitre(comptedest.getBanque().getNom()+" :"+" Attention: ");
			anonce.setDescription(comptedest.getUser().getUsername()+" :"
					+ "vous allez effectué un retrait de "+somme+" FCFA"+ " de votre compte "+comptedest.getBanque().getNom()+
					" par "+ compteagent.getUser().getUsername()+" "+ " . Cliquer pour valider ou annuler");
			
			anService.AjouterAnnonce(anonce);
			
		}

		
	//afficher la liste des annonces d'un client
		public List<Annonceretrait> afficheranonceclient(int idclient) {
			return anService.rechercherAnnoncesclient(idclient);	
		}
		//afficher les annonces de retrait d'un client
		public List<Annonceretrait> afficheranonceretraitclient(int idclient) {
		
			return anService.rechercherAnnoncesretraitclient(idclient);	
		}
		
		
		
	

}
