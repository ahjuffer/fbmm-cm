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

package org.bco.cm.domain.enrolment.event;

import com.tribc.ddd.domain.event.AbstractEvent;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.student.StudentId;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class EnrolmentCanceled extends AbstractEvent {

    private final StudentId studentId_;
    private final CourseId courseId_;

    public EnrolmentCanceled(StudentId studentId, CourseId courseId)
    {
        super(EnrolmentCanceled.class);
        studentId_ = studentId;
        courseId_ = courseId;        
    }
    
    /**
     * Returns student identifier.
     * @return Identifier.
     */
    public StudentId getStudentId()
    {
        return studentId_;
    }
    
    /**
     * Returns course identifier.
     * @return Identifier.
     */
    public CourseId getCourseId()
    {
        return courseId_;
    }
    
}
