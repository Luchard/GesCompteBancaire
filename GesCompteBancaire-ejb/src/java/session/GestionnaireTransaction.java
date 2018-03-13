/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;



import entity.TransactionBancaire;
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
public class GestionnaireTransaction {

    @PersistenceContext(unitName = "GesCompteBancaire-ejbPU")
    private EntityManager em;

    public void persist(TransactionBancaire transactionBancaire) {
        em.persist(transactionBancaire);
    }
    
    public List<TransactionBancaire> getAllTransactions() {
    
      Query query = em.createNamedQuery("TransactionBancaire.findAll");  
        return query.getResultList();
    }

    public List<TransactionBancaire> getAllTransactionsByCompte(Long idCompte) {
    
      Query query = em.createNamedQuery("TransactionBancaire.findAllByCompteId");
      query.setParameter("compteId", idCompte);
        return query.getResultList();
    }
    
      public TransactionBancaire getTransaction(Integer idTransaction) {  
        return em.find(TransactionBancaire.class, idTransaction);  
}
      
      public void creerTransactionBancaire(TransactionBancaire tBancaire) {
        em.persist(tBancaire);
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
