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
import org.bco.cm.api.facade.CourseFacade;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API implementation using Spring.
 * @author Andr&#233; Juffer, Biocenter Oulu
 * @see org.bco.cm.api.rest.spring.TeachersController for adding new courses to
 * the course catalog.
 */
@RestController
@RequestMapping(value="/courses")
public class CoursesController  {
    
    @Autowired
    private CourseFacade courseFacade_;
    
    /**
     * Returns a single course description.
     * @param id Course identifier.
     * @return Course description.
     */
    @RequestMapping( value = "/{courseId}" )
    @GetMapping( produces = "application/json;charset=UTF-8" )
    public CourseDescriptionDTO getCourse(@PathVariable("courseId") String id)
    {
        CourseId courseId = new CourseId(id);
        return courseFacade_.getCourse(courseId);
    }
    
   /**
     * Queries for all courses.
     * @return Courses. May be empty.
     */
    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<CourseDescriptionDTO> getAllCourses() 
    {
        return courseFacade_.getAllCourses();
    }
    
     /**
     * Returns all courses the given teacher is responsible for.
     * @param id Teacher identifier.
     * @return Courses. May be empty.
     */     
    @GetMapping(
        produces = "application/json;charset=UTF-8", 
        params = "teacherId"
    )
    public List<CourseDescriptionDTO> getTeachersCourses(@RequestParam("teacherId") String id)
    {
        TeacherId teacherId = new TeacherId(id);
        return courseFacade_.getTeachersCourses(teacherId);
    }
    
}
