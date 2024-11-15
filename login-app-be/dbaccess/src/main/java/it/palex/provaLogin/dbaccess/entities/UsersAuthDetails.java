package it.palex.provaLogin.dbaccess.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import it.palex.provaLogin.dbaccess.entities.generic.AuditableEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


/**
 *
 * @author Alessandro Pagliaro
 */
@Entity
@Table(name = "users_auth_details")
public class UsersAuthDetails extends AuditableEntity implements Serializable, UserDetails {
	
    private static final long serialVersionUID = 4767677938945546L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    public static final int MIN_USERNAME_SIZE = 3;
    public static final int MAX_USERNAME_SIZE = 255;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = MIN_USERNAME_SIZE, max = MAX_USERNAME_SIZE)
    @Column(name = "username", unique = true)
    private String username;
    
    public static final int MIN_HASHED_PASSWORD_SIZE = 16;
    public static final int MAX_HASHED_PASSWORD_SIZE = 255;
    
    
    @Size(min = MIN_HASHED_PASSWORD_SIZE, max = MAX_HASHED_PASSWORD_SIZE)
    @Column(name = "hashed_password")
    private String hashedPassword;

    public static final int MIN_NAME_SIZE = 2;
    public static final int MAX_NAME_SIZE = 255;

    @Size(min = MIN_NAME_SIZE, max = MAX_NAME_SIZE)
    @Column(name = "name")
    private String name;

    public static final int MIN_SURNAME_SIZE = 2;
    public static final int MAX_SURNAME_SIZE = 255;

    @Size(min = MIN_SURNAME_SIZE, max = MAX_SURNAME_SIZE)
    @Column(name = "surname")
    private String surname;

    @Basic(optional = false)
    @NotNull
    @Column(name = "last_password_change_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChangeDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISENABLED")
    private boolean isEnabled;  
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISACCOUNTNONEXPIRED")
    private boolean isAccountNonExpired;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISACCOUNTNONLOCKED")
    private boolean isAccountNonLocked;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISCREDENTIALSNONEXPIRED")
    private boolean isCredentialsNonExpired;

    public static final int PERMISSION_GROUP_MAX_SIZE = 50;

    @JoinColumn(name = "permission_group_name", referencedColumnName = "name")
    @ManyToOne
    private PermissionGroupLabel permissionGroupName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkIdUsersAuthDetails", fetch = FetchType.EAGER)
    private List<Authorities> authoritiesList;


    public UsersAuthDetails() {
    }

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Date getLastPasswordChangeDate() {
        return lastPasswordChangeDate;
    }

    public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }
    
    public static boolean isValidLastPasswordChangeDate(Date lastPasswordChangeDate) {
    	return lastPasswordChangeDate!=null;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
	public boolean isEnabled() {
		return this.isEnabled;
	}
    
    public static boolean isValidIsEnabled(Boolean isEnabled) {
    	return isEnabled!=null;
    }

    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    public void setIsAccountNonExpired(Boolean isAccountNonExpired) {
        this.isAccountNonExpired = isAccountNonExpired;
    }
    
    public static boolean isValidIsAccountNonExpired(Boolean isAccountNonExpired) {
    	return isAccountNonExpired!=null;
    }

    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    public void setIsAccountNonLocked(Boolean isAccountNonLocked) {
        this.isAccountNonLocked = isAccountNonLocked;
    }
    
    public static boolean isValidIsAccountNonLocked(Boolean isAccountNonLocked) {
    	return isAccountNonLocked!=null;
    }

    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    public void setIsCredentialsNonExpired(Boolean isCredentialsNonExpired) {
        this.isCredentialsNonExpired = isCredentialsNonExpired;
    }

    @Override
	public String getPassword() {
		return this.getHashedPassword();
	}

    @Override
   	public Collection<? extends GrantedAuthority> getAuthorities() {
   		Collection<GrantedAuthority> grantedAuths = new ArrayList<>();
   		
   		if(this.getAuthoritiesList()!=null){
   			for(Authorities auth: this.getAuthoritiesList()){
   		    	grantedAuths.add(
   		    			new SimpleGrantedAuthority(
   		    					auth.getAuthority().getAuthority()
   		    					)
   		    			);
   		    }
   		}
   	    return grantedAuths;
   	}

    public List<Authorities> getAuthoritiesList() {
        return authoritiesList;
    }

    public void setAuthoritiesList(List<Authorities> authoritiesList) {
        this.authoritiesList = authoritiesList;
    }

    public PermissionGroupLabel getPermissionGroup() {
		return permissionGroupName;
	}
    
	public void setPermissionGroup(PermissionGroupLabel permissionGroup) {
		this.permissionGroupName = permissionGroup;
	}
	
	public static boolean isValidPermissionGroup(PermissionGroupLabel permissionGroupName) {
		if(permissionGroupName==null) {
			return false;
		}		
		return true;
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
        if (!(object instanceof UsersAuthDetails)) {
            return false;
        }
        UsersAuthDetails other = (UsersAuthDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UsersAuthDetails[ id=" + id + " ]";
    }
    
}
