package it.palex.provaLogin.dbaccess.exceptions;

import it.palex.provaLogin.dbaccess.entities.UsersAccessToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExceptionsTests {

    @Test
    public void checkDataCannotBeInsertedInDatabase() {
        UsersAccessToken token = new UsersAccessToken();
        token.setTokenId("id");

        try{

            throw new DataCannotBeInsertedInDatabase(token);

        }catch(DataCannotBeInsertedInDatabase ex){
            Assertions.assertEquals(ex.getMessage(), token.whyCannotBeInsertedInDatabase());
        }
    }
}
