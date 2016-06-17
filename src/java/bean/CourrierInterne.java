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
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

@Entity
public class CourrierInterne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courrierinterne_id")
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDepart;
    private Long numOrdre;
    private String type;
    private String nature;
    private String contenu;
    private String objet;
    @OneToMany(mappedBy = "courrierInterne")
    private List<Consigne> consignes;
    @ManyToOne
    private UniteAdministrative uniteDepart;
    @OneToMany(mappedBy = "courrierInterne")
    private List<Traitement> traitements;
    private boolean validationCabinet = false;
    private boolean validationSg = false;
    private boolean validationDai = false;
    @JoinTable(name = "courrierinterne_uniteadministrative",
            joinColumns = {
                @JoinColumn(name = "courrierinterne_id", referencedColumnName = "courrierinterne_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "uniteadministrative_id", referencedColumnName = "uniteadministrative_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UniteAdministrative> uniteDestinataires;
    @JoinTable(name = "courrierInterneLus",
            joinColumns = {
                @JoinColumn(name = "courrierinterne_id", referencedColumnName = "courrierinterne_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "uniteadministrative_id", referencedColumnName = "uniteadministrative_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UniteAdministrative> lu;
    @JoinTable(name = "courrierInterneFavoris",
            joinColumns = {
                @JoinColumn(name = "courrierinterne_id", referencedColumnName = "courrierinterne_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "uniteadministrative_id", referencedColumnName = "uniteadministrative_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UniteAdministrative> favoris;

    public List<UniteAdministrative> getLu() {
        if (lu == null) {
            lu = new ArrayList<>();
        }
        return lu;
    }

    public void setLu(List<UniteAdministrative> lu) {
        this.lu = lu;
    }

    public List<UniteAdministrative> getFavoris() {
        if (favoris == null) {
            favoris = new ArrayList<>();
        }
        return favoris;
    }

    public void setFavoris(List<UniteAdministrative> favoris) {
        this.favoris = favoris;
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

    public boolean isValidationCabinet() {
        return validationCabinet;
    }

    public void setValidationCabinet(boolean validationCabinet) {
        this.validationCabinet = validationCabinet;
    }

    public boolean isValidationSg() {
        return validationSg;
    }

    public void setValidationSg(boolean validationSg) {
        this.validationSg = validationSg;
    }

    public boolean isValidationDai() {
        return validationDai;
    }

    public void setValidationDai(boolean validationDai) {
        this.validationDai = validationDai;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getDateDepart() {
//        if (dateDepart == null) {
//            dateDepart = new Date();
//        }
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Long getNumOrdre() {
        return numOrdre;
    }

    public void setNumOrdre(Long numOrdre) {
        this.numOrdre = numOrdre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public UniteAdministrative getUniteDepart() {
//        User user = SessionUtil.getConnectedUser();
//        if (uniteDepart == null) {
//            uniteDepart = user.getUniteAdministrative();
//        }
        return uniteDepart;
    }

    public void setUniteDepart(UniteAdministrative uniteDepart) {
        this.uniteDepart = uniteDepart;
    }

    public List<UniteAdministrative> getUniteDestinataires() {
        if (uniteDestinataires == null) {
            uniteDestinataires = new ArrayList<>();
        }
        return uniteDestinataires;
    }

    public void setUniteDestinataires(List<UniteAdministrative> uniteDestinataires) {
        this.uniteDestinataires = uniteDestinataires;
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
        if (!(object instanceof CourrierInterne)) {
            return false;
        }
        CourrierInterne other = (CourrierInterne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + contenu;
    }

}
