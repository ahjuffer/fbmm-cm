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

import org.bco.cm.domain.student.StudentId;
import org.bco.cm.domain.student.Student;
import com.tribc.ddd.domain.event.Event;
import com.tribc.ddd.domain.event.Eventful;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.bco.cm.domain.course.event.EnrolmentCreated;
import org.bco.cm.util.Identifiable;

/**
 * A record of registration of student for a course.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class Enrolment implements Identifiable, Eventful {
    
    private EnrolmentNumber eid_;
    private CourseId courseId_;
    private StudentId studentId_;
    private Instant when_;
    
    private final Collection<Event> events_;

    private Enrolment()
    {
        eid_ = null;
        courseId_ = null;
        studentId_ = null;
        when_ = Instant.ofEpochMilli(0);
        events_ = new HashSet<>();
    }
    
    private void setEnrolmentNumber(EnrolmentNumber eid)
    {
        if ( eid == null ) {
            throw new NullPointerException("Missing enrolment number.");
        }
        eid_ = eid;
    }
    
    private void setWhen(Instant when)
    {
        if ( when == null ) {
            throw new NullPointerException("Missing date of enrolment.");
        }
        when_ = when;
    }
    
    private void setCourseId(CourseId courseId)
    {
        if ( courseId == null ) {
            throw new NullPointerException(
                "Missing course identifier."
            );
        }
        courseId_ = courseId;
    }
    
    /**
     * Returns course identifier.
     * @return Identifier. Never null.
     */
    public CourseId getCourseId()
    {
        return courseId_;
    }
    
    private void setStudentId(StudentId studentId)
    {
        if ( studentId == null ) {
            throw new NullPointerException(
                "Missing student identifier."
            );            
        }
        studentId_ = studentId;
    }
    
    /**
     * Returns student identifier.
     * @return Identifier. Never null.
     */
    public StudentId getStudentId()
    {
        return studentId_;
    }
    
    /**
     * Returns new course enrolment. Raises a StudentEnrolledInCourse event.
     * @param eid Enrolment number.
     * @param course Course.
     * @param student Student.
     * @return New enrolment.
     */
    public static Enrolment register(EnrolmentNumber eid, Course course, Student student)
    {
        if ( course == null ) {
            throw new NullPointerException(
                "Missing course."
            );
        }
        if ( student == null ) {
            throw new NullPointerException(
                "Missing student."
            );            
        }
        Enrolment enrolment = new Enrolment();
        enrolment.setEnrolmentNumber(eid);
        enrolment.setWhen(Instant.now());
        enrolment.setCourseId(course.getIdentifier());
        enrolment.setStudentId(student.getIdentifier());
        enrolment.raiseStudentEnrolledInCourseEvent();
        return enrolment;
    }

    @Override
    public String getIdentifierAsString() 
    {
        return eid_.stringValue();
    }
    
    @Override
    public Collection<Event> getEvents() 
    {
        return Collections.unmodifiableCollection(events_);
    }

    @Override
    public void clearEvents() 
    {
        events_.clear();
    }
    
    /**
     * Raises event to informs that a student has enrolled in course.
     */
    private void raiseStudentEnrolledInCourseEvent()
    {
        events_.add(new EnrolmentCreated(studentId_, courseId_));
    }
    
}
