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
import org.bco.cm.application.command.StartNewCourse;
import org.bco.cm.application.query.ReadOnlyTeacherRepository;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.dto.CourseDTO;
import org.bco.cm.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.bco.cm.domain.course.TeacherRegistry;

/**
 * Simplified interface for teachers.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class TeacherFacade {
    
    @Autowired
    private TeacherRegistry teacherRepository_;
    
    @Autowired
    private ReadOnlyTeacherRepository readOnlyTeacherRepository_;
    
    @Autowired
    private CommandBus commandBus_;
    
    /**
     * Generates a new teacher identifier.
     * @return Identifier.
     */
    public TeacherId generateTeacherId()
    {
        return teacherRepository_.generateId();
    }

    /**
     * Adds new course to course catalog.
     * @param teacherId Identifier of responsible teacher.
     * @param courseId New course identifier.
     * @param spec New course specification. Must hold title, description, and 
     * objective.
     */
    public void startNewCourse(TeacherId teacherId, CourseId courseId, CourseDTO spec)
    {
        StartNewCourse command = new StartNewCourse(teacherId, courseId, spec);
        commandBus_.handle(command);
    }
    
    /**
     * Returns all teachers.
     * @return Teachers. May be empty.
     */
    public List<TeacherDTO> getAllTeachers()
    {
        return readOnlyTeacherRepository_.getAllTeachers();
    }

}
