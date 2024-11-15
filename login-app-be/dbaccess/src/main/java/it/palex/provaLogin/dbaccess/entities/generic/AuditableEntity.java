package it.palex.provaLogin.dbaccess.entities.generic;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;

import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Alessandro Pagliaro
 *
 */
@MappedSuperclass
@EntityListeners({BasicAuditListener.class})
public class AuditableEntity implements Serializable {

	private static final long serialVersionUID = 7466784388430769390L;
	
	public static final int MAX_CREATED_BY_SIZE = 512;
	public static final int MAX_LAST_MODIFIED_BY_SIZE = 512;
	
	@Size(max = MAX_CREATED_BY_SIZE)
	@Column(name = "CREATED_BY")
    private String createdBy;
	  
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE")
    private Date createdDate;
    
    @Size(max = MAX_LAST_MODIFIED_BY_SIZE)
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;
    
    @Temporal(jakarta.persistence.TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFIED_DATE")
    private Date lastModifiedDate;

    
    
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = StringUtils.substring(createdBy, 0, MAX_CREATED_BY_SIZE);
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = StringUtils.substring(lastModifiedBy, 0, MAX_LAST_MODIFIED_BY_SIZE);
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@Override
	public String toString() {
		return "AuditableEntity [createdBy=" + createdBy + ", createdDate=" + createdDate + ", lastModifiedBy="
				+ lastModifiedBy + ", lastModifiedDate=" + lastModifiedDate + "]";
	}
    

}
