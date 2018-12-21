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

import java.util.Arrays;
import org.bco.cm.api.access.UnauthorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Handles all run time errors in REST API.
 * @author Andr&#233; H. Juffer, Triacle Biocomputing
 */
@ControllerAdvice( annotations = RestController.class )
@RequestMapping(
    produces = "application/json;charset=UTF-8"
)
public class RestApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    ResponseEntity<ErrorResponse> handleNullPointer(NullPointerException exception) 
    {
        String errorCode = String.valueOf(HttpStatus.NOT_FOUND.value());
        ErrorResponse errorResponse = 
            ErrorResponse.Builder.errorResponse()
                                 .withStatus(HttpStatus.NOT_FOUND)
                                 .withErrorCode(errorCode)
                                 .withMessage("Requested resource or value could not be found.")
                                 .withDetail(exception.getMessage())
                                 .build();
        System.out.println(Arrays.toString(exception.getStackTrace()));
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    ResponseEntity<ErrorResponse> handle(IllegalArgumentException exception) 
    {
        String errorCode = String.valueOf(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = 
            ErrorResponse.Builder.errorResponse()
                                 .withStatus(HttpStatus.BAD_REQUEST)
                                 .withErrorCode(errorCode)
                                 .withMessage("A required value is provided with an illegal value.")
                                 .withDetail(exception.getMessage())
                                 .build();
        System.out.println(Arrays.toString(exception.getStackTrace()));
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(UnauthorizedAccessException.class)
    @ResponseBody
    ResponseEntity<ErrorResponse> handle(UnauthorizedAccessException exception) 
    {
        String errorCode = String.valueOf(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse errorResponse = 
            ErrorResponse.Builder.errorResponse()
                                 .withStatus(HttpStatus.UNAUTHORIZED)
                                 .withErrorCode(errorCode)
                                 .withMessage("Unauthorized access.")
                                 .withDetail(exception.getMessage())
                                 .build();
         System.out.println(Arrays.toString(exception.getStackTrace()));
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    ResponseEntity<ErrorResponse> handle(IllegalStateException exception)
    {
        String errorCode = String.valueOf(HttpStatus.BAD_REQUEST);
        ErrorResponse errorResponse = 
            ErrorResponse.Builder.errorResponse()
                                 .withStatus(HttpStatus.BAD_REQUEST)
                                 .withErrorCode(errorCode)
                                 .withMessage("An illegal state or situation has occurred.")
                                 .withDetail(exception.getMessage())
                                 .build();
        System.out.println(Arrays.toString(exception.getStackTrace()));
        return ResponseEntity.badRequest().body(errorResponse);
    }
    
}
