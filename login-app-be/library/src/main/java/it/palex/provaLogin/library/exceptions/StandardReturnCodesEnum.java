package it.palex.provaLogin.library.exceptions;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
public enum StandardReturnCodesEnum implements StandardReturnCode{
    UNCLASSIFIED_RESULT_CODE(-1, "Unclassified Error");

    private int code;
    private String mess;

    StandardReturnCodesEnum(int code, String mess) {
        this.code = code;
        this.mess = mess;
    }

    public int getCode() {
        return this.code;
    }

    public String getMess() {
        return this.mess;
    }

}
