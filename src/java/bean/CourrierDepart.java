package bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class CourrierDepart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateDepart;
    private Long numOrdre;
    private String type;
    private String nature;
    private String contenu;
    private String objet;
    @ManyToOne
    private UniteAdministrative uniteDepart;
    @ManyToOne
    private Contact contact;
    private boolean validationCabinet = false;
    private boolean validationSg = false;
    private boolean validationDai = false;

    public Contact getContact() {
        if (contact == null) {
            contact = new Contact();
        }
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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

    public Date getDateDepart() {
        if (dateDepart == null) {
            dateDepart = new Date();
        }
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
        if (uniteDepart == null) {
            uniteDepart = new UniteAdministrative();
        }
        return uniteDepart;
    }

    public void setUniteDepart(UniteAdministrative uniteDepart) {
        this.uniteDepart = uniteDepart;
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
        if (!(object instanceof CourrierDepart)) {
            return false;
        }
        CourrierDepart other = (CourrierDepart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bean.Courrier[ id=" + id + " ]";
    }

}
