package it.palex.provaLogin.dbservices;

import com.querydsl.core.BooleanBuilder;
import it.palex.provaLogin.dbaccess.entities.QUsersAccessToken;
import it.palex.provaLogin.dbaccess.entities.UsersAccessToken;
import it.palex.provaLogin.dbaccess.entities.UsersAuthDetails;
import it.palex.provaLogin.dbaccess.exceptions.DataCannotBeInsertedInDatabase;
import it.palex.provaLogin.dbaccess.repository.UsersAccessTokenRepository;
import it.palex.provaLogin.dbservices.service.UsersAccessTokenService;
import it.palex.provaLogin.library.utils.DateUtility;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserAccessTokenServiceTests {

    @Mock
    private UsersAccessTokenRepository usersAccessTokenRepository;
    @InjectMocks
    @Resource
    private UsersAccessTokenService usersAccessTokenServiceMock;


    @BeforeEach
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void checkFindById() {
        final String tokenId = UUID.randomUUID().toString();
        final UsersAccessToken token = new UsersAccessToken();
        token.setTokenId(tokenId);

        BooleanBuilder cond = new BooleanBuilder();
        cond.and(QUsersAccessToken.usersAccessToken.tokenId.eq(tokenId));

        when(usersAccessTokenRepository.findAll(cond)).thenReturn(Arrays.asList(token));

        UsersAccessToken tokenFound = this.usersAccessTokenServiceMock.findByTokenId(tokenId);

        Assertions.assertNotNull(tokenFound);
        Assertions.assertEquals(tokenFound.getTokenId(), token.getTokenId());
    }

    @Test()
    public void checkExceptionOnBadDataSaveOrUpdate() {
        final String tokenId = UUID.randomUUID().toString();
        final UsersAccessToken token = new UsersAccessToken();
        token.setTokenId(tokenId);
        Assertions.assertThrows(
                DataCannotBeInsertedInDatabase.class,
                () -> this.usersAccessTokenServiceMock.saveOrUpdate(token)
        );
    }


    @Test
    public void checkSaveOrUpdate(){
        final String tokenId = UUID.randomUUID().toString();
        final Date currentDate = DateUtility.getCurrentDateInUTC();
        final Date expDate = DateUtility.addSecondsToDate(currentDate, 3600);
        final Integer userId = 1;
        final UsersAuthDetails userDetails = new UsersAuthDetails();
        userDetails.setId(userId);

        UsersAccessToken token = new UsersAccessToken();
        token.setIssuedDate(currentDate);
        token.setExpirationDate(expDate);
        token.setFkIdUsersAuthDetails(userDetails);
        token.setTokenId(tokenId);
        token.setFkIdUsersAuthDetails(userDetails);

        when(usersAccessTokenRepository.save(token)).thenReturn(token);

        UsersAccessToken savedToken = this.usersAccessTokenServiceMock.saveOrUpdate(token);

        Assertions.assertEquals(token, savedToken);
    }
}
