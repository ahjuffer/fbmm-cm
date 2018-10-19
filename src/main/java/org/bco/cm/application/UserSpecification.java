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

package org.bco.cm.application;

/**
 * Specifies a new user with an application.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class UserSpecification {
    
    private String userId_;
    private String username_;
    private String password_;
    private String emailAddress_;
    private String userRole_;
    
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
    
    public void setEmailAddress(String emailAddress)
    {
        emailAddress_ = emailAddress;
    }
    
    public String getEmailAddress()
    {
        return emailAddress_;                
    }
    
    public void setUserRole(String userRole)
    {
        userRole_ = userRole;
    }
    
    public String getUserRole()
    {
        return userRole_;
    }
    
}
