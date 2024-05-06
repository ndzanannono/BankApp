package com.powersoft.BANK_PROJECT.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.powersoft.BANK_PROJECT.model.Account;
import com.powersoft.BANK_PROJECT.model.Annonceretrait;
import com.powersoft.BANK_PROJECT.model.Banque;
import com.powersoft.BANK_PROJECT.service.Account_Service;
import com.powersoft.BANK_PROJECT.service.Annonce_Service;
import com.powersoft.BANK_PROJECT.service.Banque_Service;
import com.powersoft.BANK_PROJECT.service.Email_Service;

@Controller
public class Account_Controller {

	@Autowired
	Account_Service acService;
	@Autowired
	Banque_Service bqService;
	@Autowired
	Annonce_Controller annonceController;
	@Autowired
	Email_Service emailService;
	@Autowired
	Vue_Controller vucontro;
	//afficher les comptes
	@RequestMapping("/Accounts")
	public List<Account> AfficherCompte(){
		return acService.afficherComptes();
			
	}
	
	//depot ou transfert d'argent
	@PostMapping("/depot")
	public String depot(@RequestParam("numéroAgent")String numAgent,@RequestParam("numéroDestinataire")String numDest,
			@RequestParam("somme")double somme,Model model,RedirectAttributes ra) {
		Account compteAgent=acService.rechercherComptesUtilisateurnumero(numAgent);
		Account compteDest=acService.rechercherComptesUtilisateurnumero(numDest);
		
		double fraisDepot=compteAgent.getBanque().getTauxdepot()*somme/100;
		
		String found=vucontro.client(compteAgent.getUser().getId(), model);
		
		//verifier si l'expediteur a assez d'argent
		if(compteAgent.getSolde()>=somme+fraisDepot) {
			//debiter le copte de l'expediteur
			compteAgent.setSolde(compteAgent.getSolde()-(somme+fraisDepot));
			
			//crediter le compte du destinataire
			compteDest.setSolde(compteDest.getSolde()+somme);
			
			//envoie du benefice à l'agence
		Banque bank=compteDest.getBanque();
		bank.setCapital(bank.getCapital()+fraisDepot);
		//mise à jour des comptes
		
		acService.updateCompte(compteDest);
		acService.updateCompte(compteAgent);
		bqService.updateBanque(bank);
		
		//envoie des notifications
		
		//à l'expediteur
		String subject = compteAgent.getBanque().getNom() + " : Dépot d'argent";
		String body = "<!DOCTYPE html>\n" +
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
	              "    <p><strong>Vous avez effectué un dépôt de</strong></p>\n" +
	              "    <p class=\"blue-text\">" + somme + " FCFA" + "</p>\n" +
	              "    <p>Vers le compte de Monsieur " + compteDest.getUser().getUsername() + "</p>\n" +
	              "    <p>Votre nouveau solde est de : " + compteAgent.getSolde() + " FCFA " + "</p>\n" +
	              "    <p>Frais : " + fraisDepot +" FCFA"+ "</p>\n" +
	              "    <p><strong class=\"gray-text\">Merci de faire confiance à notre entreprise et d'utiliser notre application</strong></p>\n" +
	              "  </div>\n" +
	              "</body>\n" +
	              "</html>";

		
		emailService.sendHtmlEmail(compteAgent.getUser().getEmail(), subject, body);
		
		//au destinataire
		 body = "<!DOCTYPE html>\n" +
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
	              "    <p><strong>Vous avez reçu un dépôt de</strong></p>\n" +
	              "    <p class=\"blue-text\">" + somme + " FCFA" + "</p>\n" +
	              "    <p>De la part de Monsieur " + compteAgent.getUser().getUsername() + " sur votre compte " + compteDest.getBanque().getNom() + "</p>\n" +
	              "    <p>Votre nouveau solde est de : " + compteDest.getSolde() +"FCFA"+ "</p>\n" +
	              "    <p><strong class=\"gray-text\">Merci de faire confiance à notre entreprise et d'utiliser notre application</strong></p>\n" +
	              "  </div>\n" +
	              "</body>\n" +
	              "</html>";

		emailService.sendHtmlEmail(compteDest.getUser().getEmail(), subject, body);
		
		//à la banque
		
		 body = "<!DOCTYPE html>\n" +
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
	              "    <p class=\"blue-text\">Dépôt de " + somme + " FCFA</p>\n" +
	              "    <p>Effectué par Monsieur " + compteAgent.getUser().getUsername() + " de son compte " + compteDest.getBanque().getNom() + "</p>\n" +
	              "    <p>Vers le compte de " + compteDest.getUser().getUsername() + "</p>\n" +
	              "    <p>Frais : " + fraisDepot +" FCFA"+ "</p>\n" +
	              "    <p>Nouveau capital " + bank.getCapital() +" FCFA"+ "</p>\n" +
	              "    <p><strong class=\"gray-text\">Merci de faire confiance à notre entreprise et d'utiliser notre application</strong></p>\n" +
	              "  </div>\n" +
	              "</body>\n" +
	              "</html>";

	emailService.sendHtmlEmail(compteDest.getBanque().getEmail(), subject, body);
	
		
			
		}else {
			ra.addFlashAttribute("messagedepotagent2","pas assez d'argent");
		}
		
		return found;
	}
	
	
	//lancer un retrait
	@PostMapping("/retrait")
	public String retraitlancer(@RequestParam("numéroAgent")String numAgent,@RequestParam("numéroDestinataire")String numDest,
			@RequestParam("somme")double somme,Model model,RedirectAttributes ra) {
		
		Account compteAgent=acService.rechercherComptesUtilisateurnumero(numAgent);
		Account compteDest=acService.rechercherComptesUtilisateurnumero(numDest);
		//frais de retrait
		double fraisRetrait=compteDest.getBanque().getTauxretrait()*somme/100;
		String found=vucontro.client(compteAgent.getUser().getId(), model);
		//verifier si re recepteur a assez d'argent
		if(compteDest.getSolde()>=somme+fraisRetrait) {
			//Envoi du message au destinataire pour qu'il valide
			String validationLink = "http://localhost:8081/" + compteAgent.getUser().getId();
			String subject =  compteDest.getBanque().getNom() + " : Attention! retrait d'argent</span>";
			String body = "<!DOCTYPE html>\n" +
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
		              "    <p><strong>Un retrait de </strong></p>\n" +
		              "    <p class=\"blue-text\">" + somme + " FCFA" + "</p>\n" +
		              "<p>a été lancé  sur votre compte " + compteDest.getBanque().getNom() + "</p>\n" +
		              "    <p>Par Monsieur " + compteAgent.getUser().getUsername() + "</p>\n" +
		              "    <p>Frais : " + fraisRetrait+" FCFA" + "</p>\n" +
		              "    <p><a href=\"" + validationLink + "\">Cliquez ici pour valider ou annuler</a></p>\n" +
		              "    <p><strong class=\"gray-text\">Merci de faire confiance à notre entreprise et d'utiliser notre application</strong></p>\n" +
		              "  </div>\n" +
		              "</body>\n" +
		              "</html>";

			emailService.sendHtmlEmail(compteDest.getUser().getEmail(), subject, body);
			
			
			
			
			
			//enegistrement de la notification
			annonceController.creeranonceretrait(numAgent, numDest, somme);
		
			
		}else {
			ra.addFlashAttribute("messagedepotagent2","pas assez d'argent");
		}
		
		return found;
		
	}
	
	
	
