/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entity.Client;
import entity.Utilisateur;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import session.GestionnaireUtilisateur;

/**
 *
 * @author luchi
 */
@Named(value = "utilisateursMBean")
@ViewScoped
public class UtilisateursMBean implements Serializable {

    @EJB
    private GestionnaireUtilisateur gestionnaireUtilisateur;

    
    /**
     * Creates a new instance of UtilisateursMBean
     */
    public UtilisateursMBean() {
    }
    
        public List<Utilisateur> getUtilisateurs() {
        // gestionnaireDeClient.creerComptesTest();
        gestionnaireUtilisateur.creerUtilisateursDeTest();
        return gestionnaireUtilisateur.getAllUtilisateurs();
    }
    
   

    public String showDetails(int clientId) {
        return "ClientDetails?idClient=" + clientId;
    }
    
}
