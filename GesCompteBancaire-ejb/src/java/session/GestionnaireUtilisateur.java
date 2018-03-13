/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Utilisateur;
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
public class GestionnaireUtilisateur {

    @PersistenceContext(unitName = "GesCompteBancaire-ejbPU")
    private EntityManager em;

    
   
    public List<Utilisateur> getAllUtilisateurs() {
       
     
      Query query = em.createNamedQuery("Utilisateur.findAll");  
        return query.getResultList();
    }

   
    public Utilisateur update(Utilisateur client) {
        return em.merge(client);
    }

    public void persist(Utilisateur client) {
        em.persist(client);
    }
    
    public Utilisateur getUtilisateur(Long idClient) {  
        return em.find(Utilisateur.class, idClient);  
}

    
    public Utilisateur getUtilisateur(String username , String password) {  
        Query query = em.createNamedQuery("Utilisateur.findByUsernameAndPassword");  
        query.setParameter("username", username);
        query.setParameter("password", password);
        return (Utilisateur) query.getSingleResult();
       // return query.getResultList();
}
    
}
