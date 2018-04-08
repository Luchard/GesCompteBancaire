/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Client;
import entity.CompteBancaire;
import entity.TypeCompte;
import entity.Utilisateur;
import java.util.ArrayList;
import java.util.Collection;
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

    public TypeCompte getTypeCompte(String nom) {

        Query query = em.createNamedQuery("TypeCompte.findByNom");
        query.setParameter("nomCompte", nom);
        return (TypeCompte) query.getSingleResult();
    }

    public TypeCompte getTypeCompte(Long idTypeCompte) {
        return em.find(TypeCompte.class, idTypeCompte);
    }

    public void creerTypeCompteDeTest() {
        // creerTypeCompte("Compte Courant");
        TypeCompte tc = new TypeCompte("Compte Epargne");
        tc.setInteret(1.5F);
        em.persist(tc);
        creerTypeCompte("Compte Courant");

    }

    public void creerTypeCompte(String nom) {
        TypeCompte tc = new TypeCompte(nom);
        em.persist(tc);
    }
}
