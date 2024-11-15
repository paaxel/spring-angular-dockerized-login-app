package it.palex.provaLogin.dbservices.service;

import com.querydsl.core.BooleanBuilder;
import it.palex.provaLogin.dbaccess.entities.QUsersAuthDetails;
import it.palex.provaLogin.dbaccess.entities.UsersAuthDetails;
import it.palex.provaLogin.dbaccess.repository.UsersAuthDetailsRepository;
import it.palex.provaLogin.library.service.BasicGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
@Service
public class UsersAuthDetailsService implements BasicGenericService {

    private final QUsersAuthDetails QUAD = QUsersAuthDetails.usersAuthDetails;

    @Autowired
    private UsersAuthDetailsRepository userAuthDetailsRepo;

    public UsersAuthDetails findById(Integer userId) {
        if(userId==null){
            return null;
        }
        BooleanBuilder cond = new BooleanBuilder();
        cond.and(QUAD.id.eq(userId));

        final UsersAuthDetails user = this.getFirstResultFromIterable(
                this.userAuthDetailsRepo.findAll(cond)
        );

        return user;
    }

    public UsersAuthDetails findByUsername(String username) {
        if(username==null) {
            return null;
        }
        BooleanBuilder cond = new BooleanBuilder();
        cond.and(QUAD.username.equalsIgnoreCase(username));

        final UsersAuthDetails user = this.getFirstResultFromIterable(this.userAuthDetailsRepo.findAll(cond));

        return user;
    }
}
