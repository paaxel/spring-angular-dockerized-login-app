package it.palex.provaLogin.library.security;

import java.util.UUID;

public class TokenGenerator {

    public static String generateUUID(){
        return UUID.randomUUID()+"";
    }

}
