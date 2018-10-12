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
import org.bco.cm.util.CourseId;
import org.bco.cm.domain.course.CourseRegistry;
import org.bco.cm.domain.enrolment.Enrolment;
import org.bco.cm.util.EnrolmentNumber;
import org.bco.cm.domain.enrolment.EnrolmentRegistry;
import org.bco.cm.domain.student.Student;
import org.bco.cm.util.StudentId;
import org.bco.cm.domain.student.StudentRegistry;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Creates a record of enrolment in a course.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class EnrolStudentHandler extends CmCommandHandler<EnrolStudent> {
    
    @Autowired
    private CourseRegistry courseRegistry_;
    
    @Autowired
    private StudentRegistry studentRegistry_;
    
    @Autowired
    private EnrolmentRegistry enrolmentRegistry_;
    
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
        Course course = CommandHandlerUtil.findCourse(courseId, courseRegistry_);
        Student student = CommandHandlerUtil.findStudent(studentId, studentRegistry_);        
        
        // Enrol student.
        Enrolment enrolment = Enrolment.register(eid, course, student);
        enrolmentRegistry_.add(enrolment);

        // Handle events.
        this.handleEvents(enrolment);
    }
}
