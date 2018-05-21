/*
 * The MIT License
 *
 * Copyright 2018 juffer.
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

import org.bco.cm.application.command.AddCourseModule;
import org.bco.cm.domain.course.CourseCatalogService;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.CourseDescription;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.Teacher;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.domain.course.TeacherRegistry;
import org.bco.cm.dto.ModuleDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class AddCourseModuleHandler extends CmCommandHandler<AddCourseModule> {
    
    @Autowired
    private CourseCatalog courseCatalog_; 
    
    @Autowired
    private TeacherRegistry teacherRegistry_;

    @Override
    public void handle(AddCourseModule command) 
    {
        // Get teacher, course description and new module specification.
        TeacherId teacherId = command.getTeacherId();
        Teacher teacher = CommandHandlerUtil.findTeacher(teacherId, teacherRegistry_);
        CourseId courseId = command.getCourseId();
        CourseDescription course = 
            CommandHandlerUtil.findCourseDescription(courseId, courseCatalog_);
        ModuleDTO spec = command.getSpec();
        
        // Update course description.
        CourseDescription updated = 
            CourseCatalogService.addModule(teacher, course, spec, courseCatalog_);
        
        // Handle possible domain events.
        this.handleEvents(updated);
        
    }

}
