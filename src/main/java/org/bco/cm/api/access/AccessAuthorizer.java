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

package org.bco.cm.api.access;

import java.util.Set;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.bco.cm.security.SecurityTokenManager;
import org.bco.cm.security.SecurityTokenUtil;
import org.bco.cm.util.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;

/**
 * Authorizes access to resources. All authorizations requires an security token,
 * provided by the Authorization header request parameter.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Aspect
public class AccessAuthorizer {
    
    @Autowired
    private SecurityTokenManager securityTokenManager_;
    
    @Around("org.bco.cm.api.access.Access.all() && args(httpHeaders,..) && " + 
            "@annotation(org.bco.cm.security.Authorizable)")
    void authorizeAll(HttpHeaders httpHeaders)
    {
        Set<String> keys = httpHeaders.keySet();
        System.out.println("Number of headers: " + keys.size());
        System.out.println("All headers: " + httpHeaders);
        if ( !httpHeaders.containsKey(HttpHeaders.AUTHORIZATION)) {
            throw new UnauthorizedAccessException();
        }
        
        // Security token provided.
        String authorization = httpHeaders.getFirst(HttpHeaders.AUTHORIZATION);
        String securityToken = SecurityTokenUtil.extractFrom(authorization);
        if ( !securityTokenManager_.securityTokenExists(securityToken) ) {
            throw new UnauthorizedAccessException();
        }       
    }
    
    /**
     * All authorizable methods. User must be signed in already.
     * @param authorization Value of Authorization request header.
     */
    @Before(
        "org.bco.cm.api.access.Access.all() && args(authorization,..) && " + 
        "@annotation(org.bco.cm.security.Authorizable)"
    )
    void authorizeAll(String authorization) 
    {
        String securityToken = SecurityTokenUtil.extractFrom(authorization);
        
        // Security token?
        if ( !securityTokenManager_.securityTokenExists(securityToken) ) {
            throw new UnauthorizedAccessException();
        }       
    }
    
    /**
     * All authorizable teacher methods. User must be a teacher.
     * @param authorization Value of Authorization request header.
     */
    @Before(
        "Access.teacher() && args(authorization,..) && " +
        "@annotation(org.bco.cm.security.Authorizable)"
    )
    void authorizeTeacher(String authorization)
    {
        UserSpecification user = this.getUser(authorization);
        
        // Teacher? Student?
        if ( !user.isTeacher() || !user.isStudent()) {
            throw new UnauthorizedAccessException("Neither a teacher nor a student.");
        }
    }
    
    /**
     * All authorizable student methods with a student identifier. User
     * must be a student.
     * @param authorization Value of Authorization request header.
     * @param sId Student identifier.
     */
    @Before(
        "Access.student() && args(authorization,sId,..) && " +
        "@annotation(org.bco.cm.security.Authorizable)"
    )
    void authorizeStudent(String authorization, String sId)
    {
        UserSpecification user = this.getUser(authorization);
        
        // Student?
        if ( !user.isStudent() ) {
            throw new UnauthorizedAccessException("Not a student.");
        }
        
        // Student can only access/modify his/her own information.
        if ( !user.getUserId().equals(sId) ) {
            throw new UnauthorizedAccessException(
                "Illegal attempt to view/modify another student's information."
            );
        }            
    }
    
    /**
     * Authorizable getStudents(...) method. User must be a teacher.
     * @param authorization Value of Authorization request header.
     */
    @Before(
        "Access.getStudents() && args(authorization) && " +
        "@annotation(org.bco.cm.security.Authorizable)"
    )
    void authorizeGetStudents(String authorization)
    {
        UserSpecification user = this.getUser(authorization);
        
        // Teacher?
        if ( !user.isTeacher() ) {
            throw new UnauthorizedAccessException("Not a teacher.");
        }
    }
    
    /**
     * All authorizable course catalog methods with a teacher identifier. User 
     * must be teacher.
     * @param authorization Value of Authorization request header.
     * @param tId Teacher identifier.
     */
    @Before(
        "Access.courseCatalog() && args(authorization,tId,..) && " +
        "@annotation(org.bco.cm.security.Authorizable)"
    )
    void authorizeCourseCatalog(String authorization, String tId)
    {
        UserSpecification user = this.getUser(authorization);
        
        // Teacher?
        if ( !user.isTeacher() ) {
            throw new UnauthorizedAccessException("Not a teacher.");
        }
    }
    
    private UserSpecification getUser(String authorization)
    {
        String securityToken = SecurityTokenUtil.extractFrom(authorization);
        UserSpecification spec = securityTokenManager_.getUser(securityToken);
        if ( !spec.isStudent() && !spec.isTeacher() ) {
            throw new UnauthorizedAccessException("Not a legitimate user.");
        }
        return spec;
    }
    
}
