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

package org.bco.cm.application.query;

import java.util.ArrayList;
import java.util.List;
import org.bco.cm.util.CourseId;
import org.bco.cm.util.StudentId;
import org.bco.cm.dto.EnrolmentDTO;

/**
 * Specifies a particular set of enrolments.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class EnrolmentSpecification {
    
    private String courseId_;
    private String studentId_;
    
    public EnrolmentSpecification()
    {
        courseId_ = null;
        studentId_ = null;
    }
    
    public void setCourseId(String courseId)
    {
        courseId_ = courseId;
    }
    
    public void setStudentId(String studentId)
    {
        studentId_ = studentId;
    }
    
    /**
     * Queries for enrolments.
     * @param readOnlyEnrolmentRegistry Enrolment registry.
     * @return Enrolments. May be empty. Never null.
     */
    public List<EnrolmentDTO> query(ReadOnlyEnrolmentRegistry readOnlyEnrolmentRegistry)
    {
        List<EnrolmentDTO> enrolments = new ArrayList<>();
        if ( this.areAllSpecified() ) {
            CourseId courseId = new CourseId(courseId_);
            StudentId studentId = new StudentId(studentId_);
            EnrolmentDTO enrolment = 
                readOnlyEnrolmentRegistry.getCourseEnrolment(courseId, studentId);
            enrolments.add(enrolment);
        } else {
            if ( this.hasCourseId() ) {
                CourseId courseId = new CourseId(courseId_);
                enrolments.addAll(readOnlyEnrolmentRegistry.getCourseEnrolments(courseId));
            }
            if ( this.hasStudentId() ) {
                StudentId studentId = new StudentId(studentId_);
                enrolments.addAll(readOnlyEnrolmentRegistry.getStudentEnrolments(studentId));
            }
        }
        return enrolments;
    }
    
    private boolean areAllSpecified()
    {
        return studentId_ != null && courseId_ != null;
    }
    
    private boolean hasCourseId()
    {
        return courseId_ != null;
    }
    
    private boolean hasStudentId()
    {
        return studentId_ != null;
    }

}
