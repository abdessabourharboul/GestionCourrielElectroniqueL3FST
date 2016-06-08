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

/**
 *
 * @author asus
 */
@Entity
public class Courrier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "courrier_id")
    private Long id;
    private Long numOrdreExt;
//    private String expediteur;
    private String type;
    private String nature;
    private String objet;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateArrivee;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateSaisie;
    @JoinTable(name = "courrier_uniteadministrative",
            joinColumns = {
                @JoinColumn(name = "courrier_id", referencedColumnName = "courrier_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "uniteadministrative_id", referencedColumnName = "uniteadministrative_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UniteAdministrative> uniteAdministratives;
    @OneToMany(mappedBy = "courrier")
    private List<PieceJointe> pieceJointes;
    @OneToMany(mappedBy = "courrier")
    private List<Consigne> consignes;
    @OneToMany(mappedBy = "courrier")
    private List<Traitement> traitements;
    @ManyToOne
    private Contact contact;
    private boolean validationCabinet = false;
    private boolean validationSg = false;
    private boolean validationDai = false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumOrdreExt() {
        return numOrdreExt;
    }

    public void setNumOrdreExt(Long numOrdreExt) {
        this.numOrdreExt = numOrdreExt;
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

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public Date getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Date dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Date getDateSaisie() {
        return dateSaisie;
    }

    public void setDateSaisie(Date dateSaisie) {
        this.dateSaisie = dateSaisie;
    }

    public List<UniteAdministrative> getUniteAdministratives() {
        if (uniteAdministratives == null) {
            uniteAdministratives = new ArrayList<>();
        }
        return uniteAdministratives;
    }

    public void setUniteAdministratives(List<UniteAdministrative> uniteAdministratives) {
        this.uniteAdministratives = uniteAdministratives;
    }

    public List<PieceJointe> getPieceJointes() {
        if (pieceJointes == null) {
            pieceJointes = new ArrayList<>();
        }
        return pieceJointes;
    }

    public void setPieceJointes(List<PieceJointe> pieceJointes) {
        this.pieceJointes = pieceJointes;
    }

    public List<Consigne> getConsignes() {
        if (consignes == null) {
            consignes = new ArrayList();
        }
        return consignes;
    }

    public void setConsignes(List<Consigne> consignes) {
        this.consignes = consignes;
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

    public List<Traitement> getTraitements() {
        if (traitements == null) {
            traitements = new ArrayList<>();
        }
        return traitements;
    }

    public void setTraitements(List<Traitement> traitements) {
        this.traitements = traitements;
    }

    public Contact getContact() {
        if (contact == null) {
            contact = new Contact();
        }
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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
        if (!(object instanceof Courrier)) {
            return false;
        }
        Courrier other = (Courrier) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id + " " + objet;
    }

}
