package it.palex.provaLogin.dbaccess.repository;

import it.palex.provaLogin.dbaccess.entities.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


/**
 * 
 * @author Alessandro Pagliaro
 *
 */
@Repository
public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer>,
					QuerydslPredicateExecutor<Authorities> {

}
