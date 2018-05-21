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
import org.bco.cm.api.facade.CourseFacade;
import org.bco.cm.api.facade.TeacherFacade;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private CourseFacade courseFacade_;
    
    @Autowired
    private TeacherFacade teacherFacade_;
    
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
    public TeacherDTO register(@RequestBody TeacherDTO spec)
    {
        TeacherId teacherId = teacherFacade_.generateTeacherId();
        teacherFacade_.register(teacherId, spec);
        return teacherFacade_.getTeacher(teacherId);
    }
    
    /**
     * Updates course description in course catalog.
     * @param tId Teacher identifier.
     * @param cId Course identifier.
     * @param spec Course update specification. Must hold title and summary, 
     * and may hold modules.
     * @return Updated course.
     */
    @PutMapping(
        path = "/{teacherId}/courses/{courseId}",
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    public CourseDescriptionDTO updateCourse(@PathVariable("teacherId") String tId,
                                             @PathVariable("courseId") String cId,
                                             @RequestBody CourseDescriptionDTO spec)
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseId courseId = new CourseId(cId);
        teacherFacade_.updateCourse(teacherId, courseId, spec);
        return courseFacade_.getCourse(courseId);
    }
    
    /**
     * Removes course description from course catalog.
     * @param tId Teacher identifier.
     * @param cId Course identifier.
     */
    @DeleteMapping( path = "/{teacherId}/courses/{courseId}" )
    public void deleteCourse(@PathVariable("teacherId") String tId,
                             @PathVariable("courseId") String cId)
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseId courseId = new CourseId(cId);
        teacherFacade_.deleteCourse(teacherId, courseId);
    }
    
    /**
     * Posts new course description.
     * @param id Identifier of responsible teacher.
     * @param spec New course specification. Must hold at least title and summary,
     * and may hold modules.
     * @return New course.
     */
    @PostMapping(
        path = "/{teacherId}/courses",
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDescriptionDTO postNewCourse(@PathVariable("teacherId") String id, 
                                              @RequestBody CourseDescriptionDTO spec)
    {
        TeacherId teacherId = new TeacherId(id);
        CourseId courseId = courseFacade_.generateCourseId();
        teacherFacade_.postNewCourse(teacherId, courseId, spec);
        return courseFacade_.getCourse(courseId);
    }
    
    /**
     * Adds new module to course description.
     * @param tId Teacher identifier.
     * @param cId Course identifier.
     * @param spec New module specification. Must include name, may include learning
     * path, assignment and/or quiz.
     * @return Update course.
     */
    @PostMapping(
        path ="/{teacherId}/courses/{courseId}/modules",
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDescriptionDTO addCourseModule(@PathVariable("teacherId") String tId,
                                                @PathVariable("courseId") String cId,
                                                @RequestBody ModuleDTO spec)
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseId courseId = new CourseId(cId);
        teacherFacade_.addCourseModule(teacherId, courseId, spec);
        return courseFacade_.getCourse(courseId);
    }
    
    /**
     * Updates module in course description.
     * @param tId Teacher identifier.
     * @param cId Course identifier.
     * @param mId Module identifier.
     * @param spec New module specification. Must include moduleId, name, may 
     * include learning path, assignment and/or quiz.
     * @return Updated course description.
     */
    @PutMapping(
        path ="/{teacherId}/courses/{courseId}/modules/{moduleId}",
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    public CourseDescriptionDTO updateCourseModule(@PathVariable("teacherId") String tId,
                                                   @PathVariable("courseId") String cId,
                                                   @PathVariable("moduleId") String mId,
                                                   @RequestBody ModuleDTO spec)
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseId courseId = new CourseId(cId);
        int moduleId = Integer.valueOf(mId);
        teacherFacade_.updateCourseModule(teacherId, courseId, moduleId, spec);
        return courseFacade_.getCourse(courseId);
    }
    
    /**
     * Removes a module from course description.
     * @param tId Teacher identifier.
     * @param cId Course identifier.
     * @param mId Module identifier.
     * @return Updated course description.
     */
    @DeleteMapping(
        path ="/{teacherId}/courses/{courseId}/modules/{moduleId}",
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    public CourseDescriptionDTO deleteCourseModule(@PathVariable("teacherId") String tId,
                                                   @PathVariable("courseId") String cId,
                                                   @PathVariable("moduleId") String mId)
    {
        TeacherId teacherId = new TeacherId(tId);
        CourseId courseId = new CourseId(cId);
        int moduleId = Integer.valueOf(mId);
        teacherFacade_.deleteCourseModule(teacherId, courseId, moduleId);
        return courseFacade_.getCourse(courseId);        
    }
    
    /**
     * Returns a single teacher.
     * @param id Teacher identifier.
     * @return Teacher.
     */
    @GetMapping(
        path = "/{teacherId}",
        produces = "application/json;charset=UTF-8"
    )
    public TeacherDTO getTeacher(@PathVariable("teacherId") String id)
    {
        TeacherId teacherId = new TeacherId(id);
        return teacherFacade_.getTeacher(teacherId);
    }
    
    /**
     * Returns all teacher resources.
     * @return Teachers.
     */
    @GetMapping( produces = "application/json;charset=UTF-8" )
    public List<TeacherDTO> getAllTeachers()
    {
        return teacherFacade_.getAllTeachers();
    }
    
    /**
     * Activates course.
     */
    public void activateCourse()
    {
        
    }
    
}
