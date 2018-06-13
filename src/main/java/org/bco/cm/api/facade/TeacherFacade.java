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
import org.bco.cm.application.command.RegisterNewTeacher;
import org.bco.cm.application.query.ReadOnlyTeacherRegistry;
import org.bco.cm.domain.course.CourseDescriptionId;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.dto.CourseDTO;
import org.bco.cm.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Simplified interface for teachers.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Transactional( rollbackFor = { Throwable.class } )
public class TeacherFacade {
    
    @Autowired
    private ReadOnlyTeacherRegistry readOnlyTeacherRepository_;
    
    @Autowired
    private CommandBus commandBus_;
    
    /**
     * Generates a new teacher identifier.
     * @return Identifier.
     */
    @Transactional( readOnly=true )
    public TeacherId generateTeacherId()
    {
        return TeacherId.generateId();
    }

    /**
     * Returns all teachers.
     * @return Teachers. May be empty.
     */
    @Transactional( readOnly=true )
    public List<TeacherDTO> getAllTeachers()
    {
        return readOnlyTeacherRepository_.getAll();
    }
    
    /**
     * Returns teacher.
     * @param teacherId Teacher identifier.
     * @return Teacher.
     */
    @Transactional( readOnly=true )
    public TeacherDTO getTeacher(TeacherId teacherId)
    {
        return readOnlyTeacherRepository_.getOne(teacherId);
    }
    
    /**
     * Adds new teacher to the teacher registry.
     * @param teacherId New teacher identifier.
     * @param spec New teacher specification.
     */
    public void register(TeacherId teacherId, TeacherDTO spec)
    {
        RegisterNewTeacher command = new RegisterNewTeacher(teacherId, spec);
        commandBus_.handle(command);
    }

}
