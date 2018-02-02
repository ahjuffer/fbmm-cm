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

package org.bco.cm.api.facade;

import com.tribc.cqrs.domain.command.CommandBus;
import java.util.List;
import org.bco.cm.application.command.AddNewStudent;
import org.bco.cm.application.command.EnrolStudent;
import org.bco.cm.application.query.ReadOnlyStudentRepository;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.EnrolmentNumber;
import org.bco.cm.domain.course.StudentId;
import org.bco.cm.domain.course.StudentRepository;
import org.bco.cm.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Simple interface for students.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class StudentFacade {
    
    @Autowired
    private StudentRepository studentRepository_;
    
    @Autowired
    private ReadOnlyStudentRepository readOnlyStudentRepository_;
    
    @Autowired
    private CommandBus commandBus_;
    
    public StudentId generateStudentId()
    {
        return studentRepository_.generateId();
    }
    
    /**
     * Adds new student to the student repository.
     * @param studentId Student identifier.
     * @param spec New student specification.
     */
    public void register(StudentId studentId, StudentDTO spec)
    {
        AddNewStudent command = new AddNewStudent(studentId, spec);
        commandBus_.handle(command);
    }
    
    /**
     * Returns student with given identifier.
     * @param studentId Identifier.
     * @return Student.
     */
    public StudentDTO getStudent(StudentId studentId)
    {
        return readOnlyStudentRepository_.getStudent(studentId);
    }
    
    /**
     * Returns all students.
     * @return Students. May be empty.
     */
    public List<StudentDTO> getAllStudents()
    {
        return readOnlyStudentRepository_.getAllStudents();
    }
    
    /**
     * Enrols student in course.
     * @param eid Enrolment number.
     * @param studentId Student identifier.
     * @param courseId Course identifier.
     */
    public void enrolInCourse(EnrolmentNumber eid, StudentId studentId, CourseId courseId)
    {
        EnrolStudent command = new EnrolStudent(eid, studentId, courseId);
        commandBus_.handle(command);
    }

}
