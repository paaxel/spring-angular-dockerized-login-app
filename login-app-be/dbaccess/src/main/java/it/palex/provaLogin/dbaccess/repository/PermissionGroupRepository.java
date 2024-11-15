package it.palex.provaLogin.dbaccess.repository;

import it.palex.provaLogin.dbaccess.entities.PermissionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Alessandro Pagliaro
 *
 */
@Repository
public interface PermissionGroupRepository extends JpaRepository<PermissionGroup, Integer>,
		QuerydslPredicateExecutor<PermissionGroup> {
	
	
	
}
