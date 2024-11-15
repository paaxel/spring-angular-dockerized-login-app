package it.palex.provaLogin.dbaccess.entities;

import it.palex.provaLogin.dbaccess.entities.generic.DatabaseCheckableEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Alessandro Pagliaro
 */
@Entity
@Table(name = "users_access_token")
public class UsersAccessToken implements Serializable, DatabaseCheckableEntity {

    private static final long serialVersionUID = 1914218861137198390L;

    public static final int MIN_TOKEN_ID_SIZE = 16;
    public static final int MAX_TOKEN_ID_SIZE = 255;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = MIN_TOKEN_ID_SIZE, max = MAX_TOKEN_ID_SIZE)
    @Column(name = "token_id")
    private String tokenId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "issued_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date issuedDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "expiration_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @JoinColumn(name = "fk_id_users_auth_details", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private UsersAuthDetails fkIdUsersAuthDetails;

    public UsersAccessToken() {
    }


    @Override
    public String whyCannotBeInsertedInDatabase() {
        String why = "isValidTokenId:" + isValidTokenId(this.tokenId) +
                "isValidIssueDate: " + isValidIssueDate(this.issuedDate) +
                "isValidExpirationDate:" + isValidExpirationDate(this.expirationDate);

        return why;
    }

    @Override
    public boolean canBeInsertedInDatabase() {
        boolean isValid = isValidTokenId(this.tokenId)
                && isValidIssueDate(this.issuedDate)
                && isValidExpirationDate(this.expirationDate);

        return isValid;
    }


    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public static boolean isValidTokenId(String tokenId) {
        if(tokenId==null) {
            return false;
        }
        if( tokenId.length() < MIN_TOKEN_ID_SIZE || tokenId.length() > MAX_TOKEN_ID_SIZE) {
            return false;
        }
        return true;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public static boolean isValidIssueDate(Date issuedDate) {
        return issuedDate != null;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public static boolean isValidExpirationDate(Date expirationDate) {
        return expirationDate != null;
    }

    public UsersAuthDetails getFkIdUsersAuthDetails() {
        return fkIdUsersAuthDetails;
    }

    public void setFkIdUsersAuthDetails(UsersAuthDetails fkIdUsersAuthDetails) {
        this.fkIdUsersAuthDetails = fkIdUsersAuthDetails;
    }

    public static boolean isValidFkIdUsersAuthDetails(UsersAuthDetails fkIdUsersAuthDetails) {
        return fkIdUsersAuthDetails != null;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tokenId != null ? tokenId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsersAccessToken)) {
            return false;
        }
        UsersAccessToken other = (UsersAccessToken) object;
        if ((this.tokenId == null && other.tokenId != null) || (this.tokenId != null && !this.tokenId.equals(other.tokenId))) {
            return false;
        }
        return true;
    }

}
