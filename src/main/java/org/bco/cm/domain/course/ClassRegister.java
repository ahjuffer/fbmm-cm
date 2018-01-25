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

package org.bco.cm.domain.course;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Course registrations.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class ClassRegister {
    
    private EnrolmentRepository enrolmentRepository_;
    
    @Autowired
    public void setEnrolmentRepository(EnrolmentRepository enrolmentRepository)
    {
        enrolmentRepository_ = enrolmentRepository;
    }
    
    /**
     * Enrols student in course.
     * @param student Student.
     * @param course Course.
     * @throws IllegalStateException if student is already enrolled in course.
     */
    public void enrol(Student student, Course course)
    {
        if ( !this.isEnrolled(student, course) ) {
            throw new IllegalStateException("Student already enrolled in course.");
        }
        Enrolment enrolment = new Enrolment(course, student);
        enrolmentRepository_.add(enrolment);
    }
    
    /**
     * Cancels enrolment of student in course.
     * @param student Student.
     * @param course Course.
     */
    public void cancel(Student student, Course course)
    {
        if ( !this.isEnrolled(student, course) ) {
            throw new IllegalStateException("Student is not enrolled in course.");
        }
        Enrolment enrolment = enrolmentRepository_.forCourse(course, student);
        enrolmentRepository_.remove(enrolment);
    }
    
    /**
     * Is student already enrolled for course?
     * @param student Student.
     * @param course Course.
     * @return Result.
     */
    boolean isEnrolled(Student student, Course course)
    {
        return enrolmentRepository_.forCourse(course, student) != null;
    }

}
