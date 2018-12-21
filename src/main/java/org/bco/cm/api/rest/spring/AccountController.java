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

package org.bco.cm.api.rest.spring;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bco.cm.security.SecurityToken;
import org.bco.cm.security.SecurityTokenManager;
import org.bco.cm.security.SecurityTokenUtil;
import org.bco.cm.util.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API implementation using Spring to signing in and out.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@RestController
@RequestMapping(value="/accounts")
public class AccountController {
    
    @Autowired
    private SecurityTokenManager securityTokenManager_;
    
    @Autowired
    private AccountResource accountResource_;
    
    /**
     * Signs in an user.
     * @param spec User specification. Must hold username and password.
     * @return User with userRole(s).
     */
    @PostMapping(
        path = "/signin",
        consumes = "application/json;charset=UTF-8",
        produces = "application/json;charset=UTF-8"
    )
    ResponseEntity<UserSpecification> signin(@RequestBody UserSpecification spec,
                                             HttpServletResponse response)
    {
        UserSpecification specification = 
            accountResource_.signin(spec.getUsername(), spec.getPassword());
        this.addSecurity(response, specification);
        return ResponseEntity.ok().body(specification);
    }
    
    /**
     * Signs out an user.
     * @param request HTTP request.
     * @param response HTTP response.
     * @return Success message.
     */
    @PostMapping(
        path = "/signout",
        produces = "application/json;charset=UTF-8"
    )
    ResponseEntity<Message> signout(HttpServletRequest request, 
                                   HttpServletResponse response)
    {
        SecurityToken securityToken = SecurityTokenUtil.extractFromRequest(request);
        UserSpecification spec = securityTokenManager_.getUser(securityToken);
        String message = accountResource_.signout(securityToken, spec.getUserId());
        this.removeSecurity(response, securityToken);
        return ResponseEntity.ok().body(new Message(message));
    }
    
    private void addSecurity(HttpServletResponse response, 
                             UserSpecification specification)
    {
        SecurityToken securityToken = securityTokenManager_.createNew(specification);
        Cookie cookie = SecurityTokenUtil.makeCookie(securityToken);
        response.addCookie(cookie);
    }
    
    private void removeSecurity(HttpServletResponse response, SecurityToken securityToken)
    {
        securityTokenManager_.removeSecurityToken(securityToken); 
        Cookie cookie = SecurityTokenUtil.removeCookie(securityToken);
        response.addCookie(cookie);
    }
}
