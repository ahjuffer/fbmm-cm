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

package org.bco.cm.api.rest.spring;

import org.springframework.http.HttpStatus;

/**
 *
 * @author Andr&#233; H. Juffer, Triacle Biocomputing
 * @see <a href="https://www.javadevjournal.com/spring/exception-handling-for-rest-with-spring/">Error Handling for REST with Spring</a>
 */
public class ErrorResponse {

    private HttpStatus status_;
    private String errorCode_;
    private String message_;
    private String detail_;
    
    public HttpStatus getStatus()
    {
        return status_;
    }
    
    public String getErrorCode()
    {
        return errorCode_;
    }
    
    public String getMessage()
    {
        return message_;
    }
    
    public String getDetail()
    {
        return detail_;
    }
    
    public static final class Builder {
        
        private HttpStatus status_;
        private String errorCode_;
        private String message_;
        private String detail_;    
        
        private Builder() {
        }

        public static Builder errorResponse() {
            return new Builder();
        }

        public Builder withStatus(HttpStatus status) {
            this.status_ = status;
            return this;
        }

        public Builder withErrorCode(String errorCode) {
            this.errorCode_ = errorCode;
            return this;
        }

        public Builder withMessage(String message) {
            this.message_ = message;
            return this;
        }

        public Builder withDetail(String detail) {
            this.detail_ = detail;
            return this;
        }
        
        public ErrorResponse build() 
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.status_ = status_;
            errorResponse.errorCode_ = errorCode_;
            errorResponse.detail_ = detail_;
            errorResponse.message_ = message_;
            return errorResponse;
        }    
    }

}
