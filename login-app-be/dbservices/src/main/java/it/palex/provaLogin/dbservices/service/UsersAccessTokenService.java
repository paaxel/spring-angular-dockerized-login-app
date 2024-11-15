package it.palex.provaLogin.dbservices.service;


import com.querydsl.core.BooleanBuilder;
import it.palex.provaLogin.dbaccess.entities.QUsersAccessToken;
import it.palex.provaLogin.dbaccess.entities.UsersAccessToken;
import it.palex.provaLogin.dbaccess.exceptions.DataCannotBeInsertedInDatabase;
import it.palex.provaLogin.dbaccess.repository.UsersAccessTokenRepository;
import it.palex.provaLogin.library.service.BasicGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @author Alessandro Pagliaro
 */
@Service
public class UsersAccessTokenService implements BasicGenericService {

    private final QUsersAccessToken QUAT = QUsersAccessToken.usersAccessToken;

    @Autowired
    private UsersAccessTokenRepository userAccessTokenRepo;

    @Transactional
    public UsersAccessToken saveOrUpdate(UsersAccessToken accessToken) {
        if (accessToken == null) {
            throw new NullPointerException();
        }
        if (!accessToken.canBeInsertedInDatabase()) {
            throw new DataCannotBeInsertedInDatabase(accessToken);
        }

        return this.userAccessTokenRepo.save(accessToken);
    }

    public UsersAccessToken findByTokenId(String tokenId) {
        if(tokenId==null){
            return null;
        }
        BooleanBuilder cond = new BooleanBuilder();
        cond.and(QUAT.tokenId.eq(tokenId));

        final UsersAccessToken res = this.getFirstResultFromIterable(
                this.userAccessTokenRepo.findAll(cond)
        );

        return res;
    }
    public void delete(UsersAccessToken oldUserAccessToken) {
        if (oldUserAccessToken == null) {
            throw new NullPointerException();
        }

        this.userAccessTokenRepo.delete(oldUserAccessToken);
    }

    public int deleteAllTokenExpiredBefore(Date date) {
        if(date==null){
            throw new NullPointerException("date cannot be null");
        }
        int deletedTokens = this.userAccessTokenRepo.deleteAllTokenExpiredBefore(date);

        return deletedTokens;
    }


}