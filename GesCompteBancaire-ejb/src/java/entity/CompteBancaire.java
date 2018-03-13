/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luchi
 */
@Entity
@Table(name = "COMPTEBANCAIRE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompteBancaire.findAll", query = "SELECT c FROM CompteBancaire c"),
    @NamedQuery(name = "CompteBancaire.findByCompteId", query = "SELECT c FROM CompteBancaire c WHERE c.id = :compteBancaireId"),
@NamedQuery(name = "CompteBancaire.findCompteByClientId", query = "SELECT c FROM CompteBancaire c WHERE c.client.id = :clientId")
})
public class CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    private int solde;
    @ManyToOne 
    private Client client;
    @OneToMany(mappedBy = "comptebancaire")
    private List<TransactionBancaire> transactions;

    public CompteBancaire() {
        transactions = new ArrayList<>();
        
    }

    public CompteBancaire(String nom, int solde) {
        this();
        this.nom = nom;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

}
