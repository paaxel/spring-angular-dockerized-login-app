package it.palex.provaLogin.dbaccess.entities;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 *
 * @author Alessandro Pagliaro
 */
@Entity
@Table(name = "permission_group")
public class PermissionGroup implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "name", referencedColumnName = "name")
    @ManyToOne(optional = false)
    private PermissionGroupLabel name;
    
    @JoinColumn(name = "fk_authority", referencedColumnName = "authority")
    @ManyToOne(optional = false)
    private Permissions fkAuthority;

    public PermissionGroup() {
    }

    public PermissionGroup(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PermissionGroupLabel getName() {
        return name;
    }

    public void setName(PermissionGroupLabel name) {
        this.name = name;
    }

    public Permissions getFkAuthority() {
        return fkAuthority;
    }

    public void setFkAuthority(Permissions fkAuthority) {
        this.fkAuthority = fkAuthority;
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
        if (!(object instanceof PermissionGroup)) {
            return false;
        }
        PermissionGroup other = (PermissionGroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PermissionGroup[ id=" + id + " ]";
    }
    
}