	//validation du retrait
			@GetMapping("/validretrait")
			public String validationretrait(@RequestParam("numAgent")String numAgent,
					@RequestParam("numbenef")String numbenef,
					@RequestParam("somme")double somme,@RequestParam("idanonce")int idanonce ,Model model,RedirectAttributes ra) {
			
				Account compteagent=acService.rechercherComptesUtilisateurnumero(numAgent);
				Account comptedest=acService.rechercherComptesUtilisateurnumero(numbenef);
				String found=vucontro.deletAnonceclient(idanonce, model);
				
					Banque bank=compteagent.getBanque();
					// retrait
					double fraisRetrait=somme*comptedest.getBanque().getTauxretrait()/100;
					if(comptedest.getSolde()>=somme+fraisRetrait) {
						//debiter le compte du destinataire
						comptedest.setSolde(comptedest.getSolde()-(somme+fraisRetrait));
						acService.updateCompte(comptedest);
						//crediter le compte de l'agent
						
						compteagent.setSolde(compteagent.getSolde()+(somme+(fraisRetrait*45/100)));
						acService.updateCompte(compteagent);
						
						//envoie du benefice à l'agence
						
						bank=comptedest.getBanque();
						bank.setCapital(bank.getCapital()+(fraisRetrait*55/100));
						 bqService.updateBanque(bank);
						
						//envoie de la notification à l'agent pour dire que le retrait a été autorisé ou pas
						//(celui qui est charger de donner de l'argent)
						String subject = compteagent.getBanque().getNom() + " : Retrait d'argent</span>";
						String body = "<!DOCTYPE html>\n" +
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
					              "    <p><strong>Un Retrait de  </strong></p>\n" +
					              "    <p class=\"blue-text\">" + somme + " FCFA" + "</p>\n" +
					             
					              "    <p>a  éte autorisé par  Monsieur " + comptedest.getUser().getUsername() + " sur votre compte " + compteagent.getBanque().getNom() + "</p>\n" +
					              "    <p>Votre nouveau solde est de : " + compteagent.getSolde() +" FCFA"+"</p>\n" +
					             " <p>Frais : " + fraisRetrait +" FCFA"+"</p>\n" +
					              "    <p><strong class=\"gray-text\">Merci de faire confiance à notre entreprise et d'utiliser notre application</strong></p>\n" +
					              "  </div>\n" +
					              "</body>\n" +
					              "</html>";
                        emailService.sendHtmlEmail(compteagent.getUser().getEmail(), subject, body);
						
						//envoie de la notification de la transaction au client (celui qui est charger de recevoir de l'argent)
						
                         body = "<!DOCTYPE html>\n" +
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
					              "    <p><strong>Vous avez éffectué un retrait de   </strong></p>\n" +
					              "    <p class=\"blue-text\">" + somme + " FCFA" + "</p>\n" +
					              "    <p> sur votre compte " + comptedest.getBanque().getNom() + "</p>\n" +
					              "    <p>Transaction initiée par " + compteagent.getUser().getUsername() + "</p>\n" +
					              "    <p>Votre nouveau solde est de : " + comptedest.getSolde() +" FCFA"+"</p>\n" +
					             " <p>Frais : " + fraisRetrait +" FCFA"+"</p>\n" +
					              "    <p><strong class=\"gray-text\">Merci de faire confiance à notre entreprise et d'utiliser notre application</strong></p>\n" +
					              "  </div>\n" +
					              "</body>\n" +
					              "</html>";
						emailService.sendHtmlEmail(comptedest.getUser().getEmail(), subject, body);
						
						//envoie de l'email à l'agence
						
						 body = "<!DOCTYPE html>\n" +
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
					              "    <p class=\"blue-text\">Retrait de  " + somme + " FCFA</p>\n" +
					              "    <p>Effectué par Monsieur " + comptedest.getUser().getUsername() + " de son compte " + comptedest.getBanque().getNom() + "</p>\n" +
					              "    <p>Transaction initiée par  " + compteagent.getUser().getUsername() + "</p>\n" +
					              "    <p>Votre nouveau capital est de : " + bank.getCapital() +" FCFA"+ "</p>\n" +
					              "    <p>Frais : " + fraisRetrait +" FCFA"+ "</p>\n" +
					              "    <p><strong class=\"gray-text\">Merci de faire confiance à notre entreprise et d'utiliser notre application</strong></p>\n" +
					              "  </div>\n" +
					              "</body>\n" +
					              "</html>";

						 emailService.sendHtmlEmail(comptedest.getBanque().getEmail(), subject, body);
                         
                         
					}
				return found;
						
			}
			//dépôt d'argent sur le compte d'un client par l'agence
			@PostMapping("/depotag")
			public String depotagenceclient(@RequestParam("numéroDestinataire")String numDest,
					@RequestParam("somme")double somme,Model model,RedirectAttributes ra) {
				
				
				Account comptedest=acService.rechercherComptesUtilisateurnumero(numDest);
				Banque agence1=comptedest.getBanque();
				Annonceretrait annonce=new Annonceretrait();
				Annonceretrait annonce1=new Annonceretrait();
				String found=vucontro.personnelagence(agence1.getId(), model);;
				
				
				double encour=agence1.getCapital();
			
				
				if(encour>somme) {
					//envoi d'argent compte client
					comptedest.setSolde(comptedest.getSolde()+somme);
					//debiter le compte de l'agence
					agence1.setCapital(encour-somme);
				
					//mise à jour
					bqService.updateBanque(agence1);
					acService.updateCompte(comptedest);
					
					//envoi d'un rapport au destinataire
					String subject = comptedest.getBanque().getNom() + " : Dépot d'argent</span>";
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
				              "    <p><strong>Vous avez reçu un dépôt de</strong></p>\n" +
				              "    <p class=\"blue-text\">" + somme + " FCFA" + "</p>\n" +
				              "    <p>De la part de  " + comptedest.getBanque().getNom() + "</p>\n" +
				              "    <p>Votre nouveau solde est de : " + comptedest.getSolde() + "FCFA"+"</p>\n" +
				              "    <p><strong class=\"gray-text\">Merci de faire confiance à notre entreprise et d'utiliser notre application</strong></p>\n" +
				              "  </div>\n" +
				              "</body>\n" +
				              "</html>";
					emailService.sendHtmlEmail(comptedest.getUser().getEmail(), subject, body);
					
					//envoi d'un rapport à la banque 
					body = "<!DOCTYPE html>\n" +
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
				              "    <p><strong>Dépôt de :</strong></p>\n" +
				              "    <p class=\"blue-text\">" + somme + " FCFA" + "</p>\n" +
				              "    <p>sur le compte de  " + comptedest.getUser().getUsername() + "</p>\n" +
				              "    <p>Votre nouveau capital est de : " + agence1.getCapital() +" FCFA"+ "</p>\n" +
				              "    <p>Frais : " + 0 +" FCFA"+ "</p>\n" +
				              "    <p><strong class=\"gray-text\">Merci de faire confiance à notre entreprise et d'utiliser notre application</strong></p>\n" +
				              "  </div>\n" +
				              "</body>\n" +
				              "</html>";
					
					emailService.sendHtmlEmail(agence1.getEmail(), subject, body);
					
				}else {
					ra.addFlashAttribute("messagedepoagent", "pas assez d'argent");
				}
				
				return found;
			}
			
	
}
