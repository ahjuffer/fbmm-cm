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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.bco.cm.util.UserSpecification;

/**
 * Maintains security tokens. After user sign in, a new security token is 
 * associated with that user. After a server restart, all previous tokens are lost.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class SecurityTokenManager {
    
    private final Map<String, UserSpecification> tokens_;
    
    public SecurityTokenManager()
    {
        tokens_ = new ConcurrentHashMap<>();
    }
    
    /**
     * Create new security token.
     * @param spec User specification.
     * @return Session identifier.
     */
    public String createNewSecurityToken(UserSpecification spec)
    {
        //String sessionId = SecurityToken.generate();
        String securityToken = "123";
        tokens_.put(securityToken, spec);
        return securityToken;
    }
    
    /**
     * Ends session. Usually called after signing out.
     * @param authorization Value of Authorization header. Must hold security token.
     */
    public void removeSecurityToken(String authorization)
    {
        String sessionId = SecurityTokenUtil.extractFrom(authorization);
        tokens_.remove(sessionId);
    }
    
    /**
     * Returns user.
     * @param securityToken Value of Authorization header.
     * @return User.
     */
    public UserSpecification getUser(String securityToken)
    {
        if ( !this.securityTokenExists(securityToken) ) {
            throw new NullPointerException(securityToken + ": No such user.");
        }
        return tokens_.get(securityToken);
    }
    
    /**
     * Does given security token exists?
     * @param securityToken Security token.
     * @return Result.
     */
    public boolean securityTokenExists(String securityToken)
    {
        return tokens_.containsKey(securityToken);
    }
    
}
