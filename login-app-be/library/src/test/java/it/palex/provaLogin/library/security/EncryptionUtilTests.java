package it.palex.provaLogin.library.security;

import it.palex.provaLogin.library.security.EncryptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EncryptionUtilTests {

    @Test
    public void checkUUIDSize() {
        String toHash = "password";
        String hashed = "b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb" +
                        "980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86";

        String sha512 = EncryptionUtil.sha512Crypt(toHash);

        Assertions.assertTrue(sha512.equals(hashed));
    }

}
