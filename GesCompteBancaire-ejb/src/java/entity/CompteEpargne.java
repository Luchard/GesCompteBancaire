/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author luchi
 */
@Entity
@DiscriminatorValue("COMPTEEPARGNE")
public class CompteEpargne extends CompteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double tauxAnnuel;

    public CompteEpargne() {
        this.tauxAnnuel = 0.01;
    }

    public double getTauxAnnuel() {
        return tauxAnnuel;
    }

    public void setTauxAnnuel(double tauxAnnuel) {
        this.tauxAnnuel = tauxAnnuel;
    }

    public CompteEpargne(double tauxAnnuel) {
        this.tauxAnnuel = tauxAnnuel;
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
        if (!(object instanceof CompteEpargne)) {
            return false;
        }
        CompteEpargne other = (CompteEpargne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CompteEpargne[ id=" + id + " ]";
    }

}
