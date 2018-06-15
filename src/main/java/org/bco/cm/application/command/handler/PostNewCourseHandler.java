/*
 * The MIT License
 *
 * Copyright 2018 André J. Juffer, Triacle Biocomputing
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

import org.bco.cm.application.command.PostNewCourse;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.CourseCatalogService;
import org.bco.cm.domain.course.CourseDescription;
import org.bco.cm.domain.course.CourseDescriptionId;
import org.bco.cm.domain.teacher.Teacher;
import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.domain.teacher.TeacherRegistry;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Handles starting a new course
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class PostNewCourseHandler extends CmCommandHandler<PostNewCourse> {
    
    @Autowired
    private TeacherRegistry teacherRepository_;   
    
    @Autowired
    private CourseCatalog courseCatalog_;
    
    @Override
    public void handle(PostNewCourse command)
    {
        TeacherId teacherId = command.getTeacherId();
        Teacher teacher = 
            CommandHandlerUtil.findTeacher(teacherId, teacherRepository_);
        CourseDescriptionId courseId = command.getCourseId();
        CourseDescriptionDTO spec = command.getSpecification();
        
        // Create course.
        CourseDescription course = 
            CourseCatalogService.add(teacher, courseId, spec, courseCatalog_);
        
        // Handle possible domain events.
        this.handleEvents(course);
    }
    
}
