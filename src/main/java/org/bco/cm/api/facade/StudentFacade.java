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
import org.bco.cm.application.command.EnrolStudent;
import org.bco.cm.application.command.RegisterNewStudent;
import org.bco.cm.application.query.ReadOnlyStudentRegistry;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.enrolment.EnrolmentNumber;
import org.bco.cm.domain.student.StudentId;
import org.bco.cm.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Simple interface for students.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Transactional( rollbackFor = { Throwable.class } )
public class StudentFacade {
    
    @Autowired
    private ReadOnlyStudentRegistry readOnlyStudentRegistry_;
    
    @Autowired
    private CommandBus commandBus_;
    
    /**
     * Returns new student identifier.
     * @return Student identifier.
     */
    @Transactional( readOnly=true )
    public StudentId generateStudentId()
    {
        return StudentId.generate();
    }
    
    /**
     * Adds new student to the student repository.
     * @param studentId Student identifier.
     * @param spec New student specification. Should include names.
     */
    public void register(StudentId studentId, StudentDTO spec)
    {
        RegisterNewStudent command = new RegisterNewStudent(studentId, spec);
        commandBus_.handle(command);
    }
    
    /**
     * Returns student with given identifier.
     * @param studentId Identifier.
     * @return Student.
     */
    @Transactional( readOnly=true )
    public StudentDTO getStudent(StudentId studentId)
    {
        return readOnlyStudentRegistry_.getOne(studentId);
    }
    
    /**
     * Returns all students.
     * @return Students. May be empty.
     */
    @Transactional( readOnly=true )
    public List<StudentDTO> getAllStudents()
    {
        return readOnlyStudentRegistry_.getAll();
    }
    
    /**
     * Enrols student in course.
     * @param eid Enrolment number.
     * @param studentId Student identifier.
     * @param courseId Course identifier.
     */
    public void enrolInCourse(EnrolmentNumber eid, 
                              StudentId studentId, 
                              CourseId courseId)
    {
        EnrolStudent command = new EnrolStudent(eid, studentId, courseId);
        commandBus_.handle(command);
    }

}
