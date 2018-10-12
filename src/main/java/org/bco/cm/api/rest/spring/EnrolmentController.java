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

import java.util.List;
import org.bco.cm.api.facade.EnrolmentFacade;
import org.bco.cm.application.query.EnrolmentSpecification;
import org.bco.cm.util.CourseId;
import org.bco.cm.util.EnrolmentNumber;
import org.bco.cm.util.StudentId;
import org.bco.cm.dto.EnrolmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST API implementation using Spring.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@RestController
@RequestMapping(value="/enrolments")
public class EnrolmentController {
    
    @Autowired
    private EnrolmentFacade enrolmentFacade_;
    
    /**
     * Queries for enrolments.
     * @param courseId Course identifier. 
     * @param studentId Student identifier.
     * @return Enrolments.
     */
    @GetMapping(
        produces = "application/json;charset=UTF-8"
    )
    public List<EnrolmentDTO> getEnrolments(
        @RequestParam(name = "courseId", required = false) String courseId,
        @RequestParam(name = "studentId", required= false) String studentId
    )
    {
        EnrolmentSpecification spec = new EnrolmentSpecification();
        if ( courseId != null ) {
            spec.setCourseId(courseId);
        }
        if ( studentId != null ) {
            spec.setStudentId(studentId);
        }
        return enrolmentFacade_.getSpecified(spec);
    }
    
    /**
     * Queries for an enrolment.
     * @param number Enrolment number.
     * @return Enrolment.
     */
    @GetMapping(
        path = "/{enrolmentNumber}",
        produces = "application/json;charset=UTF-8"        
    )
    public EnrolmentDTO getEnrolment(@PathVariable("enrolmentNumber") String number)
    {
        EnrolmentNumber enrolmentNumber = new EnrolmentNumber(number);
        return enrolmentFacade_.getEnrolment(enrolmentNumber);
    }
    
    /**
     * Registers student for a course.
     * @param cId Course identifier.
     * @param sId Student identifier.
     * @return Enrolment.
     */
    @PostMapping(
        produces = "application/json;charset=UTF-8" 
    )
    @ResponseStatus(HttpStatus.CREATED)
    public EnrolmentDTO register(@RequestParam("courseId") String cId,
                                 @RequestParam("studentId") String sId)
    {
        EnrolmentNumber eid = enrolmentFacade_.generateEnrolmentNumber();
        CourseId courseId = new CourseId(cId);
        StudentId studentId = new StudentId(sId);
        enrolmentFacade_.register(eid, courseId, studentId);
        return enrolmentFacade_.getEnrolment(eid);
    }
    
    /**
     * Cancels an enrolment.
     * @param number Enrolment number.
     */
    @DeleteMapping(
        path = "/{enrolmentNumber}"
    )
    public void cancel(@PathVariable("enrolmentNumber") String number)
    {
        EnrolmentNumber enrolmentNumber = new EnrolmentNumber(number);
        enrolmentFacade_.cancel(enrolmentNumber);
    }
}
