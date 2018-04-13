/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import entity.TransactionBancaire;
import entity.TypeUtilisateur;
import entity.Utilisateur;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;
import services.Util;
import session.GestionnaireTransaction;

/**
 *
 * @author luchi
 */
@Named(value = "transactionBancaireMBean")
@ViewScoped
public class TransactionBancaireMBean implements Serializable {

    @EJB
    private GestionnaireTransaction gestionnaireTransaction;
    private TransactionBancaire transactionBancaire;
    private Long idCompteBancaire;

    public Long getIdCompteBancaire() {
        return idCompteBancaire;
    }

    public void setIdCompteBancaire(Long idCompteBancaire) {
        this.idCompteBancaire = idCompteBancaire;
    }

    /**
     * Creates a new instance of TransactionBancaireMBean
     */
    public TransactionBancaireMBean() {
    }

    public List<TransactionBancaire> getTransactions() {

        HttpSession session = Util.getSession();
        Utilisateur u = (Utilisateur) session.getAttribute("Utilisateur");
        if (u.getTypeUtilisateur() == TypeUtilisateur.CLIENT) {
            return gestionnaireTransaction.getAllTransactionsByClient(u.getClient().getId());
        } else {
            return gestionnaireTransaction.getAllTransactions();
        }

    }

    public int getQuantiteTransactions() {

        HttpSession session = Util.getSession();
        Utilisateur u = (Utilisateur) session.getAttribute("Utilisateur");
        if (u.getTypeUtilisateur() == TypeUtilisateur.CLIENT) {
            return gestionnaireTransaction.getAllTransactionsByClient(u.getClient().getId()).size();
        } else {
            return gestionnaireTransaction.getAllTransactions().size();
        }
    }

    public List<TransactionBancaire> getTransactionsCompte() {
        return gestionnaireTransaction.getAllTransactionsByCompte(idCompteBancaire);
    }
    
        public String addRetour() {
       
        return "ListeTransaction.xhtml";
    }

}
