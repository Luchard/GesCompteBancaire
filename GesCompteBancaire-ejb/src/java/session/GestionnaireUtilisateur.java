/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Client;
import entity.CompteBancaire;
import entity.Utilisateur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import services.DBCon;

/**
 *
 * @author luchi
 */
@Stateless
@LocalBean
public class GestionnaireUtilisateur {

    @EJB
    private GestionnaireTypeCompte gestionnaireTypeCompte;
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

    public void creerUtilisateursDeTest() {
        creeUtilisateur("John", "Lennon", "jlennon");
        creeUtilisateur("Paul", "Mac Cartney", "pmc");
        creeUtilisateur("Ringo", "Starr", "rstarr");
        creeUtilisateur("Georges", "Harisson", "georgesH");
    }

    public void creeUtilisateur(String nom, String prenom, String login) {
        Utilisateur u = new Utilisateur(nom, prenom, login);
        Client c = new Client(nom, prenom);

        Collection<CompteBancaire> cpts = new ArrayList<>();
        CompteBancaire cpt = new CompteBancaire();
        cpt.setTypeCompte(gestionnaireTypeCompte.getTypeCompte("Compte Epargne"));
        em.persist(cpt);
        cpts.add(cpt);
        em.persist(c);
        cpt.setClient(c);
        c.setCompteBancaires(cpts);
        u.setClient(c);
        em.persist(u);
    }

    public void creerUtilisateur(Utilisateur u) {
        Client c = new Client(u.getNom(), u.getPrenom());

        Collection<CompteBancaire> cpts = new ArrayList<>();
        CompteBancaire cpt = new CompteBancaire();
        cpt.setTypeCompte(gestionnaireTypeCompte.getTypeCompte("Compte Epargne"));
        em.persist(cpt);
        cpts.add(cpt);
        em.persist(c);
        cpt.setClient(c);
        c.setCompteBancaires(cpts);
        u.setClient(c);
        em.persist(u);

    }

    public Utilisateur getUtilisateur(String username, String password) {
        Query query = em.createNamedQuery("Utilisateur.findByUsernameAndPassword");
        query.setParameter("username", username);
        query.setParameter("password", password);

        return (Utilisateur) query.getSingleResult();

        // return query.getResultList();
    }

    public boolean login(String user, String password) {

        try {
            Query query = em.createNamedQuery("Utilisateur.findByUsernameAndPassword");
            query.setParameter("username", user);
            query.setParameter("password", password);

            //ResultSet rs = (ResultSet) query.getSingleResult();
            Utilisateur u = (Utilisateur) query.getSingleResult();
            if (u.getId() > 0) // found
            {
                System.out.println(u.getUsername());
                return true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "LoginDAO!",
                        "Wrong password message test!"));
                return false;
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Database Error",
                    "Unable to connect database"));
            System.out.println("Error in login() -->" + ex.getMessage());
            return false;
        }
    }

}
