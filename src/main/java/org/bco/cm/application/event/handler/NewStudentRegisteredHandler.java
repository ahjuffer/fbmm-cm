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

package org.bco.cm.application.event.handler;

import com.tribc.ddd.domain.event.EventHandler;
import java.net.URI;
import java.util.Collection;
import java.util.HashSet;
import org.bco.cm.util.UserSpecification;
import org.bco.cm.domain.student.Student;
import org.bco.cm.domain.student.event.NewStudentRegistered;
import org.bco.cm.util.UmsRestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

/**
 * Creates new account for student. Email address serves as username.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class NewStudentRegisteredHandler 
    extends EventHandler<NewStudentRegistered>
{
    private String appId_;
    private String umsUrl_;
    private final Collection<String> userRoles_;
    
    @Autowired
    private RestTemplateBuilder restTemplateBuilder_;
    
    public NewStudentRegisteredHandler()
    {
        super();
        appId_ = null;
        umsUrl_ = null;
        userRoles_ = new HashSet<>();
    }
    
     /**
     * Sets the unique identifier for this application.
     * @param appId 
     */
    public void setApplicationId(String appId)
    {
        appId_ = appId;
    }
    
    /**
     * Sets the URL of UMS.
     * @param url 
     */
    public void setUmsUrl(String url)
    {
        umsUrl_ = url;
    }
    
    /**
     * Sets the student role.
     * @param studentRole Student role value.
     */
    public void setStudentRole(String studentRole)
    {
        userRoles_.clear();
        userRoles_.add(appId_);
    }
    
    @Override
    public void handle(NewStudentRegistered event) 
    {        
        try {
            Student student = event.getStudent();
            
            // New user.
            UserSpecification spec = new UserSpecification();
            spec.setEmailAddress(student.getEmailAddress().stringValue());
            spec.setUsername(student.getEmailAddress().stringValue());
            spec.setUserId(student.getStudentId().stringValue());
            spec.setUserRoles(userRoles_);
            spec.setPassword(appId_);
                        
            String url = umsUrl_ + "?appId=" + appId_;            
            URI uri = UmsRestHelper.makeEncodedUri(url);
            
            HttpHeaders headers = UmsRestHelper.makeHeaders();
            MappingJacksonValue json = new MappingJacksonValue(spec);
            HttpEntity<MappingJacksonValue> entity = new HttpEntity<>(json, headers);            
            RestTemplate restTemplate = restTemplateBuilder_.build();
            restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        } catch (RestClientResponseException exception) {
            String message = UmsRestHelper.getDetailMessage(exception);
            throw new IllegalStateException(message, exception);
        }
    }

}
