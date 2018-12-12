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
import org.bco.cm.util.CourseDescriptionId;
import org.bco.cm.util.CourseId;
import org.bco.cm.util.TeacherId;
import org.bco.cm.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
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
 * REST API implementation using Spring for managing courses in the course registry.
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
     * is provided, all courses with the end date later than today's are returned.
     * @param studentId Student identifier. If provided, include all courses that 
     * the student has enrolled in.
     * @param all If provided, include courses with end dates later than todays date.
     * @param past If provided, include -all- courses, where 'all' must be provided 
     * as well.
     * @param teacherId Teacher identifier. If provided, include all teacher's courses. 
     * @param ongoing If provided, include ongoing courses.
     * @param active If provided, include courses that are active now.
     * @param courseDescriptionId Course description identifier. If provided, all
     * activated courses associated with course descriptions are returned. Past 
     * courses are excluded.
     * @return Courses. May be empty. Will be empty if no arguments were provided.
     */
    @GetMapping( 
        produces = "application/json;charset=UTF-8" 
    )    
    public List<CourseDTO> getCourses(
        @RequestParam(name = "all", required = false ) String all,
        @RequestParam(name = "past", required = false ) String past,
        @RequestParam(name = "teacherId", required = false ) String teacherId,
        @RequestParam(name = "studentId", required = false ) String studentId,
        @RequestParam(name = "ongoing", required = false ) String ongoing,
        @RequestParam(name = "active", required = false ) String active,
        @RequestParam(name = "courseDescriptionId", required = false) String courseDescriptionId
    )
    {
        CourseSpecification spec = new CourseSpecification();
        if ( all != null ) {
            spec.selectAll();
            if (past != null ) {
                spec.selectAll(true);
            }
        }
        if ( teacherId != null ) {
            spec.setTeacherId(teacherId);
        }
        if ( studentId != null ) {
            spec.setStudentId(studentId);
        }
        if ( courseDescriptionId != null ) {
            spec.setCourseDescriptionId(courseDescriptionId);
        }
        if ( ongoing != null ) {
            spec.selectOngoing();
        }
        if ( active != null ) {
            spec.selectActive();
        }
        if (all == null && 
            teacherId == null && 
            studentId == null &&
            ongoing == null &&
            active == null && 
            courseDescriptionId == null ) {
            spec.selectAll();
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
    
    /**
     * Starts a course.
     * @param cId Course identifier.
     * @param tId Identifier of teacher starting course.
     * @return Started course.
     */
    @PutMapping(
        path = "/{courseId}/start",
        produces = "application/json;charset=UTF-8"    
    )
    public CourseDTO startCourse(@PathVariable("courseId") String cId,
                                 @RequestParam("teacherId") String tId)
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseId courseId = new CourseId(cId);
        courseFacade_.start(courseId, teacherId);
        return courseFacade_.getCourse(courseId);
    }
    
    /**
     * Stops a course. 
     * @param cId Course identifier.
     * @param tId Identifier of teacher starting course.
     * @return Stopped course.
     */
    @PutMapping(
        path = "/{courseId}/stop",
        produces = "application/json;charset=UTF-8"  
    )
    public CourseDTO stopCourse(@PathVariable("courseId") String cId,
                                 @RequestParam("teacherId") String tId)
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseId courseId = new CourseId(cId);
        courseFacade_.stop(courseId, teacherId);
        return courseFacade_.getCourse(courseId);        
    }
    
    /**
     * Updates a course. Updates start and end date plus the number of seats.
     * @param cId Course identifier.
     * @param tId Identifier of teacher updating course.
     * @param spec Update specification.
     * @return 
     */
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
    
    /**
     * Deletes course.
     * @param cId Course identifier.
     * @param tId Identifier of teacher updating course.
     */
    @DeleteMapping(
        path = "/{courseId}"        
    )
    public void deleteCourse(@PathVariable("courseId") String cId,
                             @RequestParam("teacherId") String tId)
    {
        System.out.println("Deleting course....");
        
        TeacherId teacherId = new TeacherId(tId);
        CourseId courseId = new CourseId(cId);
        courseFacade_.remove(courseId, teacherId);
    }
    
}   
