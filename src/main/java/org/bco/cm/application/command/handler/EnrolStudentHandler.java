/*
 * The MIT License
 *
 * Copyright 2017 Andr&#233; Juffer, Triacle Biocomputing.
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

package org.bco.cm.application.command.handler;

import org.bco.cm.application.command.EnrolStudent;
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.EnrolmentNumber;
import org.bco.cm.domain.course.EnrolmentRepository;
import org.bco.cm.domain.student.Student;
import org.bco.cm.domain.student.StudentId;
import org.bco.cm.domain.student.StudentRegistry;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Creates a record of enrolment in a course.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class EnrolStudentHandler extends CmCommandHandler<EnrolStudent> {
    
    @Autowired
    private CourseCatalog courseCatalog_;
    
    @Autowired
    private StudentRegistry studentRepository_;
    
    @Autowired
    private EnrolmentRepository enrolmentRepository_;
    
    /**
     * @param command Command.
     * @throws IllegalStateException if student was already enrolled in course.
     */
    @Override
    public void handle(EnrolStudent command)
    {
        EnrolmentNumber eid = command.getEnrolmentNumber();
        StudentId studentId = command.getStudentId();
        CourseId courseId = command.getCourseId();
           
        Student student = CommandHandlerUtil.findStudent(studentId, studentRepository_);
        
        /*
        Course course = courseCatalog_.forCourseId(courseId);
        if ( this.isEnrolled(student, course) ) {
            throw new IllegalStateException("Student already enrolled in course.");
        }       
        Enrolment enrolment = Enrolment.register(eid, course, student);
        enrolmentRepository_.add(enrolment);

        // Handle in same thread.
        this.handleEvents(enrolment);
        */
    }
    
    private boolean isEnrolled(Student student, Course course)
    {
        return enrolmentRepository_.forCourse(course, student) != null;
    }
}
