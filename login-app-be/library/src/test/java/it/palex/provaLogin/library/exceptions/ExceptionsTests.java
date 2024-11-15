package it.palex.provaLogin.library.exceptions;

import it.palex.provaLogin.library.exceptions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import org.junit.jupiter.api.Assertions;

@ExtendWith(MockitoExtension.class)
public class ExceptionsTests {


    @Test
    public void checkBadDataExceptionConstruction() {
        try {
            throw new BadDataException(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE);
        } catch (BadDataException e) {
            Assertions.assertTrue(e.getCode() == StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
            Assertions.assertTrue(e.getMessage().equals(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getMess()));
        }
    }

    @Test
    public void checkForbiddenExceptionConstruction() {
        try {
            throw new ForbiddenException(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE);
        } catch (ForbiddenException e) {
            Assertions.assertTrue(e.getCode() == StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
            Assertions.assertTrue(e.getMessage().equals(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getMess()));
        }
    }

    @Test
    public void checkInternalServerErrorExceptionConstruction() {
        try {
            throw new InternalServerErrorException(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE);
        } catch (InternalServerErrorException e) {
            Assertions.assertTrue(e.getCode() == StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
            Assertions.assertTrue(e.getMessage().equals(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getMess()));
        }
    }

    @Test
    public void checkUnauthorizedExceptionConstruction() {
        try {
            throw new UnauthorizedException(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE);
        } catch (UnauthorizedException e) {
            Assertions.assertTrue(e.getCode() == StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getCode());
            Assertions.assertTrue(e.getMessage().equals(StandardReturnCodesEnum.UNCLASSIFIED_RESULT_CODE.getMess()));
        }
    }


}
