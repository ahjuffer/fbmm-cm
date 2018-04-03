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
    @PostMapping(consumes = "application/json;charset=UTF-8", 
                 produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherDTO register(@RequestBody TeacherDTO spec)
    {
        TeacherId teacherId = teacherFacade_.generateTeacherId();
        teacherFacade_.register(teacherId, spec);
        return teacherFacade_.getTeacher(teacherId);
    }
    
    /**
     * Adds module to existing course.
     * @param tId Teacher identifier.
     * @param cId Course identifier.
     * @param spec New module specification. Must include name, may include learning
     * path, assignment and/or quiz.
     * @return Update course.
     */
    @RequestMapping(value="/{teacherId}/course/{courseId}/module")
    @PutMapping(consumes = "application/json;charset=UTF-8", 
                produces = "application/json;charset=UTF-8")
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
     * Posts new course.
     * @param id Identifier of responsible teacher.
     * @param spec New course specification. Must hold only title and summary. 
     * No modules.
     * @return New course.
     * @see #addCourseModule(java.lang.String, java.lang.String, org.bco.cm.dto.ModuleDTO) 
     */
    @RequestMapping(value="/{teacherId}/course")
    @PostMapping(consumes = "application/json;charset=UTF-8", 
                 produces = "application/json;charset=UTF-8")
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
     * Returns a single teacher.
     * @param id Teacher identifier.
     * @return Teacher.
     */
    @GetMapping(produces = "application/json;charset=UTF-8")
    @RequestMapping(value="/{teacherId}")
    public TeacherDTO getTeacher(@PathVariable("teacherId") String id)
    {
        TeacherId teacherId = new TeacherId(id);
        return teacherFacade_.getTeacher(teacherId);
    }
    
    /**
     * Returns all teacher resources.
     * @return Teachers.
     */
    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<TeacherDTO> getAllTeachers()
    {
        return teacherFacade_.getAllTeachers();
    }
    
}
