package it.palex.provaLogin.library;

import it.palex.provaLogin.library.rest.GenericResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class GenericResponseTests {

    @Test
    public void checkGenericResponseConstruction1() {
        final String version = "1.0.0";
        final String data = "DATA";
        final int code = 0;
        final String message = "message";

        GenericResponse<String> res = new GenericResponse<>(version, data, code, message);

        Assertions.assertTrue(res.getOperationIdentifier()!=null);
        Assertions.assertTrue(data.equals(res.getData()));
        Assertions.assertTrue(code==res.getCode());
        Assertions.assertTrue(message.equals(res.getMessage()));
    }

    @Test
    public void checkGenericResponseConstruction2() {
        final String data = "DATA";
        final int code = 0;
        final String message = "message";
        final int subcode = 0;

        GenericResponse<String> res = new GenericResponse<>(data, code, message, subcode);

        Assertions.assertTrue(res.getOperationIdentifier()!=null);
        Assertions.assertTrue(data.equals(res.getData()));
        Assertions.assertTrue(code==res.getCode());
        Assertions.assertTrue(subcode==res.getSubcode());
        Assertions.assertTrue(message.equals(res.getMessage()));
    }


    @Test
    public void checkGenericResponseConstruction3() {
        final String data = "DATA";
        final int code = 0;
        final String message = "message";

        GenericResponse<String> res = new GenericResponse<>(data, code, message);

        Assertions.assertTrue(res.getOperationIdentifier()!=null);
        Assertions.assertTrue(data.equals(res.getData()));
        Assertions.assertTrue(code==res.getCode());
        Assertions.assertTrue(message.equals(res.getMessage()));
    }

    @Test
    public void checkGenericResponseConstruction4() {
        GenericResponse<String> res = new GenericResponse<>();

        Assertions.assertTrue(res.getOperationIdentifier()!=null);
    }

    @Test
    public void checkGenericResponseSetterAndGetters() {
        final String version = "1.0.0";
        final String data = "DATA";
        final int code = 0;
        final String message = "message";
        final int subcode = 0;
        final String uuid = UUID.randomUUID().toString();

        GenericResponse<String> res = new GenericResponse<>();
        res.setMessage(message);
        res.setSubcode(subcode);
        res.setVersion(version);
        res.setData(data);
        res.setCode(code);
        res.setOperationIdentifier(uuid);

        Assertions.assertTrue(uuid.equals(res.getOperationIdentifier()));
        Assertions.assertTrue(data.equals(res.getData()));
        Assertions.assertTrue(code==res.getCode());
        Assertions.assertTrue(subcode==res.getSubcode());
        Assertions.assertTrue(message.equals(res.getMessage()));
        Assertions.assertTrue(version.equals(res.getVersion()));
    }

}
