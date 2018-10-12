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
import org.bco.cm.api.facade.StudentFacade;
import org.bco.cm.util.StudentId;
import org.bco.cm.dto.StudentDTO;
import org.bco.cm.util.CourseId;
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
 * REST interface for students.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@RestController
@RequestMapping(value="/students")
public class StudentsController {
    
    @Autowired
    private StudentFacade studentFacade_;
    
    /**
     * Registers new student.
     * @param spec New student specification. Must hold first name, surname,
     * student identifier, and email address.
     * @return New student.
     */
    @PostMapping(
        consumes = "application/json;charset=UTF-8", 
        produces = "application/json;charset=UTF-8"
    )
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO register(@RequestBody StudentDTO spec)
    {
        StudentId studentId = new StudentId(spec.getStudentId());
        studentFacade_.register(studentId, spec);
        return studentFacade_.getStudent(studentId);
    }
    
    /**
     * Returns student.
     * @param sId Student identifier.
     * @return Student.
     */
    @GetMapping(
        path = "/{studentId}",
        produces = "application/json;charset=UTF-8"
    )
    public StudentDTO getStudent(@PathVariable("studentId") String sId)
    {
        StudentId studentId = new StudentId(sId);
        return studentFacade_.getStudent(studentId);
    }
    
    /**
     * Returns all students.
     * @return Students.
     */
    @GetMapping(
        produces = "application/json;charset=UTF-8"
    )
    public List<StudentDTO> getStudents()
    {
        return studentFacade_.getAllStudents();
    }
    
    /**
     * Completes a task.
     * @param sId Student identifier.
     * @param task Task definition, either 'quiz' or 'assignment.
     * @param cId Course identifier.
     */
    @PutMapping(
        path = "/{studentId}/complete/{task}"
    )
    public void completeTask(@PathVariable("studentId") String sId,
                             @PathVariable("task") String task,
                             @RequestParam("courseId") String cId)
    {
        StudentId studentId = new StudentId(sId);
        CourseId courseId = new CourseId(cId);
        if ( task.equals("quiz") ) {
            studentFacade_.completeQuiz(studentId, courseId);
        } else if (task.equals("assignment") ) {
            studentFacade_.completeAssignment(studentId, courseId);
        } else {
            throw new IllegalArgumentException(task + ": No such task.");
        }
    }
}
