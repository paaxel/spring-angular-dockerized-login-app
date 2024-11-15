package it.palex.provaLogin.dbaccess.repository;

import it.palex.provaLogin.dbaccess.entities.UsersAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 
 * @author Alessandro Pagliaro
 *
 */
@Repository
public interface UsersAccessTokenRepository extends JpaRepository<UsersAccessToken, String>,
				QuerydslPredicateExecutor<UsersAccessToken> {

    @Modifying
    @Query("DELETE from UsersAccessToken where expirationDate < ?1")
    int deleteAllTokenExpiredBefore(Date date);
}
