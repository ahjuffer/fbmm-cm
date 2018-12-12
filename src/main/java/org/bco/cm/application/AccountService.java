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

package org.bco.cm.application;

import org.bco.cm.util.UserSpecification;
import java.net.URI;
import org.bco.cm.util.UmsRestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Application service for signing in users.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class AccountService  {
    
    private String umsUrl_;
    
    @Autowired
    private RestTemplateBuilder restTemplateBuilder_;
    
    /**
     * Sets the URL of UMS.
     * @param url 
     */
    public void setUmsUrl(String url)
    {
        umsUrl_ = url;
    }    

    /**
     * Signs in user.
     * @param username Username.
     * @param password Password.
     * @return Signed in user. Holds userId and userRole.
     */
    public UserSpecification signin(String username, String password) 
    {
        try {
            UserSpecification spec = new UserSpecification();
            spec.setUsername(username);
            spec.setPassword(password);
            
            String url = umsUrl_ + "/signin";
            UriComponents uriComponents = 
                UriComponentsBuilder.fromUriString(url).build().encode();
            URI uri = uriComponents.toUri();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            MappingJacksonValue json = new MappingJacksonValue(spec);
            HttpEntity<MappingJacksonValue> entity = new HttpEntity<>(json, headers);            
            RestTemplate restTemplate = restTemplateBuilder_.build();
            ResponseEntity<UserSpecification> resource = 
                restTemplate.exchange(uri, HttpMethod.POST, entity, UserSpecification.class);
            return resource.getBody();
        } catch (RestClientResponseException exception) {
            String message = UmsRestHelper.getDetailMessage(exception);            
            throw new IllegalStateException(message, exception);
        }
    }

    /**
     * Signs out an user.
     * @param userId User identifier.
     */
    public void signout(String userId)
    {
        try {
            UserSpecification spec = new UserSpecification();
            spec.setUserId(userId);
            
            String url = umsUrl_ + "/signout";
            UriComponents uriComponents = 
                UriComponentsBuilder.fromUriString(url).build().encode();
            URI uri = uriComponents.toUri();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
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
