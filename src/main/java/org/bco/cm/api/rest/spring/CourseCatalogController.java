/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer, Biocenter Oulu.
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
import javax.servlet.http.HttpServletRequest;
import org.bco.cm.api.facade.CourseCatalogFacade;
import org.bco.cm.application.query.CourseSpecification;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.security.Authorizable;
import org.bco.cm.util.CourseDescriptionId;
import org.bco.cm.util.TeacherId;
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
 * REST API implementation using Spring for managing course descriptions in the 
 * course catalog.
 * @author Andr&#233; Juffer, Biocenter Oulu
 * @see org.bco.cm.api.rest.spring.TeachersController for adding new course 
 * descriptions to the course catalog.
 */
@RestController
@RequestMapping(value="/course/catalog")
public class CourseCatalogController {
    
    @Autowired
    private CourseCatalogFacade courseCatalogFacade_;
    
    /**
     * Returns a single course description.
     * @param cId Course identifier.
     * @return Course description.
     */
    @GetMapping( 
        path = "/{courseId}",
        produces = "application/json;charset=UTF-8" 
    )
    public CourseDescriptionDTO getCourse(@PathVariable("courseId") String cId)
    {
        CourseDescriptionId courseId = new CourseDescriptionId(cId);
        return courseCatalogFacade_.getCourse(courseId);
    }
    
   /**
     * Queries for course description according to specification. If no argument 
     * is provided, all courses are returned.
     * @param request Request. Must be provided.
     * @param all If provided, include all courses.
     * @param tId If provided, include all teacher's courses.
     * @return Courses. May be empty.
     */
    @GetMapping( 
        produces = "application/json;charset=UTF-8" 
    )
    public List<CourseDescriptionDTO> getCourses(
        HttpServletRequest request,
        @RequestParam(name = "all", required = false) String all,
        @RequestParam(name = "teacherId", required = false) String tId
    )
    {   
        CourseSpecification spec = new CourseSpecification();
        if ( all != null ) {
            spec.selectAll();
        } else if ( tId != null ) {
            spec.setTeacherId(tId);
        } else {
            spec.selectAll();
        }
        
        return courseCatalogFacade_.getSpecified(spec);
    }
    
    /**
     * Posts new course description.
     * @param request Request. Must be provided.
     * @param tId Identifier of responsible teacher.
     * @param spec New course specification. Must hold at least title and summary,
     * and may hold modules.
     * @return New course.
     */
    @Authorizable
    @PostMapping(
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDescriptionDTO postNewCourse(
        HttpServletRequest request,
        @RequestParam("teacherId") String tId, 
        @RequestBody CourseDescriptionDTO spec
    )
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseDescriptionId courseId = courseCatalogFacade_.generate();
        courseCatalogFacade_.postNewCourse(courseId, teacherId, spec);
        return courseCatalogFacade_.getCourse(courseId);
    }
    
    /**
     * Updates course description in course catalog.
     * @param request HTTP Request. Must be provided.
     * @param tId Identifier teacher updating course description.
     * @param cId Course identifier.
     * @param spec Course update specification. Must hold title and summary, 
     * and may hold modules.
     * @return Updated course.
     */
    @Authorizable
    @PutMapping(
        path = "/{courseId}",
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    public CourseDescriptionDTO updateCourse(
        HttpServletRequest request,
        @RequestParam("teacherId") String tId,
        @PathVariable("courseId") String cId,
        @RequestBody CourseDescriptionDTO spec
    )
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseDescriptionId courseId = new CourseDescriptionId(cId);
        courseCatalogFacade_.updateCourse(courseId, teacherId, spec);
        return courseCatalogFacade_.getCourse(courseId);
    }
    
    /**
     * Adds new module to course description.
     * @param request HTTP Request. Must be provided.
     * @param tId Teacher identifier.
     * @param cId Course identifier.
     * @param spec New module specification. Must include name, may include quizzes,
     * assignments, etc.
     * @return Updated course.
     */
    @Authorizable
    @PostMapping(
        path ="/{courseId}/modules",
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDescriptionDTO addCourseModule(
        HttpServletRequest request,
        @RequestParam("teacherId") String tId,
        @PathVariable("courseId") String cId,
        @RequestBody ModuleDTO spec
    )
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseDescriptionId courseId = new CourseDescriptionId(cId);
        courseCatalogFacade_.addCourseModule(courseId, teacherId, spec);
        return courseCatalogFacade_.getCourse(courseId);
    }
    
    /**
     * Updates module in course description.
     * @param request HTTP Request. Must be provided.
     * @param tId Teacher identifier.
     * @param cId Course identifier.
     * @param mId Module identifier.
     * @param spec New module specification. Must include moduleId, name, may 
     * include learning path, assignment and/or quiz.
     * @return Updated course description.
     */
    @Authorizable
    @PutMapping(
        path ="/{courseId}/modules/{moduleId}",
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    public CourseDescriptionDTO updateCourseModule(
        HttpServletRequest request,
        @RequestParam("teacherId") String tId,
        @PathVariable("courseId") String cId,
        @PathVariable("moduleId") String mId,
        @RequestBody ModuleDTO spec
    )
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseDescriptionId courseId = new CourseDescriptionId(cId);
        int moduleId = Integer.valueOf(mId);
        courseCatalogFacade_.updateCourseModule(courseId, teacherId, moduleId, spec);
        return courseCatalogFacade_.getCourse(courseId);
    }
    
    /**
     * Removes a module from course description.
     * @param request HTTP Request. Must be provided.
     * @param tId Teacher identifier.
     * @param cId Course identifier.
     * @param mId Module identifier.
     * @return Updated course description.
     */
    @Authorizable
    @DeleteMapping(
        path ="/{courseId}/modules/{moduleId}",
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    public CourseDescriptionDTO deleteCourseModule(
        HttpServletRequest request,
        @RequestParam("teacherId") String tId,
        @PathVariable("courseId") String cId,
        @PathVariable("moduleId") String mId
    )
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseDescriptionId courseId = new CourseDescriptionId(cId);
        int moduleId = Integer.valueOf(mId);
        courseCatalogFacade_.deleteCourseModule(courseId, teacherId, moduleId);
        return courseCatalogFacade_.getCourse(courseId);        
    }
    
    /**
     * Removes course description from course catalog.
     * @param request HTTP Request. Must be provided.
     * @param tId Identifier teacher removing course.
     * @param cId Course identifier.
     */
    @Authorizable
    @DeleteMapping( path = "/{courseId}" )
    public void deleteCourse(
        HttpServletRequest request,
        @RequestParam("teacherId") String tId,
        @PathVariable("courseId") String cId
    )
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseDescriptionId courseId = new CourseDescriptionId(cId);
        courseCatalogFacade_.deleteCourse(courseId, teacherId);
    }
    
}
