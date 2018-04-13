/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import session.GestionnaireTypeCompte;


/**
 *
 * @author luchi
 */
@Entity
@Table(name = "COMPTEBANCAIRE")
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="DISC", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("COMPTEBANCAIRE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompteBancaire.findAll", query = "SELECT c FROM CompteBancaire c"),
    @NamedQuery(name = "CompteBancaire.findByCompteId", query = "SELECT c FROM CompteBancaire c WHERE c.id = :compteBancaireId"),
    @NamedQuery(name = "CompteBancaire.findByNumeroCompte", query = "SELECT c FROM CompteBancaire c WHERE c.numeroCompte = :numeroCompteBancaire"),
    @NamedQuery(name = "CompteBancaire.getNombre", query = "select count(c) from CompteBancaire c"),
    @NamedQuery(name = "CompteBancaire.findCompteByClientIdAndCompteId", query = "SELECT c FROM CompteBancaire c WHERE c.client.id = :clientId and c.id NOT IN (:compteBancaireId)"),
    @NamedQuery(name = "CompteBancaire.findCompteByClientId", query = "SELECT c FROM CompteBancaire c WHERE c.client.id = :clientId")
})
public class CompteBancaire implements Serializable {

   

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int solde;
    @ManyToOne
    private Client client;
    @ManyToOne
    private TypeCompte typeCompte;
    @OneToMany(mappedBy = "comptebancaire", cascade = CascadeType.ALL)
    private List<TransactionBancaire> transactions;
    private String numeroCompte;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOuverture;

    public CompteBancaire() {
        transactions = new ArrayList<>();
        Date dateOuvertureCompte;
        dateOuvertureCompte = new Date();
        Random rand = new Random(); 
        int nombreAleatoire = rand.nextInt(10000 - 0 + 1) + 0;
     //    GestionnaireTypeCompte gestionnaireTypeCompte = lookupGestionnaireTypeCompteBean();
     //   this.typeCompte = gestionnaireTypeCompte.getTypeCompte("Compte Epargne");
        this.numeroCompte = String.valueOf(nombreAleatoire);
        this.dateOuverture = dateOuvertureCompte;
    }

    public Date getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(Date dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public TypeCompte getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(TypeCompte typeCompte) {
        this.typeCompte = typeCompte;
    }

    public CompteBancaire(int solde) {
        this();
        TypeCompte t = new TypeCompte();

        this.typeCompte = new TypeCompte();

        this.solde = solde;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<TransactionBancaire> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionBancaire> transactions) {
        this.transactions = transactions;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void FaireTransaction(TransactionBancaire transaction) {
        transactions.add(transaction);
    }

    public void renversementOperation(TransactionBancaire transaction) {
        transactions.remove(transaction);
    }

    public void deposer(int montant) {
        this.solde = this.solde + montant;
        // TransactionBancaire tBancaire = new TransactionBancaire();
        // FaireTransaction(tBancaire);
    }

    public int retirer(int montant) {
        if (montant <= solde) {
            solde -= montant;
            //   TransactionBancaire tBancaire = new TransactionBancaire();
            //  FaireTransaction(tBancaire);
            return solde;
        } else {
            return 0;
        }
    }

    public int getSolde() {
        return solde;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CompteBancaire)) {
            return false;
        }
        CompteBancaire other = (CompteBancaire) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CompteBancaire[ id=" + id + " ]";
    }

    private GestionnaireTypeCompte lookupGestionnaireTypeCompteBean() {
        try {
            Context c = new InitialContext();
            return (GestionnaireTypeCompte) c.lookup("java:global/GesCompteBancaire/GesCompteBancaire-ejb/GestionnaireTypeCompte!session.GestionnaireTypeCompte");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
