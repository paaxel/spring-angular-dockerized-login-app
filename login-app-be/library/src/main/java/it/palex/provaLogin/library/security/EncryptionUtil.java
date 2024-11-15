package it.palex.provaLogin.library.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil {

    public static String sha512Crypt(String stringToHash) {
        return crypt(stringToHash, "SHA-512");
    }

    private static String crypt(String stringToHash, String digestAlg){
        String cryptedString= null;

        MessageDigest md=null;
        try {
            md = MessageDigest.getInstance(digestAlg);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("This JVM does not support Algorithm:"+digestAlg);
        }
        md.update(stringToHash.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i< bytes.length ;i++)
        {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        cryptedString = sb.toString();

        return cryptedString;
    }
}
