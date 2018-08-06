/*
 * The MIT License
 *
 * Copyright 2018 Andr�� H. Juffer, Biocenter Oulu
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
import org.bco.cm.api.facade.CourseFacade;
import org.bco.cm.api.facade.TeacherFacade;
import org.bco.cm.application.query.CourseSpecification;
import org.bco.cm.domain.course.CourseDescriptionId;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API implementation using Spring.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@RestController
@RequestMapping(value="/courses")
public class CoursesController {

    @Autowired
    private CourseFacade courseFacade_;
    
    @Autowired 
    TeacherFacade teacherFacade_;
    
   /**
     * Queries for courses according to specification. If no argument
     * is provided, all courses are returned.
     * @param studentId Student identifier.
     * @param all If provided, include all courses.
     * @param teacherId If provided, include all teacher's courses. 
     * @param ongoing If provided, include ongoing courses.
     * @param active If provided, include all active courses.
     * @return Courses. May be empty.
     */
    @GetMapping( 
        produces = "application/json;charset=UTF-8" 
    )    
    public List<CourseDTO> getCourses(
        @RequestParam(name = "all", required = false ) String all,
        @RequestParam(name = "teacherId", required = false ) String teacherId,
        @RequestParam(name = "studentId", required = false ) String studentId,
        @RequestParam(name = "ongoing", required = false ) String ongoing,
        @RequestParam(name = "active", required = false ) String active
    )
    {
        CourseSpecification spec = new CourseSpecification();
        if ( all != null ) {
            spec.setAll(true);
        }
        if ( teacherId != null ) {
            spec.setTeacherId(teacherId);
        }
        if ( studentId != null ) {
            spec.setStudentId(studentId);
        }
        if ( ongoing != null ) {
            spec.setOngoing(true);
        }
        if ( active != null ) {
            spec.setActive(true);
        }
        
        // If no argument was provided, return all courses.
        if ( !spec.isSpecified() ) {
            spec.setAll(true);
        }
        
        return courseFacade_.getSpecified(spec);
    }

    /**
     * Returns a course.
     * @param cId Course identifier.
     * @return Course.
     */
    @GetMapping(
        path = "/{courseId}",
        produces = "application/json;charset=UTF-8" 
    )
    public CourseDTO getCourse(@PathVariable("courseId") String cId)
    {
        CourseId courseId = new CourseId(cId);
        return courseFacade_.getCourse(courseId);
    }
    
    /**
     * Activates a course.
     * @param cdId Course description identifier of course to be activated.
     * @param tId Identifier of teacher activating the course.
     * @param spec Activation specification. Must hold start and end date plus
     * the number of seats.
     * @return Activated course.
     */
    @PostMapping(
        consumes = "application/json;charset=UTF-8",
        produces = "application/json;charset=UTF-8"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDTO activate(@RequestParam("courseDescriptionId") String cdId,
                              @RequestParam("teacherId") String tId,
                              @RequestBody CourseDTO spec)
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseDescriptionId courseDescriptionId = new CourseDescriptionId(cdId);
        CourseId courseId = CourseId.generate();
        courseFacade_.activate(teacherId, courseDescriptionId, courseId, spec);
        return courseFacade_.getCourse(courseId);
    }
    
    @PutMapping(
        path = "/{courseId}",
        consumes = "application/json;charset=UTF-8",
        produces = "application/json;charset=UTF-8"    
    )
    public CourseDTO updateCourse(@PathVariable("courseId") String cId,
                                  @RequestParam("teacherId") String tId,
                                  @RequestBody CourseDTO spec)
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseId courseId = new CourseId(cId);
        courseFacade_.update(courseId, teacherId, spec);        
        return courseFacade_.getCourse(courseId);
    }
    
}   
