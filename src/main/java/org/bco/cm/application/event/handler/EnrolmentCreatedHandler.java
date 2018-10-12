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

package org.bco.cm.application.event.handler;

import com.tribc.ddd.domain.event.EventHandler;
import org.bco.cm.domain.course.Course;
import org.bco.cm.util.CourseId;
import org.bco.cm.domain.course.CourseRegistry;
import org.bco.cm.domain.enrolment.Enrolment;
import org.bco.cm.util.EnrolmentNumber;
import org.bco.cm.domain.enrolment.EnrolmentRegistry;
import org.bco.cm.domain.enrolment.event.EnrolmentCreated;
import org.bco.cm.domain.student.Student;
import org.bco.cm.util.StudentId;
import org.bco.cm.domain.student.StudentRegistry;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Adds student to course roster.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class EnrolmentCreatedHandler extends EventHandler<EnrolmentCreated> {
    
    @Autowired
    private CourseRegistry courseRegistry_;
    
    @Autowired
    private StudentRegistry studentRepository_;
    
    @Autowired
    private EnrolmentRegistry enrolmentRegistry_;

    @Override
    public void handle(EnrolmentCreated event) 
    {
        EnrolmentNumber enrolentNumber = event.getEnrolmentNumber();        
        Enrolment enrolment = enrolmentRegistry_.forOne(enrolentNumber);
        
        CourseId courseId = enrolment.getCourseId();
        StudentId studentId = enrolment.getStudentId();                
        Course course = courseRegistry_.forOne(courseId);
        Student student = studentRepository_.forOne(studentId);
        
        course.enrolled(student);
        
        courseRegistry_.update(course);
    }

}
