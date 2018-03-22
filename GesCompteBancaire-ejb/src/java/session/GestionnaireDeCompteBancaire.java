/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

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
public class GestionnaireDeCompteBancaire {

    @PersistenceContext(unitName = "GesCompteBancaire-ejbPU")
    private EntityManager em;

    public void creerCompteBancaire(CompteBancaire c) {
        em.persist(c);
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

    public void creerComptesTest() {
        creerCompteBancaire(new CompteBancaire(150000));
        creerCompteBancaire(new CompteBancaire(950000));
        creerCompteBancaire(new CompteBancaire(20000));
        creerCompteBancaire(new CompteBancaire(100000));
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public CompteBancaire update(CompteBancaire comptebancaire) {
        return em.merge(comptebancaire);
    }

    public void persist(CompteBancaire comptebancaire) {
        em.persist(comptebancaire);
    }

    public CompteBancaire getCompteBancaire(Long idCompteBancaire) {
        return em.find(CompteBancaire.class, idCompteBancaire);
    }

    public void virementCompteACompte(Long id1, Long id2, int montant) {
        CompteBancaire c1 = em.find(CompteBancaire.class, id1);
        CompteBancaire c2 = em.find(CompteBancaire.class, id2);
        c1.retirer(montant);
        c2.deposer(montant);
    }

    public void fermerCompte(Long idCompteBancaire) {
        CompteBancaire c1 = em.find(CompteBancaire.class, idCompteBancaire);
        em.remove(c1);
    }
    
    public void depot(Long idCompteBancaire, int montant){
    CompteBancaire c1 = em.find(CompteBancaire.class, idCompteBancaire);
    c1.deposer(montant);
    }
    
    public int retrait(Long idCompteBancaire, int montant){
    CompteBancaire c1 = em.find(CompteBancaire.class, idCompteBancaire);
    return c1.retirer(montant);
    }
}
