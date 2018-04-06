/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entity.Client;
import entity.CompteBancaire;
import entity.TransactionBancaire;
import entity.TypeCompte;
import entity.TypeUtilisateur;
import entity.Utilisateur;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.TransactionManagement;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.servlet.http.HttpSession;
import javax.transaction.RollbackException;
import services.Util;
import session.GestionnaireDeClient;
import session.GestionnaireDeCompteBancaire;
import session.GestionnaireTransaction;
import session.GestionnaireTypeCompte;

/**
 *
 * @author luchi
 */
@Named(value = "compteBancairMBean")
@ViewScoped
public class CompteBancaireMBean implements Serializable {

    @Resource(mappedName = "java:app/jms/loggingMessages")
    private Queue java_appJmsLoggingMessages;

    @Inject
    @JMSConnectionFactory("java:comp/DefaultJMSConnectionFactory")
    private JMSContext context;

    @EJB
    private GestionnaireTypeCompte gestionnaireTypeCompte;

    @EJB
    private GestionnaireDeCompteBancaire gestionnaireDeCompteBancaire;
    @EJB
    private GestionnaireTransaction gestionnaireTransaction;
    private int quantiteCompteBancaire;

    public int getQuantiteCompteBancaire() {

        HttpSession session = Util.getSession();
        Utilisateur u = (Utilisateur) session.getAttribute("Utilisateur");
        if (u.getTypeUtilisateur() == TypeUtilisateur.CLIENT) {
            return gestionnaireDeCompteBancaire.getAllComptes(u.getClient().getId()).size();
        } else {
            return gestionnaireDeCompteBancaire.getAllComptes().size();
        }

        //  return (gestionnaireDeCompteBancaire.getAllComptes()).size();
    }

    private final TransactionBancaire transaction;
    private final TransactionBancaire transaction1;
    @EJB
    private GestionnaireDeClient gestionnaireDeClient;

    private CompteBancaire compteBancaire = new CompteBancaire();
    private CompteBancaire compteBancaireVire = new CompteBancaire();
    private CompteBancaire compteBancaireTransferer = new CompteBancaire();

    private Client client;
    private TypeCompte typeCompte;
    private boolean sansMontant = true;
    private boolean compteEpargne = false;
    private float montantInteret = 0.0F;
    private Long idCompteBancaire;

    public Long getIdCompteBancaire() {
        return idCompteBancaire;
    }

    public void setIdCompteBancaire(Long idCompteBancaire) {
        this.idCompteBancaire = idCompteBancaire;
    }

    public float getMontantInteret() {
        return montantInteret;
    }

    public void setMontantInteret(float montantInteret) {
        this.montantInteret = montantInteret;
    }

    public boolean isCompteEpargne() {
        return compteEpargne;
    }

    public void setCompteEpargne(boolean compteEpargne) {
        this.compteEpargne = compteEpargne;
    }

    public boolean isSansMontant() {
        return sansMontant;
    }

    public void setSansMontant(boolean sansMontant) {
        this.sansMontant = sansMontant;
    }

    public TypeCompte getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(TypeCompte typeCompte) {
        this.typeCompte = typeCompte;
    }

    public CompteBancaire getCompteBancaireTransferer() {
        return compteBancaireTransferer;
    }

    public void setCompteBancaireTransferer(CompteBancaire compteBancaireTransferer) {
        this.compteBancaireTransferer = compteBancaireTransferer;
    }

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

    public List<TypeCompte> getTypeComptes() {
        // gestionnaireDeClient.creerComptesTest();
        return gestionnaireTypeCompte.getAllTypeCompte();
    }

    public List<CompteBancaire> getCompteBancaires() {
        //gestionnaireDeCompteBancaire.creerComptesTest();

        HttpSession session = Util.getSession();
        Utilisateur u = (Utilisateur) session.getAttribute("Utilisateur");
        if (u.getTypeUtilisateur() == TypeUtilisateur.CLIENT) {
            return gestionnaireDeCompteBancaire.getAllComptes(u.getClient().getId());
        } else {
            return gestionnaireDeCompteBancaire.getAllComptes();
        }

    }

    public List<CompteBancaire> getCompteBancaires(Long clientId) {
        return gestionnaireDeCompteBancaire.getAllComptes(clientId);
    }

    public List<CompteBancaire> getCompteBancairesClients(Long clientId, Long compteBancaireId) {
        return gestionnaireDeCompteBancaire.getAllComptes(clientId, compteBancaireId);
    }
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

    private final Converter typeCompteConverter = new Converter() {

        @Override
        public Object getAsObject(FacesContext context, UIComponent component, String value) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            TypeCompte c = gestionnaireTypeCompte.getTypeCompte(Long.valueOf(value));
            return c;
        }

