/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luchi
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TransactionBancaire.findAll", query = "SELECT t FROM TransactionBancaire t"),
    @NamedQuery(name = "TransactionBancaire.findAllByClientId", query = "SELECT t FROM TransactionBancaire t where t.client.id =:clientId"),
    @NamedQuery(name = "TransactionBancaire.findAllByCompteId", query = "SELECT t FROM TransactionBancaire t where t.comptebancaire.id =:compteId")
    })
public class TransactionBancaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private int montant;
    @ManyToOne
    private CompteBancaire comptebancaire;
    @ManyToOne
    private Client client;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTransaction;
    private String description;
    public TransactionBancaire() {
    //    this.client = new Client();
      //  this.comptebancaire = new CompteBancaire();
     
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public int getMontant() {
        return montant;
    }

    public void setMontant(int montant) {
        this.montant = montant;
    }

    public CompteBancaire getComptebancaire() {
        return comptebancaire;
    }

    public void setComptebancaire(CompteBancaire comptebancaire) {
        this.comptebancaire = comptebancaire;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof TransactionBancaire)) {
            return false;
        }
        TransactionBancaire other = (TransactionBancaire) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "entity.Transaction[ id=" + id + " ]";
    }
    
}
