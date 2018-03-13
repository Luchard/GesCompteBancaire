/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Client;
import entity.CompteBancaire;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luchi
 */
@Stateless
@LocalBean
public class GestionnaireDeClient {

    @PersistenceContext(unitName = "GesCompteBancaire-ejbPU")
    private EntityManager em;

    public void creerClient(Client c) {
        em.persist(c);
    }

    public List<Client> getAllClients() {
       // Query query = em.createNamedQuery("Customer.findAll");
      //  return query.getResultList();
  //  Query query =  em.createQuery("SELECT c FROM CompteBancaire c");
    //return query.getResultList();
     
      Query query = em.createNamedQuery("Client.findAll");  
        return query.getResultList();
    }

    public void creerComptesTest() {
        creerClient(new Client("Orelus", "Luchard"));
        creerClient(new Client("Paul", "Carlors"));
        creerClient(new Client("Marc", "Olivier"));
        creerClient(new Client("Georges", "Sanders"));
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public Client update(Client client) {
        return em.merge(client);
    }

    public void persist(Client client) {
        em.persist(client);
    }
    
    public Client getClient(Long idClient) {  
        return em.find(Client.class, idClient);  
}
}
