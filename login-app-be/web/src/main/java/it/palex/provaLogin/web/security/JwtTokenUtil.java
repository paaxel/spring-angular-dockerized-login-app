package it.palex.provaLogin.web.security;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.palex.provaLogin.dbaccess.entities.UsersAuthDetails;
import it.palex.provaLogin.library.security.EncryptionUtil;
import it.palex.provaLogin.library.security.TokenGenerator;
import it.palex.provaLogin.library.utils.DateUtility;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 *
 * @author Alessandro Pagliaro
 *
 */
@Component
public class JwtTokenUtil implements Serializable {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final long serialVersionUID = 1L;
    public static final String CUSTOM_CLAIM_KEY_CLIENT_ID = "userId";
    public static final String CUSTOM_CLAIM_KEY_PERMISSION_GROUP = "permissionGroup";
    public static final String CUSTOM_CLAIM_KEY_TOKEN_ID = "tokenId";

    @Value("${security.jwt.secret}")
    private String secretJWT;
    private String secret = "";

    @PostConstruct
    private void prepareComponent(){
        this.secret = EncryptionUtil.sha512Crypt(this.secretJWT);
    }

    public Integer getUserIDFromToken(String token) {
        if (token == null) {
            return null;
        }
        final Integer id;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            try {
                id = (int) Double.parseDouble(claims.get(CUSTOM_CLAIM_KEY_CLIENT_ID)+"");
                return id;
            } catch (ClassCastException e) {
                logger.error("ID not valid in token received");
                return null;
            } catch (NumberFormatException e) {
                logger.error("ID not valid in token received");
                return null;
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            logger.warn("Token expired", e);
            return null;
        } catch (Exception e) {
            logger.error("Error during getIDClienteFromToken", e);
            return null;
        }
    }

    public String getUsernameFromToken(String token) {
        if (token == null) {
            return null;
        }
        String username = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            username = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error during getUsernameFromToken", e);
        }
        return username;
    }

    public Date getCreatedDateFromToken(String token) {
        if (token == null) {
            return null;
        }
        Date created = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            created = claims.getIssuedAt();
        } catch (Exception e) {
            logger.error("Error during getCreatedDateFromToken", e);
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        if (token == null) {
            return null;
        }
        Date expiration = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            expiration = claims.getExpiration();
        } catch (Exception e) {
            logger.error("Error during getExpirationDateFromToken", e);
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        if (token == null) {
            return null;
        }
        final Claims claims = Jwts.parser().setSigningKey(this.secret).build().parseClaimsJws(token).getBody();

        return claims;
    }

    private Boolean isTokenExpired(String token) {
        if (token == null) {
            return true;
        }
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(DateUtility.getCurrentDateInUTC());
    }


    public TokenInfo generateToken(UsersAuthDetails userDetails, Date expirationDate) {
        String tokenId = TokenGenerator.generateUUID();

        return this.generateToken(userDetails, expirationDate, tokenId);
    }
    public TokenInfo generateToken(UsersAuthDetails userDetails, Date expirationDate, String tokenId) {
        if (userDetails == null || expirationDate == null || tokenId==null) {
            throw new NullPointerException();
        }
        Map<String, Object> claims = new HashMap<>();

        String permissionGroupName= "";
        if(userDetails.getPermissionGroup()!=null) {
            permissionGroupName = userDetails.getPermissionGroup().getName();
        }

        final Date createdDate = DateUtility.getCurrentDateInUTC();
        final String subject = userDetails.getUsername()+"";
        claims.put(CUSTOM_CLAIM_KEY_PERMISSION_GROUP, permissionGroupName);
        claims.put(CUSTOM_CLAIM_KEY_CLIENT_ID, userDetails.getId()+"");
        claims.put(CUSTOM_CLAIM_KEY_TOKEN_ID, tokenId);

        String jwtTokenStr = doGenerateToken(claims, createdDate, expirationDate, subject);

        TokenInfo tokenInfo = new TokenInfo(tokenId, jwtTokenStr);

        return tokenInfo;
    }

    private String doGenerateToken(Map<String, Object> claims, Date createdDate,
                                   Date expirationDate, String subject) {
        logger.debug("doGenerateToken " + createdDate);

        /*
         * SignatureAlgorithm.HS512 ---> HS512("HS512", "HMAC using SHA-512", "HMAC",
         * "HmacSHA512", true) SignatureAlgorithm.RS512 ---> RS512("RS512",
         * "RSASSA-PKCS-v1_5 using SHA-512", "RSA", "SHA512withRSA", true)
         */
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        if(userDetails==null) {
            return false;
        }
        UsersAuthDetails user = (UsersAuthDetails) userDetails;
        final Integer id = this.getUserIDFromToken(token);

        if (id == null) {
            return false;
        }
        final Date created = getCreatedDateFromToken(token);
        // final Date expiration = getExpirationDateFromToken(token);
        return (id.equals(user.getId()) && !isTokenExpired(token) && !isCreatedBeforeLastPasswordReset(created, user));
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, UsersAuthDetails user) {
        Date lastPasswordChange = user.getLastPasswordChangeDate();
        if (lastPasswordChange != null) {
            return created.before(lastPasswordChange);
        }
        return false;
    }

    public String getTokenIdClaim(String token) {
        if (token == null) {
            return null;
        }
        final String id;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims == null) {
                return null;
            }
            try {
                id = claims.get(CUSTOM_CLAIM_KEY_TOKEN_ID).toString();
                return id;
            } catch (ClassCastException e) {
                logger.warn("ID not valid in token received");
                return null;
            }
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            logger.warn("Token expired", e);
            return null;
        } catch (Exception e) {
            logger.error("Error during getTokenIdFromToken", e);
            return null;
        }
    }
}
