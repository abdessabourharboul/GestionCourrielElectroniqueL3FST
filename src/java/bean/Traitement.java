/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Yassine
 */
@Entity
public class Traitement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "traitement_id")
    private Long id;
    private String contenu;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateTraitement;
    @ManyToOne
    private UniteAdministrative uniteTrait;
    @ManyToOne
    private Courrier courrier;
    @ManyToOne
    private CourrierInterne courrierInterne;
    @JoinTable(name = "traitement_uniteadministrative",
            joinColumns = {
                @JoinColumn(name = "traitement_id", referencedColumnName = "traitement_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "uniteadministrative_id", referencedColumnName = "uniteadministrative_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UniteAdministrative> uniteDest;

    public List<UniteAdministrative> getUniteDest() {
        if (uniteDest == null) {
            uniteDest = new ArrayList();
        }
        return uniteDest;
    }

    public void setUniteDest(List<UniteAdministrative> uniteDest) {
        this.uniteDest = uniteDest;
    }

    public Courrier getCourrier() {
        if (courrier == null) {
            courrier = new Courrier();
        }
        return courrier;
    }

    public void setCourrier(Courrier courrier) {
        this.courrier = courrier;
    }

    public CourrierInterne getCourrierInterne() {
        if (courrierInterne == null) {
            courrierInterne = new CourrierInterne();
        }

        return courrierInterne;
    }

    public void setCourrierInterne(CourrierInterne courrierInterne) {
        this.courrierInterne = courrierInterne;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateTraitement() {
        if (dateTraitement == null) {
            dateTraitement = new Date();
        }
        return dateTraitement;
    }

    public void setDateTraitement(Date dateTraitement) {
        this.dateTraitement = dateTraitement;
    }

    public UniteAdministrative getUniteTrait() {
        if (uniteTrait == null) {
            uniteTrait = new UniteAdministrative();
        }
        return uniteTrait;
    }

    public void setUniteTrait(UniteAdministrative uniteTrait) {
        this.uniteTrait = uniteTrait;
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
        if (!(object instanceof Traitement)) {
            return false;
        }
        Traitement other = (Traitement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Traitement[ id=" + id + " ]";
    }

}
