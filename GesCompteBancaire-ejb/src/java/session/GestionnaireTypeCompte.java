/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Client;
import entity.TypeCompte;
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
public class GestionnaireTypeCompte {

    @PersistenceContext(unitName = "GesCompteBancaire-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

     public List<TypeCompte> getAllTypeCompte() {
    
     
      Query query = em.createNamedQuery("TypeCompte.findAll");  
        return query.getResultList();
    }
    
      public TypeCompte getTypeCompte(Long idTypeCompte) {  
        return em.find(TypeCompte.class, idTypeCompte);  
}
}
