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
package org.bco.cm;

import java.net.URI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bco.cm.dto.StudentDTO;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author ajuffer
 */
public class TestRestTemplate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger("com.bco.cm");
        
        UriComponents uriComponents = 
            UriComponentsBuilder.fromUriString("http://localhost:8012/students")
                                .build()
                                .encode();
        URI uri = uriComponents.toUri();
        
        /*
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Content-Type", "application/json; charset=UTF-8");
        HttpEntity<?> requestEntity = new HttpEntity(requestHeaders);     
        */
        
        RestTemplate restTemplate = 
            new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        
        /*
        HttpEntity<String> response = 
            restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
        String responseHeader = response.getHeaders().getFirst("Content-Type");
        logger.info("Response header 'Content-Type':" + responseHeader);
        String body = response.getBody();        
        logger.info("Response body: " + body);
        */
        
        StudentDTO spec = new StudentDTO();
        spec.setFirstName("André");
        spec.setSurname("Juffer");
        spec.setStudentId("17364648");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);        
        MappingJacksonValue value = new MappingJacksonValue(spec);
        HttpEntity<MappingJacksonValue> entity = new HttpEntity<>(value, headers);
        
        HttpEntity<String> res 
            = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
        String responseHeader = res.getHeaders().getFirst("Content-Type");
        logger.info("Response header 'Content-Type':" + responseHeader);
        String json = res.getBody();
        logger.info("Response body: " + json);
        
        /*
        response =
            restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
        responseHeader = response.getHeaders().getFirst("Content-Type");
        logger.info("Response header 'Content-Type':" + responseHeader);
        body = response.getBody();        
        logger.info("Response body: " + body);
        
        HttpEntity<StudentDTO> ent = new HttpEntity<>(spec, headers);
        ResponseEntity<StudentDTO> re = 
            restTemplate.postForEntity(uri, spec, StudentDTO.class);
        */  
    }
    
}
