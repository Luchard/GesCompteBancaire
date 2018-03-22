/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbeans;

import services.Util;
import entity.Utilisateur;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import services.LoginDAO;
import session.GestionnaireUtilisateur;

/**
 *
 * @author luchi
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    @EJB
    private GestionnaireUtilisateur gestionnaireUtilisateur;

     private String password;
    private String message, uname;
 private Utilisateur utilisateur;

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    
    public String getMessage() {
        return message;
    }
 
    public void setMessage(String message) {
        this.message = message;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public String getUname() {
        return uname;
    }
 
    public void setUname(String uname) {
        this.uname = uname;
    }
 
    public String loginProject() {
        utilisateur = gestionnaireUtilisateur.getUtilisateur(uname, password);
        FacesContext context = FacesContext.getCurrentInstance();
       HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
       
        if (utilisateur.getId() > 0){
        return "ListeClients.xhtml";
        }
        else{
        return "login.xhtml";
        }
        
    }
    private String username;

    public String getUsername() {
         HttpSession session = Util.getSession();
            
        username = (String) session.getAttribute("username"); 
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    private Utilisateur utilisateurConnecte;

    public Utilisateur getUtilisateurConnecte() {
         HttpSession session = Util.getSession();
            
        utilisateurConnecte = (Utilisateur) session.getAttribute("Utilisateur");
        return utilisateurConnecte;
    }

    public void setUtilisateurConnecte(Utilisateur utilisateurConnecte) {
        this.utilisateurConnecte = utilisateurConnecte;
    }
    
    
    
        public String login() {
        boolean result = gestionnaireUtilisateur.login(uname, password);
      
        if (result) {
  Utilisateur u = gestionnaireUtilisateur.getUtilisateur(uname, password);
            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);     
            session.setAttribute("Utilisateur", u);
            return "faces/ListeClients.xhtml";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Invalid Login!",
                    "Please Try Again!"));
            
             // invalidate session, and redirect to other pages
             //message = "Invalid Login. Please Try Again!";
            return "login";
        }
    }
 
    public String logout() {
      HttpSession session = Util.getSession();
      session.invalidate();
      return "login";
   }
    
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
    }
    
    
}
