/*
 * The MIT License
 *
 * Copyright 2018 Andr&#233; H. Juffer, Biocenter Oulu.
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

import org.bco.cm.application.command.UpdateCourse;
import org.bco.cm.domain.course.Course;
import org.bco.cm.util.CourseId;
import org.bco.cm.domain.course.CourseRegistry;
import org.bco.cm.domain.course.CourseService;
import org.bco.cm.domain.teacher.Teacher;
import org.bco.cm.util.TeacherId;
import org.bco.cm.domain.teacher.TeacherRegistry;
import org.bco.cm.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class UpdateCourseHandler 
    extends CmCommandHandler<UpdateCourse>
{
    @Autowired
    private TeacherRegistry teacherRepository_;   
    
    @Autowired
    private CourseRegistry courseRegistry_;

    @Override
    public void handle(UpdateCourse command) 
    {
        TeacherId teacherId = command.getTeacherId();
        Teacher teacher = 
            CommandHandlerUtil.findTeacher(teacherId, teacherRepository_);
        CourseId courseId = command.getCourseId();
        Course course = CommandHandlerUtil.findCourse(courseId, courseRegistry_);
        CourseDTO spec = command.getSpecification();
        
        Course updated = CourseService.update(teacher, 
                                              course, 
                                              spec, 
                                              courseRegistry_);
        
        this.handleEvents(updated);
    }

}
