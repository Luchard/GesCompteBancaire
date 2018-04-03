/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entity.Client;
import entity.TypeUtilisateur;
import entity.Utilisateur;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import services.Util;
import session.GestionnaireDeClient;

/**
 *
 * @author luchi
 */
@Named(value = "clientMBean")
@ViewScoped
public class ClientMBean implements Serializable {

    @EJB
    private GestionnaireDeClient gestionnaireDeClient;

    private int nombreClient;

    public int getNombreClient() {   
        HttpSession session = Util.getSession();
        Utilisateur u = (Utilisateur) session.getAttribute("Utilisateur");
        if (u.getTypeUtilisateur() == TypeUtilisateur.CLIENT) {
            return 1;
        } else {
            return (gestionnaireDeClient.getAllClients()).size();
        }
        
        
        
       
    }

    public void setNombreClient(int nombreClient) {
        this.nombreClient = nombreClient;
    }

    /**
     * Creates a new instance of ClientMBean
     */
    public ClientMBean() {
    }

    public List<Client> getClients() {
        // gestionnaireDeClient.creerComptesTest();

        HttpSession session = Util.getSession();
        Utilisateur u = (Utilisateur) session.getAttribute("Utilisateur");
        if (u.getTypeUtilisateur() == TypeUtilisateur.CLIENT) {
            return gestionnaireDeClient.getAllClientsById(u.getClient().getId());
        } else {
            return gestionnaireDeClient.getAllClients();
        }

    }

    public String showDetails(int clientId) {
        return "ClientDetails?idClient=" + clientId;
    }

}
