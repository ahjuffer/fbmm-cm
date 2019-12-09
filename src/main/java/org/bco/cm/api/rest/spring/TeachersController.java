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

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.bco.cm.dto.TeacherDTO;
import org.bco.cm.util.TeacherId;
import org.bco.security.SecurityToken;
import org.bco.security.SecurityTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST interface for teachers.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@RestController
@RequestMapping(value="/teachers")
public class TeachersController {
    
    @Autowired
    private TeachersResource teachersResource_;
    
    /**
     * Registers new teacher.
     * @param spec New teacher specification. Must hold first name and surname.
     * @return Teacher information.
     */
    @PostMapping(
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TeacherDTO> register(@RequestBody TeacherDTO spec)
    {
        TeacherDTO teacher = teachersResource_.register(spec);
        return ResponseEntity.ok().body(teacher);
    }
    
    /**
     * Returns a single teacher.
     * @param request Request.
     * @param id Teacher identifier.
     * @return Teacher.
     */
    @GetMapping(
        path = "/{teacherId}",
        produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<TeacherDTO> getTeacher(HttpServletRequest request, 
                                                 @PathVariable("teacherId") String id)
    {
        SecurityToken securityToken = SecurityTokenUtil.extractFromRequest(request);
        TeacherId teacherId = new TeacherId(id);
        TeacherDTO teacher = teachersResource_.getTeacher(securityToken, teacherId);
        return ResponseEntity.ok().body(teacher);
    }
    
    /**
     * Returns all teachers.
     * @param request Request.
     * @param all If provided, all teachers are returned.
     * @return Teachers. May be empty.
     */
    @GetMapping( 
        produces = "application/json;charset=UTF-8" 
    )
    public ResponseEntity<List<TeacherDTO>> getTeachers(
        HttpServletRequest request,
        @RequestParam(name = "all",required = true) String all
    )
    {
        SecurityToken securityToken = SecurityTokenUtil.extractFromRequest(request);
        List<TeacherDTO> teachers = new ArrayList<>();
        if ( all != null ) {
            teachers = teachersResource_.getTeachers(securityToken);
        }
        return ResponseEntity.ok().body(teachers);
    }
    
}
