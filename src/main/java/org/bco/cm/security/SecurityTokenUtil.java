/*
 * The MIT License
 *
 * Copyright 2018 André H. Juffer, Biocenter Oulu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.bco.cm.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;

/**
 * Utilities for security tokens.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class SecurityTokenUtil {
    
    private static final String ALGORITHM = "HmacSHA256";
    private static final String BEARER = "bearer";
    private static final int BEARER_LENGTH = BEARER.length();
    private static final String KEY = "crapp_security";
    
    public static final String COOKIE_NAME = "crapp_security_token";
    
    /**
     * Extracts security token from Authorization header.
     * @param authorization Value of Authorization header.
     * @return Security token.
     */
    public static String extractFrom(String authorization)
    {
        if ( authorization == null ) {
            throw new NullPointerException("Missing authorization header.");
        }
        if ( authorization.isEmpty() ) {
            throw new IllegalArgumentException("Empty authorization header.");
        }
        String bearer = authorization.substring(0, BEARER_LENGTH).toLowerCase();
        if ( !bearer.equals(BEARER) ) {            
            throw new IllegalArgumentException("Illegal authorization header.");
        }
        return authorization.substring(BEARER_LENGTH).trim();
    }
    
    /**
     * Returns a security token.
     * @return Token.
     */
    static String generate()
    {
        try {
            SecretKey secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(secretKey);
            String s = UUID.randomUUID().toString();
            byte[] hash = mac.doFinal(s.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (InvalidKeyException | NoSuchAlgorithmException exception) {
            throw new IllegalStateException(
                "Cannot generate new security token. " + exception.getMessage(), exception
            );
        }
    }    
    
    /**
     * Create cookie.
     * @param securityToken
     * @return 
     */
    public static Cookie makeCookie(String securityToken)
    {
        Cookie cookie = new Cookie(COOKIE_NAME, securityToken);
        cookie.setHttpOnly(true);
        return cookie;
        
    }
    
}
