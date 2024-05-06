package com.powersoft.BANK_PROJECT.controller;

import java.util.List;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.powersoft.BANK_PROJECT.model.Account;
import com.powersoft.BANK_PROJECT.model.Admin;
import com.powersoft.BANK_PROJECT.model.Demande;
import com.powersoft.BANK_PROJECT.model.User;
import com.powersoft.BANK_PROJECT.service.Account_Service;
import com.powersoft.BANK_PROJECT.service.Banque_Service;
import com.powersoft.BANK_PROJECT.service.Demande_Service;
import com.powersoft.BANK_PROJECT.service.Email_Service;
import com.powersoft.BANK_PROJECT.service.User_Service;


@Controller
public class User_Controller {

	
	@Autowired
	User_Service userService;
	@Autowired
	Account_Service acService;
	@Autowired
	Banque_Service banqueservice;
	
	@Autowired
	Demande_Service demandeserv;
	@Autowired
	Vue_Controller vuecontroller;
	@Autowired
	Email_Service emailService;
	
	//validation d'une demande
	@GetMapping("/addclient")
	public String ajouterclient(@RequestParam("id")int id,Model model) {
		System.out.println(id);
		Demande demande=new Demande();
		demande= demandeserv.rechercherDemande(id);
		String found=vuecontroller.personnelagence(demande.getBanque().getId(), model);;
		if(demande!=null) {
		
			User client1=userService.rechercherUsercni(demande.getNumcni());
			if(client1==null) {
				System.out.println("okay");
				User client=new User();
				client.setUsername(demande.getUsername());
				
				client.setNumcni(demande.getNumcni());
				client.setEmail(demande.getEmail());
				client.setPassword(demande.getPassword());
				
				userService.ajouterUser(client);
				
				//création de compte
				Account compte=new Account();
				User client2=userService.rechercherUsercni(client.getNumcni());
				compte.setNumcompte(client.getNumcni());
				compte.setSolde(0);
				compte.setStatus(true);
			
				compte.setBanque(banqueservice.findAll().get(0));
				compte.setUser(client2);
				acService.CreerCompte(compte);
				demandeserv.DeletDemande(id);
				
				//envoi du mail mail au client pour dire que la demande a été approuvée
				//et qu'il peut desormais effectuer des transactions via l'application
				
				String subject = " : Creation de compte</span>";
				String  body = "<!DOCTYPE html>\n" +
			              "<html>\n" +
			              "<head>\n" +
			              "  <style>\n" +
			              "    .container {\n" +
			              "      width: 400px;\n" +
			              "      margin: 0 auto;\n" +
			              "      text-align: center;\n" +
			              "      border: 1px solid #000;\n" +
			              "      padding: 20px;\n" +
			              "      background-color: #c2e7fd;\n" +
			              "    }\n" +
			              "    .blue-text {\n" +
			              "      color: blue;\n" +
			              "    }\n" +
			              "    .gray-text {\n" +
			              "      color: gray;\n" +
			              "      font-size: 23px;\n" +
			              "    }\n" +
			              "  </style>\n" +
			              "</head>\n" +
			              "<body>\n" +
			              "  <div class=\"container\">\n" +
			              "    <p><strong> Chère "+client2.getUsername()+"  Votre demande a été approuvée</strong></p>\n" +
			              "    <p class=\"blue-text\">" +" Vous êtes désormais client de la banque"+compte.getBanque().getNom() + "</p>\n" +
			              "    <p>Numero de compte: " + compte.getNumcompte() + "</p>\n" +
			            
			              "    <p><strong class=\"gray-text\">Merci de faire confiance à notre entreprise et d'utiliser notre application</strong></p>\n" +
			              "  </div>\n" +
			              "</body>\n" +
			              "</html>";
				emailService.sendHtmlEmail(client2.getEmail(), subject, body);
				
				
				//retour à admin
				found=vuecontroller.personnelagence(demande.getBanque().getId(), model);
				
			}
			
		}
		return found;
	}
	
	//afficher la liste des utilisateurs
	@RequestMapping("/users")
	public List<User> afficherclients() {
		return userService.afficherUsers();
	}

	
	//authentification d'un client ou d'un personnel de la banque
	@PostMapping("/clientAuthentAgent")
	public String authentificationclient(@RequestParam("pass")String pass,
			@RequestParam("cni")String cni,
			Model model,RedirectAttributes ra){
		 String found="";
		 User client=userService.authentification(cni,pass);
		 
		if(client!=null){
			if(client instanceof Admin) {
				if(client.getNumcni().compareTo(cni)==0 && client.getPassword().compareTo(pass)==0) {
					//annonce
					found=vuecontroller.personnelagence(client.getId(), model);
				
				
				}else{
					
					ra.addFlashAttribute("messageagence","agence inexistante");
					found="redirect:/login";
				}
				
			}else {
				
				if(client.getNumcni().compareTo(cni)==0 && client.getPassword().compareTo(pass)==0) {
					found=vuecontroller.client(client.getId(), model);
				}else {
					ra.addFlashAttribute("messageclient","utilisateur inexistant");
					found="redirect:/login";
				}
			
		}
			
		}else {
			ra.addFlashAttribute("messageagence","données incorrectes");
			found="redirect:/login";
		}
		return found;
	}
	
	
}
