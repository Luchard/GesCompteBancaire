/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entity.Client;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import session.GestionnaireDeClient;

/**
 *
 * @author luchi
 */
@Named(value = "nouveauClientMBean")
@ViewScoped
public class NouveauClientMBean implements Serializable {

    @EJB
    private GestionnaireDeClient gestionnaireDeClient;

    private final  Client client = new Client();

    public Client getClient() {
        return client;
    }
    
    /**
     * Creates a new instance of NouveauClientMBean
     */
    public NouveauClientMBean() {
   
    }
    
public String creeClient(){
    gestionnaireDeClient.creerClient(client);
    FacesMessage message = new FacesMessage("Succes de inscription");
    FacesContext.getCurrentInstance().addMessage(null, message);
    return "ListeClients.xhtml";
}

    
}
