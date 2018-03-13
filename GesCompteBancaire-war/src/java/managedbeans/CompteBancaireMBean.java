/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entity.Client;
import entity.CompteBancaire;
import entity.TransactionBancaire;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import session.GestionnaireDeClient;
import session.GestionnaireDeCompteBancaire;
import session.GestionnaireTransaction;

/**
 *
 * @author luchi
 */
@Named(value = "compteBancairMBean")
@ViewScoped
public class CompteBancaireMBean implements Serializable {

    @EJB
    private GestionnaireDeCompteBancaire gestionnaireDeCompteBancaire;
    @EJB
    private GestionnaireTransaction gestionnaireTransaction;

    private final TransactionBancaire transaction;
    private final TransactionBancaire transaction1;
    @EJB
    private GestionnaireDeClient gestionnaireDeClient;

    private CompteBancaire compteBancaire = new CompteBancaire();
    private CompteBancaire compteBancaireVire = new CompteBancaire();

    private Client client;

    public CompteBancaire getCompteBancaire() {
        return compteBancaire;
    }

    public CompteBancaire getCompteBancaireVire() {
        return compteBancaireVire;
    }

    public void setCompteBancaireVire(CompteBancaire compteBancaireVire) {
        this.compteBancaireVire = compteBancaireVire;
    }
    

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCompteBancaire(CompteBancaire compteBancaire) {
        this.compteBancaire = compteBancaire;
    }

    /**
     * Creates a new instance of CompteBancairMBean
     */
    public CompteBancaireMBean() {
        // this.compteBancaire = new CompteBancaire();
        this.transaction = new TransactionBancaire();
        this.transaction1 = new TransactionBancaire();
    }

    public List<CompteBancaire> getCompteBancaires() {
        //gestionnaireDeCompteBancaire.creerComptesTest();
        return gestionnaireDeCompteBancaire.getAllComptes();
    }

    // public List<CompteBancaire> getCompteBancaires(Long clientId) {
    //gestionnaireDeCompteBancaire.creerComptesTest();
    //     clientId = client.getId();
    //  return gestionnaireDeCompteBancaire.getAllComptes(clientId);
    // }
    private final Converter clientConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            Client c = gestionnaireDeClient.getClient(Long.valueOf(value));
            return c;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            Client c = (Client) value;
            return String.valueOf(c.getId());
        }

    };

    public Converter getCompteConverter() {

        return compteBancaireConverter;
    }

    private final Converter compteBancaireConverter = new Converter() {
        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            CompteBancaire c = gestionnaireDeCompteBancaire.getCompteBancaire(Long.valueOf(value));
            return c;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            CompteBancaire cB = (CompteBancaire) value;
            return String.valueOf(cB.getId());
        }
    };

    public Converter getClientConverter() {
        return clientConverter;
    }

    public String showDetails(int compteBancaireId) {
        return "ModifierCompte?idCompteBancaire=" + compteBancaireId;
    }

    public void addCompte() {
        compteBancaire.setClient(client);

        gestionnaireDeCompteBancaire.creerCompteBancaire(compteBancaire);

    }

    public void deposit(int montant) {
        compteBancaire.deposer(montant);
    }

    public void retrait(int montant) {
        compteBancaire.retirer(montant);
    }

    public String sauvegarderTransaction() {
        transaction.setComptebancaire(compteBancaire);
        transaction.setClient(client);
        transaction.setDescription("Dépot");
        gestionnaireTransaction.creerTransactionBancaire(transaction);
        gestionnaireDeCompteBancaire.depot(compteBancaire.getId(), transaction.getMontant());
        return "ListeCompteBancaires.xhtml";
    }

        public String sauvegarderRetraitTransaction() {
        transaction.setComptebancaire(compteBancaire);
        transaction.setClient(client);
        transaction.setDescription("Retrait");
        gestionnaireTransaction.creerTransactionBancaire(transaction);
        int retrait = 0;
        retrait = gestionnaireDeCompteBancaire.retrait(compteBancaire.getId(), transaction.getMontant());
        return "ListeCompteBancaires.xhtml";
    }
        
        public String sauvegarderVirement(){
            transaction.setComptebancaire(compteBancaire);
        transaction.setClient(client);
        transaction.setDescription("Retrait");
        gestionnaireTransaction.creerTransactionBancaire(transaction);
        int retrait = 0;
        retrait = gestionnaireDeCompteBancaire.retrait(compteBancaire.getId(), transaction.getMontant());
        
        
        transaction1.setComptebancaire(compteBancaireVire);
        transaction1.setClient(client);
        transaction1.setDescription("Dépot");
        transaction1.setDateTransaction(transaction.getDateTransaction());
        transaction1.setMontant(transaction.getMontant());
        gestionnaireTransaction.creerTransactionBancaire(transaction1);
        gestionnaireDeCompteBancaire.depot(compteBancaireVire.getId(), transaction1.getMontant());
            
        return "ListeCompteBancaires.xhtml";
        }
        
    public TransactionBancaire getTransaction() {
        return transaction;
    }
    public String fermerCompte(){
    gestionnaireDeCompteBancaire.fermerCompte(compteBancaire.getId());
    return "ListeClients.xhtml";
    }
}
