/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author asus
 */
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "user_id")
    private String id;
    private String password;
    @JoinTable(name = "user_role",
            joinColumns = {
                @JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {
                @JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private boolean logged = false;
    @ManyToOne
    private UniteAdministrative uniteAdministrative;
    private String language;

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UniteAdministrative getUniteAdministrative() {
//        if (uniteAdministrative == null) {
//            uniteAdministrative = new UniteAdministrative();
//        }
        return uniteAdministrative;
    }

    public void setUniteAdministrative(UniteAdministrative uniteAdministrative) {
        this.uniteAdministrative = uniteAdministrative;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public List<Role> getRoles() {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toUpperCase();
    }

}
