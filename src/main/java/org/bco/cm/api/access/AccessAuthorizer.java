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

import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.bco.cm.util.StudentId;
import org.bco.cm.util.UserSpecification;
import org.bco.security.SecurityToken;
import org.bco.security.SecurityTokenException;
import org.bco.security.SecurityTokenManager;
import org.bco.security.SecurityTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Authorizes access to resources. All authorizations requires an security token,
 * provided by the Authorization header request parameter.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Aspect
public class AccessAuthorizer {
    
    @Autowired
    private SecurityTokenManager<UserSpecification> securityTokenManager_;
    
    /**
     * All authorizable methods. User must be signed in already.
     * @param request HTTP request.
     */
    @Before("org.bco.cm.api.access.Access.all() && args(securityToken,..) && " + 
            "@annotation(org.bco.cm.security.Authorizable)")
    void authorizeAll(SecurityToken securityToken)
    {
        try {            
            if ( !securityTokenManager_.exists(securityToken) ) {
                throw new SecurityTokenException("Illegal security token.");
            }
        } catch (SecurityTokenException exception) {
            throw new UnauthorizedAccessException(exception.getMessage(), exception);
        }
    }
    
    /**
     * All authorizable teacher methods. User must be a teacher.
     * @param request HTTP Request.
     */
    @Before(
        "org.bco.cm.api.access.Access.teacher() && args(securityToken,..) && " +
        "@annotation(org.bco.cm.security.Authorizable)"
    )
    void authorizeTeacher(SecurityToken securityToken)
    {
        UserSpecification user = this.getUser(securityToken);
        
        // Teacher? Student?
        if ( !user.isTeacher() && !user.isStudent()) {
            throw new UnauthorizedAccessException("Neither a teacher nor a student.");
        }
    }
    
    /**
     * All authorizable student methods with a student identifier. User must be a 
     * student.
     * @param request HTTP Request.
     * @param sId Student identifier.
     */
    @Before(
        "org.bco.cm.api.access.Access.student() && args(securityToken,studentId,..) && " +
        "@annotation(org.bco.cm.security.Authorizable)"
    )
    void authorizeStudent(SecurityToken securityToken, StudentId studentId)
    {
        UserSpecification user = this.getUser(securityToken);
        
        // Student?
        if ( !user.isStudent() ) {
            throw new UnauthorizedAccessException("Not a student.");
        }
        
        // Student can only access/modify his/her own information.
        if ( !user.getUserId().equals(studentId.stringValue()) ) {
            throw new UnauthorizedAccessException(
                "Illegal attempt to view/modify another student's information."
            );
        }            
    }
    
    /**
     * Authorizable students method. User must be a teacher.
     * @param request HTTP Request.
     */
    @Before(
        "org.bco.cm.api.access.Access.getStudents() && args(securityToken) && " +
        "@annotation(org.bco.cm.security.Authorizable)"
    )
    void authorizeStudents(SecurityToken securityToken)
    {
        UserSpecification user = this.getUser(securityToken);
        
        // Teacher?
        if ( !user.isTeacher() ) {
            throw new UnauthorizedAccessException("Not a teacher.");
        }
    }
    
    /**
     * All authorizable course catalog methods with a teacher identifier. User 
     * must be teacher.
     * @param request HTTP Request.
     * @param tId Teacher identifier.
     */
    @Before(
        "org.bco.cm.api.access.Access.courseCatalog() && args(request,tId,..) && " +
        "@annotation(org.bco.cm.security.Authorizable)"
    )
    void authorizeCourseCatalog(HttpServletRequest request, String tId)
    {
        UserSpecification user = this.getUserFromRequest(request);
        
        // Teacher?
        if ( !user.isTeacher() ) {
            throw new UnauthorizedAccessException("Not a teacher.");
        }
    }
    
    private UserSpecification getUserFromAuthorization(HttpServletRequest request)
    {
        SecurityToken securityToken = 
            SecurityTokenUtil.extractFromAuthorizationHeader(request);
        return this.getUser(securityToken);
    }
    
    private UserSpecification getUserFromRequest(HttpServletRequest request)
    {
        SecurityToken securityToken = SecurityTokenUtil.extractFromRequest(request);
        return this.getUser(securityToken);        
    }
    
    private UserSpecification getUser(SecurityToken securityToken)
    {
        UserSpecification spec = securityTokenManager_.getUser(securityToken);
        if ( !spec.isStudent() && !spec.isTeacher() ) {
            throw new UnauthorizedAccessException("Not a legitimate user.");
        }
        return spec;
    }
    
}
