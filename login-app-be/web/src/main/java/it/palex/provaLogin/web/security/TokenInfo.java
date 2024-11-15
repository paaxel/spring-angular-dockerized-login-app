package it.palex.provaLogin.web.security;

/**
 *
 * @author Alessandro Pagliaro
 *
 */
public class TokenInfo {
    private String tokenId;
    private String jwtToken;

    public TokenInfo(String tokenId, String jwtToken) {
        if(tokenId==null || jwtToken==null){
            throw new NullPointerException();
        }
        this.tokenId = tokenId;
        this.jwtToken = jwtToken;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
