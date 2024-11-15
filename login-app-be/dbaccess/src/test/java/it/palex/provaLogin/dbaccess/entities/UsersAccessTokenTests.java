package it.palex.provaLogin.dbaccess.entities;

import it.palex.provaLogin.library.utils.DateUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UsersAccessTokenTests {

    @Test
    public void checkUserAccessTokenSetterGetter() {
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

        Assertions.assertEquals(token.getIssuedDate(), currentDate);
        Assertions.assertNotNull(token.getIssuedDate());

        Assertions.assertEquals(token.getExpirationDate(), expDate);
        Assertions.assertNotNull(token.getExpirationDate());

        Assertions.assertEquals(token.getFkIdUsersAuthDetails().getId(), userId);
        Assertions.assertNotNull(token.getFkIdUsersAuthDetails().getId());

        Assertions.assertEquals(token.getTokenId(), tokenId);
        Assertions.assertNotNull(token.getTokenId());
    }
}
