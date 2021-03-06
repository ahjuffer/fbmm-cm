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

package org.bco.cm.domain.enrolment;

import com.tribc.ddd.domain.event.Event;
import com.tribc.ddd.domain.event.Eventful;
import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.enrolment.event.EnrolmentCanceled;
import org.bco.cm.domain.enrolment.event.EnrolmentCreated;
import org.bco.cm.domain.student.Student;
import org.bco.cm.domain.student.StudentId;
import org.bco.cm.dto.EnrolmentDTO;
import org.bco.cm.util.Identifiable;
import org.hibernate.annotations.NaturalId;

/**
 * A record of registration of student for a course.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Entity( name = "Enrolment" )
@Table( name = "enrolments" )
public class Enrolment implements Identifiable, Eventful, Serializable 
{    
    private UUID id_;
    private EnrolmentNumber eid_;
    private CourseId courseId_;
    private StudentId studentId_;
    private Instant dateOfRegistration_;
    
    private final Collection<Event> events_;

    protected Enrolment()
    {
        eid_ = null;
        courseId_ = null;
        studentId_ = null;
        dateOfRegistration_ = Instant.now();
        events_ = new HashSet<>();
    }
    
    private void setId(UUID id)
    {
        id_ = id;
    }
    
    /**
     * Returns identifier assigned or generated by repository.
     * @return Identifier.
     */
    @Id
    @GeneratedValue
    protected UUID getId()
    {
        return id_;
    }
    
    private void setEnrolmentNumber(EnrolmentNumber eid)
    {
        if ( eid == null ) {
            throw new NullPointerException("Missing enrolment number.");
        }
        eid_ = eid;
    }
    
    /**
     * Returns enrolment number.
     * @return Number. Never null.
     */
    @NaturalId
    @Embedded
    public EnrolmentNumber getEnrolmentNumber()
    {
        return eid_;
    }
    
    private void setDateOfRegistration(Instant date)
    {
        if ( date == null ) {
            throw new NullPointerException("Missing date of enrolment.");
        }
        dateOfRegistration_ = date;
    }
    
    /**
     * Returns date of enrolment.
     * @return Date. Never null.
     */
    @Transient
    public Instant getDateOfRegistration()
    {
        return dateOfRegistration_;
    }   
    
    private void setDateOfRegistrationTimestamp(Long timestamp)
    {
        dateOfRegistration_ = Instant.ofEpochMilli(timestamp);
    }
    
    @Column ( name = "date_of_registration" )
    protected Long getDateOfRegistrationTimestamp()
    {
        return dateOfRegistration_.toEpochMilli();
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
    @Embedded
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
    @Embedded
    public StudentId getStudentId()
    {
        return studentId_;
    }
    
    /**
     * Returns new course enrolment. Raises an EnrolmentCreated event.
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
        enrolment.setCourseId(course.getIdentifier());
        enrolment.setStudentId(student.getIdentifier());
        
        // Raise event.
        enrolment.raiseStudentEnrolledInCourseEvent();
        
        return enrolment;
    }
    
    /**
     * Cancels this enrolment. Raises an EnrolmentCanceled event.
     */
    public void cancel()
    {
        this.raiseCanceledEvent();
    }
    
    /**
     * Returns a data transfer object.
     * @return DTO.
     */
    public EnrolmentDTO toDTO()
    {
        EnrolmentDTO dto = new EnrolmentDTO();
        dto.setCourseId(courseId_.stringValue());
        dto.setEnrolmentNumber(eid_.stringValue());
        dto.setStudentId(studentId_.stringValue());
        dto.setDateOfRegistration(dateOfRegistration_);
        return dto;
    }

    @Override
    @Transient
    public String getIdentifierAsString() 
    {
        return eid_.stringValue();
    }
    
    @Transient
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
     * Raises event to inform that a student enrolled in a course.
     */
    private void raiseStudentEnrolledInCourseEvent()
    {
        events_.add(new EnrolmentCreated(eid_));
    }
    
    private void raiseCanceledEvent()
    {
        events_.add(new EnrolmentCanceled(studentId_, courseId_));
    }
    
}
