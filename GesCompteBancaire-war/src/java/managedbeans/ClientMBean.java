/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entity.Client;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
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
    
    private int nombreClient ;

    public int getNombreClient() {
        return (gestionnaireDeClient.getAllClients()).size();
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
        return gestionnaireDeClient.getAllClients();
    }
    
   

    public String showDetails(int clientId) {
        return "ClientDetails?idClient=" + clientId;
    }

}
