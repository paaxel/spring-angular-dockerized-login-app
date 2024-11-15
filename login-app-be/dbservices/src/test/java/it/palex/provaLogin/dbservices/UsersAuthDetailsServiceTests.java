package it.palex.provaLogin.dbservices;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import it.palex.provaLogin.dbaccess.entities.QUsersAuthDetails;
import it.palex.provaLogin.dbaccess.entities.UsersAuthDetails;
import it.palex.provaLogin.dbaccess.repository.UsersAuthDetailsRepository;
import it.palex.provaLogin.dbservices.service.UsersAuthDetailsService;
import it.palex.provaLogin.library.rest.CustomExceptionHandler;
import it.palex.provaLogin.library.service.ApplicationVersionService;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UsersAuthDetailsServiceTests {

    @Mock
    private UsersAuthDetailsRepository userAuthDetailsRepoMock;
    @InjectMocks
    @Resource
    private UsersAuthDetailsService usersAuthDetailsServiceMock;

    @BeforeEach
    public void setupMocks() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void checkFindById() {
        final Integer userId = 0;
        final UsersAuthDetails user = new UsersAuthDetails();
        user.setId(userId);

        BooleanBuilder cond = new BooleanBuilder();
        cond.and(QUsersAuthDetails.usersAuthDetails.id.eq(userId));

        when(userAuthDetailsRepoMock.findAll(cond)).thenReturn(Arrays.asList(user));

        UsersAuthDetails userFound = this.usersAuthDetailsServiceMock.findById(userId);

        Assertions.assertNotNull(userFound);
        Assertions.assertEquals(userFound.getId(), userId);
    }

    @Test
    public void checkFindByUsername() {
        final Integer userId = 0;
        final String username = "username";
        final UsersAuthDetails user = new UsersAuthDetails();
        user.setId(userId);
        user.setUsername(username);

        BooleanBuilder cond = new BooleanBuilder();
        cond.and(QUsersAuthDetails.usersAuthDetails.username.equalsIgnoreCase(username));

        when(userAuthDetailsRepoMock.findAll(cond)).thenReturn(Arrays.asList(user));

        UsersAuthDetails userFound = this.usersAuthDetailsServiceMock.findByUsername(username);

        Assertions.assertNotNull(userFound);
        Assertions.assertEquals(user.getUsername(), userFound.getUsername());
    }


}
