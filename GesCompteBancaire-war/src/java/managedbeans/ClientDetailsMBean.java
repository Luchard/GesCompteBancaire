/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entity.Client;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import session.GestionnaireDeClient;

/**
 *
 * @author luchi
 */
@Default
@Any
@Named(value = "clientDetailsMBean")
@ViewScoped
public class ClientDetailsMBean implements Serializable {
private Long idClient;  
  private Client client;
    @EJB
    private GestionnaireDeClient gestionnaireDeClient;

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }


    
    public Client getDetails() {  
    return client;  
  }  
  
  
  public String update() {  
    System.out.println("###UPDATE###");  
    client = gestionnaireDeClient.update(client);  
    return "ListeClients.xhtml";  
  }  
  
  public String addRetour() {
       
        return "ListeClients.xhtml";
    }
  public String list() {  
    System.out.println("###LIST###");  
    return "ListeClients.xhtml";  
  }  
  
  public void loadClient() {  
    this.client = gestionnaireDeClient.getClient(idClient);  
  }

    /**
     * Creates a new instance of ClientDetailsMBean
     */
    public ClientDetailsMBean()  {
    }
    
}
