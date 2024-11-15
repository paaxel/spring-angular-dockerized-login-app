package it.palex.provaLogin.dbaccess.entities;

import it.palex.provaLogin.library.utils.DateUtility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
public class UsersAuthDetailsTests {

    @Test
    public void checkUsersAuthDetails() {
        final Integer userId = 1;
        final String name = "Name";
        final String surname = "Name";
        final String username = "username";
        final String hashedPassword = "hashedPasswordhashedPasswordhashedPassword";
        final Date lastPasswordChangeDate = DateUtility.getCurrentDateInUTC();
        final boolean enabledBools = true;

        final UsersAuthDetails userDetails = new UsersAuthDetails();
        userDetails.setId(userId);
        userDetails.setName(name);
        userDetails.setSurname(surname);
        userDetails.setUsername(username);
        userDetails.setHashedPassword(hashedPassword);
        userDetails.setLastPasswordChangeDate(lastPasswordChangeDate);
        userDetails.setIsAccountNonExpired(enabledBools);
        userDetails.setIsAccountNonLocked(enabledBools);
        userDetails.setIsCredentialsNonExpired(enabledBools);
        userDetails.setIsEnabled(enabledBools);


        Assertions.assertEquals(userDetails.getId(), userId);
        Assertions.assertEquals(userDetails.getName(), name);
        Assertions.assertEquals(userDetails.getSurname(), surname);
        Assertions.assertEquals(userDetails.getUsername(), username);
        Assertions.assertEquals(userDetails.getHashedPassword(), hashedPassword);
        Assertions.assertEquals(userDetails.getLastPasswordChangeDate(), lastPasswordChangeDate);
        Assertions.assertEquals(userDetails.isEnabled(), enabledBools);
        Assertions.assertEquals(userDetails.isAccountNonExpired(), enabledBools);
        Assertions.assertEquals(userDetails.isAccountNonLocked(), enabledBools);
        Assertions.assertEquals(userDetails.isCredentialsNonExpired(), enabledBools);
    }
}
