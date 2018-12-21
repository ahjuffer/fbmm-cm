/*
 * The MIT License
 *
 * Copyright 2018 André H. Juffer, Triacle Biocomputing
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

package org.bco.cm.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Specifies a new user with an application.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@JsonInclude(Include.NON_NULL)
public class UserSpecification {
    
    private String userId_;
    private String username_;
    private String password_;
    private String newPassword_;
    private String emailAddress_;
    private final Set<String> userRoles_;
    
    public UserSpecification()
    {
        userId_ = null;
        username_ = null;
        password_ = null;
        newPassword_ = null;
        emailAddress_ = null;
        userRoles_ = new HashSet<>();
    }
    
    public void setUserId(String userId)
    {
        userId_ = userId;
    }
    
    public String getUserId()
    {
        return userId_;
    }
    
    public void setUsername(String username)
    {
        username_ = username;
    }
    
    public String getUsername()
    {
        return username_;
    }
    
    public void setPassword(String password)
    {
        password_ = password;
    }
    
    public String getPassword()
    {
        return password_;
    }
    
    public void setNewPassword(String password)
    {
        newPassword_ = password;
    }
    
    public String getNewPassword()
    {
        return newPassword_;
    }
    
    public void setEmailAddress(String emailAddress)
    {
        emailAddress_ = emailAddress;
    }
    
    @JsonIgnore
    public String getEmailAddress()
    {
        return emailAddress_;                
    }
    
    /**
     * Adds new user role (name) to existing user roles.
     * @param role Role (role name).
     */
    public void addUserRole(String role)
    {
        userRoles_.add(role);
    }
    
    /**
     * Replaces existing user roles with new user roles.
     * @param userRoles New user roles.
     */
    public void setUserRoles(Collection<String> userRoles)
    {
        userRoles_.clear();
        userRoles_.addAll(userRoles);
    }
    
    /**
     * Returns all user roles.
     * @return User roles.
     */
    public Collection<String> getUserRoles()
    {
        return userRoles_;
    }
    
    @JsonIgnore
    public boolean isStudent()
    {
        return this.hasRole("student");
    }
    
    @JsonIgnore
    public boolean isTeacher()
    {
        return this.hasRole("teacher");
    }
    
    /**
     * Has user given role?
     * @param roleName Role name.
     * @return Result.
     */
    @JsonIgnore
    public boolean hasRole(String roleName)
    {
        return userRoles_.contains(roleName);
    }
    
}
