package com.powersoft.BANK_PROJECT.controller;

import java.io.File;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.powersoft.BANK_PROJECT.model.Banque;
import com.powersoft.BANK_PROJECT.model.Demande;
import com.powersoft.BANK_PROJECT.service.Demande_Service;



@Controller
public class Demande_Controller {

	
	@Autowired
	Demande_Service demandeserv;
	
	//ajouter une demande
	@PostMapping("/demandeadd") 
	public String ajouterDemande(@RequestParam("username")String username,
			@RequestParam("cni")String cni1,
			@RequestParam("pass")String password1,@RequestParam("email")String email1,@RequestParam("idagence")int idagence,
			
			@RequestParam("verso_cni")MultipartFile versocni,
			@RequestParam("recto_cni")MultipartFile rectocni, RedirectAttributes ra) {
		Demande demande1= demandeserv.rechercherDemandecni(cni1);
		String found="redirect:/inscription";;
		if(demande1==null) {
			 String  fileName= StringUtils.cleanPath(versocni.getOriginalFilename());
			 String  fileName1= StringUtils.cleanPath(rectocni.getOriginalFilename());
			   try {
					File dir=new File("src/main/resources/static/imagesdemande");
					if(!dir.exists()) {
						dir.mkdirs();
					} File serverFile=new File(dir.getAbsolutePath()+File.separator+fileName);
					File serverFile1=new File(dir.getAbsolutePath()+File.separator+fileName1);
					versocni.transferTo(serverFile);
					rectocni.transferTo(serverFile1);
					Demande demande=new Demande();
					
					Banque bank=new Banque();
					bank.setId(idagence);
					
					
					demande.setUsername(username);
					demande.setNumcni(cni1);
					demande.setPassword(password1);
					demande.setEmail(email1);
					demande.setBanque(bank);
					
					demande.setRecto("imagesdemande/"+fileName1);
					demande.setVerso("imagesdemande/"+fileName);
					
					
					demandeserv.ajouterDemande(demande);
			
					found="redirect:/";
				}catch(Exception e){
				
				}
			
		}else {
				   ra.addFlashAttribute("message","la demande existe déjà sur votre cni pour l'opérateur  ");
			   }
		
		return found;
	}
	//afficher la liste des demandes
	
	@RequestMapping("/demandes")
	public List<Demande> afficherdemandes() {
		return demandeserv.AfficherDemande();
	}
	//supprimer une demande
	@GetMapping("/demandesup")
	public void deletdemande(@RequestParam("id")int id) {
		demandeserv.DeletDemande(id);
	}

}
