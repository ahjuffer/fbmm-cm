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
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.Student;
import org.bco.cm.domain.course.StudentId;
import org.bco.cm.domain.course.event.EnrolmentCreated;
import org.springframework.beans.factory.annotation.Autowired;
import org.bco.cm.domain.course.StudentRegistry;

/**
 * Adds student to course roster.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class EnrolmentCreatedHandler extends EventHandler<EnrolmentCreated> {
    
    @Autowired
    private CourseCatalog courseCatalog_;
    
    @Autowired
    private StudentRegistry studentRepository_;

    @Override
    public void handle(EnrolmentCreated event) 
    {
        CourseId courseId = event.getCourseId();
        StudentId studentId = event.getStudentId();
        
        /*
        Course course = courseCatalog_.forCourseId(courseId);
        Student student = studentRepository_.forStudentId(studentId);
        course.enrolled(student);
        courseCatalog_.update(course);           
        */
    }

}
