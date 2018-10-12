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

package org.bco.cm.application.command;

import com.tribc.cqrs.domain.command.AbstractCommand;
import org.bco.cm.util.CourseId;
import org.bco.cm.util.EnrolmentNumber;
import org.bco.cm.util.StudentId;

/**
 * Command to enroll student in course.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class EnrolStudent extends AbstractCommand {
    
    private final EnrolmentNumber eid_;
    private final StudentId studentId_;
    private final CourseId courseId_;
    
    /**
     * Constructor.
     * @param eid Enrolment number.
     * @param studentId Student identifier.
     * @param courseId Course identifier.
     */
    public EnrolStudent(EnrolmentNumber eid, StudentId studentId, CourseId courseId)
    {
        super(EnrolStudent.class);
        eid_ = eid;
        studentId_ = studentId;
        courseId_ = courseId;
    }
    
    /**
     * Returns enrolment number.
     * @return Enrolment number.
     */
    public EnrolmentNumber getEnrolmentNumber()
    {
        return eid_;
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
