package com.powersoft.BANK_PROJECT.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.powersoft.BANK_PROJECT.model.Admin;
import com.powersoft.BANK_PROJECT.model.Banque;
import com.powersoft.BANK_PROJECT.model.User;
import com.powersoft.BANK_PROJECT.service.Banque_Service;
import com.powersoft.BANK_PROJECT.service.User_Service;


@Controller
public class Banque_Controller {

	@Autowired
	Banque_Service bServ; 
	@Autowired
	User_Service userService;
	//creer une banque
	@PostMapping("/agenceadd")
	public String creeragence(@RequestParam("nom")String nom,@RequestParam("email")String email,
			@RequestParam("capital")double capital,
			@RequestParam("tauxretrait")double retrait,@RequestParam("tauxdépot")double tauxdépot,
			@RequestParam("logo") MultipartFile logo1,
			RedirectAttributes ra) {
		String fileName=StringUtils.cleanPath(logo1.getOriginalFilename());
         String found="redirect:/";	
		if(bServ.findAll().size()==0) {
			
			
			 try {
					File dir=new File("src/main/resources/static/imagesBanques");
					if(!dir.exists()) {
						dir.mkdirs();
					} 
					File serverFile=new File(dir.getAbsolutePath()+File.separator+fileName);
				
					logo1.transferTo(serverFile);

					Banque banque=new Banque();
					banque.setNom(nom);
			
					banque.setCapital(capital);
					banque.setTauxretrait(retrait);
					banque.setTauxdepot(tauxdépot);
				    banque.setEmail(email);
					banque.setImgbank("imagesBanques/"+fileName);
					bServ.creerBanque(banque);
					
					List<Banque> bq = bServ.findAll();
					if (!bq.isEmpty()) {
						if(userService.rechercherUser(1)==null) {
							Admin admin=new Admin();
							admin.setEmail(bq.get(0).getEmail());
							admin.setUsername(bq.get(0).getNom()+"jeanne");
							admin.setNumcni(admin.getUsername()+"237");
							admin.setPassword(admin.getUsername()+"232");
							userService.ajouterUser(admin);
						}
					} 
					
				}catch(Exception e){
				
				}
			 
			
				}else {
					
					
					
					
					
					
					
					ra.addFlashAttribute("massageb", "banque déjà existante");
					found="redirect:/ajoutbanque";
				}
		return found;
	}
	//supprimer une agence
	@RequestMapping("/agencesup/{id}")
	public void supprimerAgence(@PathVariable("id")int id) {
		bServ.deletBanque(id);
	}
	//afficher la liste des agence
	@RequestMapping("/agences")
	public List<Banque> afficherAgence() {
		return bServ.findAll();
	}
		//mettre a jour les données d'une agence
	@PostMapping("/updateAgence")
	public String updateagence(@RequestParam("id")int id,@RequestParam("email")String email,
			@RequestParam("tauxretrait")double retrait,@RequestParam("tauxdépot")double tauxdépot,
			@RequestParam("logo") MultipartFile logo1,
			RedirectAttributes ra) {
		String fileName=StringUtils.cleanPath(logo1.getOriginalFilename());
		

		 try {
				File dir=new File("src/main/resources/static/imagesBanques");
				if(!dir.exists()) {
					dir.mkdirs();
				} 
				File serverFile=new File(dir.getAbsolutePath()+File.separator+fileName);
			
				logo1.transferTo(serverFile);

				Banque banque=bServ.rechercherBanque(id);
				banque.setId(id);
				banque.setTauxretrait(retrait);
				banque.setTauxdepot(tauxdépot);
				banque.setEmail(email);
			
				banque.setImgbank("imagesBanques/"+fileName);
				bServ.updateBanque(banque);
				
				
				User admin=userService.rechercherUser(1);
				admin.setEmail(banque.getEmail());
				userService.updateUser(admin);
				
				
				
			}catch(Exception e){
			
			}
		 return "redirect:/";
		
	}
}
