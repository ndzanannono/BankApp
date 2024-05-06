package com.powersoft.BANK_PROJECT.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.powersoft.BANK_PROJECT.model.Account;
import com.powersoft.BANK_PROJECT.model.Admin;
import com.powersoft.BANK_PROJECT.model.Banque;
import com.powersoft.BANK_PROJECT.model.User;
import com.powersoft.BANK_PROJECT.service.Account_Service;
import com.powersoft.BANK_PROJECT.service.Annonce_Service;
import com.powersoft.BANK_PROJECT.service.Banque_Service;
import com.powersoft.BANK_PROJECT.service.Demande_Service;
import com.powersoft.BANK_PROJECT.service.Email_Service;
import com.powersoft.BANK_PROJECT.service.User_Service;



@Controller
public class Vue_Controller {

	@Autowired
	Email_Service emailservice;
	@Autowired
	Banque_Controller bqController;
	
	@Autowired
	Banque_Service bqService;
	@Autowired
	Annonce_Controller anonceController;
	@Autowired
	Account_Service acService;
	@Autowired
	Demande_Service demService;
	@Autowired
	User_Service userService;
	@Autowired
	Annonce_Service anonceService;
	@Autowired
	Email_Service emailService;
	
	//page d'accueil
		@GetMapping("/")
		public String Acceuil(Model model) {
			
			
			model.addAttribute("Agence",bqController.afficherAgence());
			return "/web/index.html";
			
		}
	
		//connection
		@RequestMapping("/login")
		public String login(Model model) {
			model.addAttribute("agence",bqController.afficherAgence());
	       return "/web/login.html";		
			
		}
		//inscription
			@GetMapping("/inscription")
			public String demande(Model model) {
				model.addAttribute("agence",bqController.afficherAgence());
				return"/web/register";
				
			}
			//ajout d'une banque
					@GetMapping("/ajoutbanque")
					public String ajoutbanques(Model model) {
					
						return"/web/ajoutdesbanques";
						
					}
					//chargemen interface client
					@GetMapping("/interfaceclient")
					public String client(int id,Model model) {
						
						//compte client
						model.addAttribute("annonceretrait", anonceController.afficheranonceretraitclient(id));

						model.addAttribute("compteclient",acService.rechercherComptesClient(id));
						
						return"/web/User";
						
					}
					@GetMapping("/interfaceperso")
					public String personnelagence(int id,Model model) {
						
						//les clients de l'agence
						model.addAttribute("listeclients", afficherclientsAgence(id));
						//tous les comptes créés dans l'agence
						model.addAttribute("compteagence", acService.afficherComptes());
						//les demandes de l'agence
						model.addAttribute("demandesagence",demService.AfficherDemande());
						//les donnee de lagence
						model.addAttribute("agence", bqService.rechercherBanque(id));
						return"/web/Admin";
						
					}
					//mise à jour des données de lagence
					@GetMapping("/updateagence")
					public String updatepersonnelagence(@RequestParam("id") int id,Model model) {
						
						//les clients de l'agence
						model.addAttribute("listeclients", afficherclientsAgence(id));
						//tous les comptes créés dans l'agence
						model.addAttribute("compteagence", acService.afficherComptes());
						//les demandes de l'agence
						model.addAttribute("demandesagence",demService.AfficherDemande());
						//les donnee de lagence
						model.addAttribute("agence", bqService.rechercherBanque(id));
						return"/web/updateagence";
						
					}
					//afficher la liste des clients d'une agence
					public List<User> afficherclientsAgence(int idagence) {
						return userService.afficherUsers();
					}
					
					//supprimer une annonce coté client
					@GetMapping("/anoncesupcl")
					public String deletAnonceclient(@RequestParam("id")int id,Model model) {
						int idclient=anonceService.rechercherAnnonce(id).getUser().getId();
						anonceService.deletAnnonce(id);
						return client(idclient, model);
						
					}
					//envoyer un message à un client
					@PostMapping("/messageclient")
					public String envoimessage(@RequestParam("numbenef")  String numbenef,@RequestParam("description")String descripion,
							@RequestParam("titre")String titre,Model model ) {
						Account comptedest=new Account();
						 comptedest=acService.rechercherComptesUtilisateurnumero(numbenef);
						
						emailService.sendHtmlEmail(comptedest.getUser().getEmail(), titre, descripion);
						
						return personnelagence(comptedest.getBanque().getId(), model);
						
					}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
