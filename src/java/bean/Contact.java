/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Yassine
 */
@Entity
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String ville;
    private String numTel;
    private String adresse;
    private String type;
    @OneToMany(mappedBy = "contact")
    private List<Courrier> courriers;
    @OneToMany(mappedBy = "contact")
    private List<CourrierDepart> courrierDeparts;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Courrier> getCourriers() {
        if (courriers == null) {
            courriers = new ArrayList<>();
        }
        return courriers;
    }

    public void setCourriers(List<Courrier> courriers) {
        this.courriers = courriers;
    }

    public List<CourrierDepart> getCourrierDeparts() {
        if (courrierDeparts == null) {
            courrierDeparts = new ArrayList<>();
        }
        return courrierDeparts;
    }

    public void setCourrierDeparts(List<CourrierDepart> courrierDeparts) {
        this.courrierDeparts = courrierDeparts;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Contact[ id=" + id + " ]";
    }

}
