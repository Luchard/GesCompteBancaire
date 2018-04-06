/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luchi
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeCompte.findAll", query = "SELECT t FROM TypeCompte t"),
    @NamedQuery(name = "TypeCompte.findById", query = "SELECT t FROM TypeCompte t WHERE t.id = :typeCompteId")})
public class TypeCompte implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nom;
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, mappedBy = "typeCompte")
    private Collection<CompteBancaire> compteBancaires;
private float interet;
    public Collection<CompteBancaire> getCompteBancaires() {
        return compteBancaires;
    }

    public TypeCompte() {

    }

    public TypeCompte(String nom) {
        this.nom = nom;
        this.interet = 0.0F;
    }

    public void setCompteBancaires(Collection<CompteBancaire> compteBancaires) {
        this.compteBancaires = compteBancaires;
    }

    public Long getId() {
        return id;
    }

    public float getInteret() {
        return interet;
    }

    public void setInteret(float interet) {
        this.interet = interet;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
        if (!(object instanceof TypeCompte)) {
            return false;
        }
        TypeCompte other = (TypeCompte) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TypeCompte[ id=" + id + " ]";
    }

}
