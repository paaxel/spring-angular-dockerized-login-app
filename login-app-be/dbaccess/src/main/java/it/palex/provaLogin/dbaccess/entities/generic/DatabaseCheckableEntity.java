package it.palex.provaLogin.dbaccess.entities.generic;

/**
 * 
 * @author Alessandro Pagliaro
 *
 */
public interface DatabaseCheckableEntity {

	public boolean canBeInsertedInDatabase();
	
	default public String whyCannotBeInsertedInDatabase() {
		return this.toString();
	}
	
}
