package it.palex.provaLogin.dbaccess.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Alessandro Pagliaro
 */
@Entity
@Table(name = "permissions")
public class Permissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "authority")
    private String authority;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkAuthority")
    private Collection<PermissionGroup> permissionGroupCollection;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authority")
    private Collection<Authorities> authoritiesCollection;

    public Permissions() {
    }

    public Permissions(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Collection<PermissionGroup> getPermissionGroupCollection() {
        return permissionGroupCollection;
    }

    public void setPermissionGroupCollection(Collection<PermissionGroup> permissionGroupCollection) {
        this.permissionGroupCollection = permissionGroupCollection;
    }

    public Collection<Authorities> getAuthoritiesCollection() {
        return authoritiesCollection;
    }

    public void setAuthoritiesCollection(Collection<Authorities> authoritiesCollection) {
        this.authoritiesCollection = authoritiesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authority != null ? authority.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permissions)) {
            return false;
        }
        Permissions other = (Permissions) object;
        return (this.authority != null || other.authority == null) && (this.authority == null || this.authority.equals(other.authority));
    }

    @Override
    public String toString() {
        return "Permissions[ authority=" + authority + " ]";
    }

}
