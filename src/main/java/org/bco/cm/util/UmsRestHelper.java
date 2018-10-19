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

package org.bco.cm.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Some helpers methods for dealing with UMS.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class UmsRestHelper {
    
    private UmsRestHelper()
    {        
    }
    
    /**
     * Extracts a detail error message from exception that occurred when 
     * interacting with UMS.
     * @param exception Exception.
     * @return Detail error message.
     */
    public static String getDetailMessage(RestClientResponseException exception)
    {
        try {
            String message = exception.getResponseBodyAsString();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(message);
            return jsonNode.get("detail").textValue();
        } catch (IOException ex) {                
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }        
    }
    
    /**
     * Prepares an encoded URI.
     * @param url URL.
     * @return URI.
     */
    public static URI makeEncodedUri(String url)
    {
        UriComponents uriComponents = 
            UriComponentsBuilder.fromUriString(url).build().encode();
        return uriComponents.toUri();
    }
    
    /**
     * Returns headers for interacting with UMS.
     * @return Headers.
     */
    public static HttpHeaders makeHeaders()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return headers;
    }

}
