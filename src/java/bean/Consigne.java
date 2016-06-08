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

@Entity
public class Consigne implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consigne_id")
    private Long id;
    private String contenu;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateConsigne;
    @ManyToOne
    private UniteAdministrative uniteAdministrativeDepart;
    @ManyToOne
    private Courrier courrier;
    @ManyToOne
    private CourrierInterne courrierInterne;
    @JoinTable(name = "consigne_uniteadministrative",
            joinColumns = {
                @JoinColumn(name = "consigne_id", referencedColumnName = "consigne_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "uniteadministrative_id", referencedColumnName = "uniteadministrative_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<UniteAdministrative> uniteDestinataires;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateConsigne() {
        return dateConsigne;
    }

    public void setDateConsigne(Date dateConsigne) {
        this.dateConsigne = dateConsigne;
    }

    public UniteAdministrative getUniteAdministrativeDepart() {
        if (uniteAdministrativeDepart == null) {
            uniteAdministrativeDepart = new UniteAdministrative();
        }
        return uniteAdministrativeDepart;
    }

    public void setUniteAdministrativeDepart(UniteAdministrative uniteAdministrativeDepart) {
        this.uniteAdministrativeDepart = uniteAdministrativeDepart;
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
        return courrierInterne;
    }

    public void setCourrierInterne(CourrierInterne courrierInterne) {
        this.courrierInterne = courrierInterne;
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
        if (!(object instanceof Consigne)) {
            return false;
        }
        Consigne other = (Consigne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }

}
