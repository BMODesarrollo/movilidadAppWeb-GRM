/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.utils;

import java.security.SecureRandom;

/**
 *
 * @author cesar
 */
public class TokenGeneratorUtil {

    protected static SecureRandom random = new SecureRandom();
    public static final String CHARACTERS = "0123456789";
    public static final int SECURE_TOKEN_LENGTH = 6;
    private static final char[] symbols = CHARACTERS.toCharArray();
    private static final char[] buf = new char[SECURE_TOKEN_LENGTH];

    public static String nextToken() {
        for (int idx = 0; idx < buf.length; ++idx) {
            buf[idx] = symbols[random.nextInt(symbols.length)];
        }
        return new String(buf);
    }

}


