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

package org.bco.cm.api.facade;

import com.tribc.cqrs.domain.command.CommandBus;
import java.util.List;
import org.bco.cm.application.command.CancelEnrolment;
import org.bco.cm.application.query.ReadOnlyEnrolmentRegistry;
import org.bco.cm.domain.enrolment.EnrolmentNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.bco.cm.dto.EnrolmentDTO;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.student.StudentId;
import org.bco.cm.application.command.EnrolStudent;

/**
 * Simplified interface for the enrolment registry.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Transactional( rollbackFor = { Throwable.class } )
public class EnrolmentFacade {
    
    @Autowired
    private ReadOnlyEnrolmentRegistry readOnlyEnrolmentRegistry_;
    
    @Autowired
    private CommandBus commandBus_;
    
    @Transactional( readOnly = true )
    public EnrolmentNumber generateEnrolmentNumber()
    {
        return EnrolmentNumber.generate();
    }
    
    /**
     * Returns all enrolments for given course.
     * @param courseId Course identifier.
     * @return Enrolments. May be empty.
     */
    @Transactional( readOnly = true )
    public List<EnrolmentDTO> getEnrolments(CourseId courseId)
    {
        return readOnlyEnrolmentRegistry_.getCourseEnrolments(courseId);
    }
    
    /**
     * Returns en enrolment.
     * @param enrolmentNumber Enrolment number.
     * @return Enrolment.
     */
    @Transactional( readOnly = true )
    public EnrolmentDTO getEnrolment(EnrolmentNumber enrolmentNumber)
    {
        return readOnlyEnrolmentRegistry_.getOne(enrolmentNumber);
    }
    
    /**
     * Returns enrolment in course of student.
     * @param courseId Course identifier.
     * @param studentId Student identifier.
     * @return Enrolment.
     */
    @Transactional( readOnly = true )
    public EnrolmentDTO getEnrolment(CourseId courseId, StudentId studentId)
    {
        return readOnlyEnrolmentRegistry_.getCourseEnrolment(courseId, studentId);
    }

    /**
     * Registers student for a course.
     * @param enrolmentNumber Enrolment number.
     * @param courseId Course identifier.
     * @param studentId Student identifier.
     */
    public void register(EnrolmentNumber enrolmentNumber, 
                         CourseId courseId, 
                         StudentId studentId)
    {
        EnrolStudent command = new EnrolStudent(enrolmentNumber, studentId, courseId);
        commandBus_.handle(command);
    }
    
    /**
     * Cancels an enrolment.
     * @param enrolmentNumber Enrolment number.
     */
    public void cancel(EnrolmentNumber enrolmentNumber)
    {
        CancelEnrolment command = new CancelEnrolment(enrolmentNumber);
        commandBus_.handle(command);
    }

}
