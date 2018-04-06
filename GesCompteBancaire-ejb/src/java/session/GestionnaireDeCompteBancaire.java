/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.CompteBancaire;
import entity.TransactionBancaire;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

/**
 *
 * @author luchi
 */
@Stateless
@LocalBean
@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class GestionnaireDeCompteBancaire {

    @PersistenceContext(unitName = "GesCompteBancaire-ejbPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction userTx;

    public void creerCompteBancaire(CompteBancaire c) {

        try {
            userTx.begin();
            em.persist(c);
            try {
                userTx.commit();
            } catch (HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException | RollbackException ex) {
                Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NotSupportedException | SystemException ex) {
            Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void creerCompteBancaire(String proprietaire, int montant) {
        CompteBancaire cBancaire = new CompteBancaire();

        cBancaire.setSolde(montant);
        em.persist(cBancaire);
    }

    public List<CompteBancaire> getAllComptes() {
        Query query = em.createNamedQuery("CompteBancaire.findAll");
        return query.getResultList();
    }

    public List<CompteBancaire> getAllComptes(Long id_client) {
        Query query = em.createNamedQuery("CompteBancaire.findCompteByClientId");
        query.setParameter("clientId", id_client);
        return query.getResultList();
    }

    public List<CompteBancaire> getAllComptes(Long id_client, Long id_compte) {
        Query query = em.createNamedQuery("CompteBancaire.findCompteByClientIdAndCompteId");
        query.setParameter("clientId", id_client);
        query.setParameter("compteBancaireId", id_compte);
        return query.getResultList();
    }

    public void creerComptesTest() {
        creerCompteBancaire(new CompteBancaire(150000));
        creerCompteBancaire(new CompteBancaire(950000));
        creerCompteBancaire(new CompteBancaire(20000));
        creerCompteBancaire(new CompteBancaire(100000));
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public CompteBancaire update(CompteBancaire comptebancaire) {
        try {
            userTx.begin();
            em.merge(comptebancaire);
            try {
                userTx.commit();
            } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NotSupportedException | SystemException ex) {
            Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return em.merge(comptebancaire);
    }

    public void persist(CompteBancaire comptebancaire) {
        em.persist(comptebancaire);
    }

    public CompteBancaire getCompteBancaire(Long idCompteBancaire) {
        CompteBancaire cB = new CompteBancaire();
        try {
            userTx.begin();
            cB = em.find(CompteBancaire.class, idCompteBancaire);
            userTx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cB;

    }

    public CompteBancaire getCompteBancaireByNumCompte(String numeroCompte) {
        CompteBancaire cB = new CompteBancaire();
        try {
            userTx.begin();
            // cB =  em.find(CompteBancaire.class, idCompteBancaire);
            Query query = em.createNamedQuery("CompteBancaire.findByNumeroCompte");
            query.setParameter("numeroCompteBancaire", numeroCompte);
            //  return query.getResultList();
            cB = (CompteBancaire) query.getSingleResult();
            userTx.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cB;

    }

    public long getNombreDeComptes() {
        Query query = em.createNamedQuery("CompteBancaire.getNombre");
        return (long) query.getSingleResult();
    }

    public List<CompteBancaire> getComptesTriesParNom(int start, int nb, String order) {
        String orderValue = "";
        if (order.equals("ASCENDING")) {
            orderValue = "ASC";
        } else {
            orderValue = "DESC";
        }
        String r = "select c from CompteBancaire c order by c.client.nom"
                + orderValue;
        Query query = em.createQuery(r);
        query.setFirstResult(start);
        query.setMaxResults(nb);
        return query.getResultList();
    }

    public void virementCompteACompte(Long id1, Long id2, int montant) throws RollbackException {
        try {
            userTx.begin();
            CompteBancaire c1 = em.find(CompteBancaire.class, id1);
            CompteBancaire c2 = em.find(CompteBancaire.class, id2);
            if (c1.getSolde() < montant) {
                userTx.rollback();
            }

            c1.retirer(montant);
            TransactionBancaire transaction = new TransactionBancaire();
            transaction.setComptebancaire(c1);
            transaction.setClient(c1.getClient());
            transaction.setDescription("Retrait");
            Date date = new Date();
            transaction.setMontant(montant);
            transaction.setDateTransaction(date);
            em.persist(transaction);
            c2.deposer(montant);
            TransactionBancaire transaction1 = new TransactionBancaire();
            transaction1.setComptebancaire(c2);
            transaction1.setClient(c2.getClient());
            transaction1.setDescription("Dépot");
            transaction1.setDateTransaction(date);
            transaction1.setMontant(montant);
            em.persist(transaction1);
            em.persist(c1);
            em.persist(c2);

            try {
                userTx.commit();
            } catch (HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NotSupportedException | SystemException ex) {
            Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fermerCompte(Long idCompteBancaire) {
        try {
            userTx.begin();
            CompteBancaire c1 = em.find(CompteBancaire.class, idCompteBancaire);
            em.remove(c1);
            try {
                userTx.commit();
            } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NotSupportedException | SystemException ex) {
            Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TransactionBancaire depot(Long idCompteBancaire, int montant) {
        TransactionBancaire transaction = new TransactionBancaire();
        try {
            userTx.begin();
            CompteBancaire c1 = em.find(CompteBancaire.class, idCompteBancaire);
            Date date = new Date();
            transaction.setComptebancaire(c1);
            transaction.setDateTransaction(date);
            transaction.setClient(c1.getClient());
            transaction.setDescription("Dépot");
            c1.deposer(montant);
            try {
                userTx.commit();
            } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NotSupportedException | SystemException ex) {
            Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (transaction);
    }

    public int retrait(Long idCompteBancaire, int montant) {
        int montantRetire = 0;
        try {
            userTx.begin();
            CompteBancaire c1 = em.find(CompteBancaire.class, idCompteBancaire);
            TransactionBancaire transaction = new TransactionBancaire();
            Date date = new Date();
            transaction.setComptebancaire(c1);
            transaction.setMontant(montant);
            transaction.setDateTransaction(date);
            transaction.setClient(c1.getClient());
            transaction.setDescription("Retrait");
            em.persist(transaction);
            montantRetire = c1.retirer(montant);
            try {
                userTx.commit();
            } catch (RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
                Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (NotSupportedException | SystemException ex) {
            Logger.getLogger(GestionnaireDeCompteBancaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return montantRetire;
    }
}
