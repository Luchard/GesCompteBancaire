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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luchi
 */
@Entity
@Table(name = "utilisateurs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u"),
    @NamedQuery(name = "Utilisateur.findByUtilisateurId", query = "SELECT u FROM Utilisateur u where u.id = :userId"),
    @NamedQuery(name = "Utilisateur.findByUsername", query = "SELECT u FROM Utilisateur u where u.username = :username"),
    @NamedQuery(name = "Utilisateur.findByClientId", query = "SELECT u FROM Utilisateur u where u.client.id = :clientId"),
    @NamedQuery(name = "Utilisateur.findByUsernameAndPassword", query = "SELECT u FROM Utilisateur u where u.username = :username and u.password = :password")})
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String prenom;
    private String nom;
    private StatutCompte statutCompte;
    private TypeUtilisateur typeUtilisateur;
    @OneToOne
    private Client client;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date derniereConnexion;

    public Utilisateur() {

    }

    public Utilisateur(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
        this.typeUtilisateur = TypeUtilisateur.CLIENT;
    }

    public Utilisateur(String nom, String prenom, String username) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.typeUtilisateur = TypeUtilisateur.CLIENT;
        this.statutCompte = StatutCompte.ACTIF;
    }

    public Utilisateur(Client client) {
        this.nom = client.getNom();
        this.prenom = client.getPrenom();
    }

    public StatutCompte getStatutCompte() {
        return statutCompte;
    }

    public void setStatutCompte(StatutCompte statutCompte) {
        this.statutCompte = statutCompte;
    }

    public TypeUtilisateur getTypeUtilisateur() {
        return typeUtilisateur;
    }

    public void setTypeUtilisateur(TypeUtilisateur typeUtilisateur) {
        this.typeUtilisateur = typeUtilisateur;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDerniereConnexion() {
        return derniereConnexion;
    }

    public void setDerniereConnexion(Date derniereConnexion) {
        this.derniereConnexion = derniereConnexion;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "entity.Utilisateur[ id=" + id + " ]";
    }

}