        @Override
        public String getAsString(FacesContext context, UIComponent component, Object value) {
            TypeCompte t = (TypeCompte) value;
            return String.valueOf(t.getId());
        }

    };

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

    public Converter getTypeCompteConverter() {
        return typeCompteConverter;
    }

    public Converter getClientConverter() {
        return clientConverter;
    }

    public String showDetails(int compteBancaireId) {
        return "ModifierCompte?idCompteBancaire=" + compteBancaireId;
    }

    public String addCompte() {
        //     gestionnaireDeCompteBancaire.getCompteBancaireByNumCompte(compteBancaire.getNumeroCompte());
        compteBancaire.setClient(client);
        compteBancaire.setTypeCompte(typeCompte);
        gestionnaireDeCompteBancaire.creerCompteBancaire(compteBancaire);
        return "ListeCompteBancaires.xhtml";
    }

    public void deposit(int montant) {
        compteBancaire.deposer(montant);
    }

    public void retrait(int montant) {
        compteBancaire.retirer(montant);
    }

    public String sauvegarderTransaction() {
        gestionnaireDeCompteBancaire.depot(compteBancaire.getId(), transaction.getMontant());
        return "ListeCompteBancaires.xhtml";
    }

    public void sauvegarderRetraitTransaction() {
        if ("Compte Epargne".equals(compteBancaire.getTypeCompte().getNom())) {
            compteEpargne = true;
        } else {

            int retrait = 0;
            retrait = gestionnaireDeCompteBancaire.retrait(compteBancaire.getId(), transaction.getMontant());
            //  return "ListeCompteBancaires.xhtml";
        }
    }

    public String sauvegarderVirement() {
        transaction.setComptebancaire(compteBancaire);
        transaction.setClient(client);
        transaction.setDescription("Retrait");
//             gestionnaireTransaction.creerTransactionBancaire(transaction);
//        //     int retrait = 0;
//        //     retrait = gestionnaireDeCompteBancaire.retrait(compteBancaire.getId(), transaction.getMontant());
//           transaction1.setComptebancaire(compteBancaireVire);
//          transaction1.setClient(client);
//          transaction1.setDescription("Dépot");
//          transaction1.setDateTransaction(transaction.getDateTransaction());
        transaction1.setMontant(transaction.getMontant());
//        gestionnaireTransaction.creerTransactionBancaire(transaction1);
//        // gestionnaireDeCompteBancaire.depot(compteBancaireVire.getId(), transaction1.getMontant());
        try {
            gestionnaireDeCompteBancaire.virementCompteACompte(compteBancaire.getId(), compteBancaireVire.getId(), transaction.getMontant());
        } catch (RollbackException ex) {
            Logger.getLogger(CompteBancaireMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ListeCompteBancaires.xhtml";
    }

    public TransactionBancaire getTransaction() {
        return transaction;
    }

    public void fermerCompte() {
        if (compteBancaire.getSolde() > 0) {
            sansMontant = false;
        } else {
            gestionnaireDeCompteBancaire.fermerCompte(compteBancaire.getId());
            sansMontant = true;
        }     // return "ListeClients.xhtml";

    }

    public String transfererMontant() {
        try {

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

            Date today = Calendar.getInstance().getTime();
            String dateToday = df.format(today);
            String dateOuvertureCompte = df.format(compteBancaire.getDateOuverture());
            int nbreMois = 0;
            try {
                nbreMois = Util.nbOfMonthsBetweenTwoDates(dateOuvertureCompte, dateToday);
            } catch (Exception ex) {
                Logger.getLogger(CompteBancaireMBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            montantInteret = (float) (compteBancaire.getTypeCompte().getInteret() * (float) compteBancaire.getSolde() * nbreMois) / 100;

            
            gestionnaireDeCompteBancaire.virementCompteACompte(compteBancaire.getId(), compteBancaireTransferer.getId(), compteBancaire.getSolde());
            gestionnaireDeCompteBancaire.depot(compteBancaireTransferer.getId(), (int) montantInteret);
            gestionnaireDeCompteBancaire.fermerCompte(compteBancaire.getId());

        } catch (RollbackException ex) {
            Logger.getLogger(CompteBancaireMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ListeCompteBancaires.xhtml";
    }

    public String getmontantInteret(Long compteBancaireId) {

        return "InteretCompte?idCompteBancaire=" + compteBancaireId;
    }

    public void loadMontant() {
        CompteBancaire cbancaire;
        cbancaire = gestionnaireDeCompteBancaire.getCompteBancaire(idCompteBancaire);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        Date today = Calendar.getInstance().getTime();
        String dateToday = df.format(today);
        String dateOuvertureCompte = df.format(cbancaire.getDateOuverture());
        int nbreMois = 0;
        try {
            nbreMois = Util.nbOfMonthsBetweenTwoDates(dateOuvertureCompte, dateToday);
        } catch (Exception ex) {
            Logger.getLogger(CompteBancaireMBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        montantInteret = (float) (cbancaire.getTypeCompte().getInteret() * (float) cbancaire.getSolde() * nbreMois) / 100;
    }

    private void sendJMSMessageToLoggingMessages(String messageData) {
        context.createProducer().send(java_appJmsLoggingMessages, messageData);
    }

}
