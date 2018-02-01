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
import org.bco.cm.application.query.ReadOnlyTeacherRepository;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.dto.CourseDTO;
import org.bco.cm.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
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
     * Starts new course.
     * @param id Identifier of responsible teacher.
     * @param spec New course specification. Must hold title, description, and 
     * objective.
     * @return New course.
     */
    @RequestMapping(value="/{id}/course")
    @PostMapping(consumes = "application/json", 
                 produces = "application/json;charset=UTF-8")
    @ResponseStatus(HttpStatus.CREATED)
    public CourseDTO startNewCourse(@PathVariable String id, 
                                    @RequestBody CourseDTO spec)
    {
        TeacherId teacherId = new TeacherId(id);
        CourseId courseId = courseFacade_.generateCourseId();
        teacherFacade_.startNewCourse(teacherId, courseId, spec);
        return courseFacade_.getCourse(courseId);
    }
    
    /**
     * Returns all teachers.
     * @return Teachers.
     */
    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<TeacherDTO> getAllTeachers()
    {
        return teacherFacade_.getAllTeachers();
    }
    
}
