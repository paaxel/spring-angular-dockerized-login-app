package it.palex.provaLogin.library.security;

import it.palex.provaLogin.library.security.TokenGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TokenGeneratorTest {

    @Test
    public void checkUUIDSize() {
        String generateUUID = TokenGenerator.generateUUID();
        final int sizeUUID = 32+4;

        Assertions.assertTrue(generateUUID.length()==sizeUUID);
    }

}