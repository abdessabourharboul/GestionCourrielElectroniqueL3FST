package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class UniteAdministrative implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uniteadministrative_id")
    private Long id;
    private String titre;
    @OneToOne
    private UniteAdministrative uniteAdministrativePere;
    @OneToMany(mappedBy = "uniteAdministrative")
    private List<User> users;
    @ManyToMany(mappedBy = "uniteAdministratives")
    private List<Courrier> courriers;
    @OneToMany(mappedBy = "uniteDepart")
    private List<CourrierInterne> courrierInternesD;
    @ManyToMany(mappedBy = "uniteDestinataires")
    private List<CourrierInterne> courrierInternesI;
    @OneToMany(mappedBy = "uniteDepart")
    private List<CourrierDepart> courrierDeparts;
    @OneToMany(mappedBy = "uniteTrait")
    private List<Traitement> traitements;
    @ManyToMany(mappedBy = "uniteDest")
    private List<Traitement> traitementsDestinataires;
    @OneToMany(mappedBy = "uniteAdministrativeDepart")
    private List<Consigne> consignes;
    @ManyToMany(mappedBy = "uniteDestinataires")
    private List<Consigne> consignesDestinataires;
    @ManyToMany(mappedBy = "lu")
    private List<Courrier> courriersLu;
    @ManyToMany(mappedBy = "favoris")
    private List<Courrier> courriersFavoris;
    @ManyToMany(mappedBy = "clotures")
    private List<CourrierInterne> courrierInternesClotures;
    @ManyToMany(mappedBy = "clotures")
    private List<Courrier> courriersClotures;
    @ManyToMany(mappedBy = "favoris")
    private List<CourrierInterne> courrierInternesFavoris;
    @ManyToMany(mappedBy = "lu")
    private List<CourrierInterne> courrierInternesLus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public UniteAdministrative getUniteAdministrativePere() {
        return uniteAdministrativePere;
    }

    public void setUniteAdministrativePere(UniteAdministrative uniteAdministrativePere) {
        this.uniteAdministrativePere = uniteAdministrativePere;
    }

    public List<CourrierInterne> getCourrierInternesClotures() {
        return courrierInternesClotures;
    }

    public void setCourrierInternesClotures(List<CourrierInterne> courrierInternesClotures) {
        this.courrierInternesClotures = courrierInternesClotures;
    }

    public List<Courrier> getCourriersClotures() {
        return courriersClotures;
    }

    public void setCourriersClotures(List<Courrier> courriersClotures) {
        this.courriersClotures = courriersClotures;
    }

    public List<CourrierInterne> getCourrierInternesFavoris() {
        return courrierInternesFavoris;
    }

    public void setCourrierInternesFavoris(List<CourrierInterne> courrierInternesFavoris) {
        this.courrierInternesFavoris = courrierInternesFavoris;
    }

    public List<CourrierInterne> getCourrierInternesLus() {
        return courrierInternesLus;
    }

    public void setCourrierInternesLus(List<CourrierInterne> courrierInternesLus) {
        this.courrierInternesLus = courrierInternesLus;
    }

    public List<Courrier> getCourriersLu() {
        if (courriersLu == null) {
            courriersLu = new ArrayList<>();
        }
        return courriersLu;
    }

    public void setCourriersLu(List<Courrier> courriersLu) {
        this.courriersLu = courriersLu;
    }

    public List<Courrier> getCourriersFavoris() {
        if (courriersFavoris == null) {
            courriersFavoris = new ArrayList<>();
        }
        return courriersFavoris;
    }

    public void setCourriersFavoris(List<Courrier> courriersFavoris) {
        this.courriersFavoris = courriersFavoris;
    }

    public List<Consigne> getConsignesDestinataires() {
        if (consignesDestinataires == null) {
            consignesDestinataires = new ArrayList<>();
        }
        return consignesDestinataires;
    }

    public void setConsignesDestinataires(List<Consigne> consignesDestinataires) {
        this.consignesDestinataires = consignesDestinataires;
    }

    public List<Traitement> getTraitements() {
        if (traitements == null) {
            traitements = new ArrayList<>();
        }
        return traitements;
    }

    public void setTraitements(List<Traitement> traitements) {
        this.traitements = traitements;
    }

    public List<Traitement> getTraitementsDestinataires() {
        if (traitementsDestinataires == null) {
            traitementsDestinataires = new ArrayList<>();
        }
        return traitementsDestinataires;
    }

    public void setTraitementsDestinataires(List<Traitement> traitementsDestinataires) {
        this.traitementsDestinataires = traitementsDestinataires;
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

    public List<Consigne> getConsignes() {
        if (consignes == null) {
            consignes = new ArrayList<>();
        }
        return consignes;
    }

    public void setConsignes(List<Consigne> consignes) {
        this.consignes = consignes;
    }

    public List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<>();
        }
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<CourrierInterne> getCourrierInternesD() {
        return courrierInternesD;
    }

    public void setCourrierInternesD(List<CourrierInterne> courrierInternesD) {
        this.courrierInternesD = courrierInternesD;
    }

    public List<CourrierInterne> getCourrierInternesI() {
        return courrierInternesI;
    }

    public void setCourrierInternesI(List<CourrierInterne> courrierInternesI) {
        this.courrierInternesI = courrierInternesI;
    }

    public List<CourrierDepart> getCourrierDeparts() {
        return courrierDeparts;
    }

    public void setCourrierDeparts(List<CourrierDepart> courrierDeparts) {
        this.courrierDeparts = courrierDeparts;
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
        if (!(object instanceof UniteAdministrative)) {
            return false;
        }
        UniteAdministrative other = (UniteAdministrative) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + " " + titre;
    }

}
