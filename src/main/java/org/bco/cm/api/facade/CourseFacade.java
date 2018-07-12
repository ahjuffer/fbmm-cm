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
import org.bco.cm.application.command.ActivateCourse;
import org.bco.cm.application.query.CourseSpecification;
import org.bco.cm.application.query.ReadOnlyCourseRegistry;
import org.bco.cm.application.query.ReadOnlyEnrolmentRegistry;
import org.bco.cm.domain.course.CourseDescriptionId;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Simplified interface for handling active courses.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Transactional( rollbackFor = { Throwable.class } )
public class CourseFacade {
    
    @Autowired
    private ReadOnlyCourseRegistry readOnlyCourseRegistry_;
    
    @Autowired
    private ReadOnlyEnrolmentRegistry readOnlyEnrolmentRegistry_;
    
    @Autowired
    private CommandBus commandBus_;
    
    /**
     * Generates new course identifier.
     * @return New course identifier.
     */
    @Transactional( readOnly = true )
    public CourseId generate()
    {
        return CourseId.generate();
    }
    
    /**
     * Returns all courses, whether or not they are active.
     * @return Courses.
     */
    @Transactional( readOnly = true )
    public List<CourseDTO> getAllCourses()
    {
        return readOnlyCourseRegistry_.getAll();
    }
    
    /**
     * Returns a single course.
     * @param courseId Course identifier.
     * @return Course.
     */
    @Transactional( readOnly = true )
    public CourseDTO getCourse(CourseId courseId)
    {
        return readOnlyCourseRegistry_.getOne(courseId);
    }
    
    /**
     * Returns specified courses.
     * @param spec Specification.
     * @return Courses. May be empty.
     */
    @Transactional( readOnly = true )
    public List<CourseDTO> getSpecified(CourseSpecification spec)
    {
        return spec.query(readOnlyCourseRegistry_, readOnlyEnrolmentRegistry_);
    }

    /**
     * Activates courses.
     * @param teacherId Identifier of teacher who activates course.
     * @param courseDescriptionId Identifier of course to be activated.
     * @param courseId Identifier of newly activated course.
     * @param spec Activation specification. Must hold start and end date plus
     * the number of seats.
     */
    public void activate(TeacherId teacherId, 
                         CourseDescriptionId courseDescriptionId,
                         CourseId courseId, 
                         CourseDTO spec)
    {
        ActivateCourse command = 
            new ActivateCourse(teacherId, courseDescriptionId, courseId, spec);
        commandBus_.handle(command);
    }

}
