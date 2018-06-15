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

package org.bco.cm.application.command.handler;

import org.bco.cm.application.command.StartCourse;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.course.CourseRegistry;
import org.bco.cm.domain.course.StartCourseService;
import org.bco.cm.domain.teacher.Teacher;
import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.domain.teacher.TeacherRegistry;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class StartCourseHandler extends CmCommandHandler<StartCourse> {
    
    @Autowired
    private CourseRegistry courseRegistry_;
    
    @Autowired
    private TeacherRegistry teacherRegistry_;
    
    @Override
    public void handle(StartCourse command) 
    {
        CourseId courseId = command.getCourseId();
        Course course = CommandHandlerUtil.findCourse(courseId, courseRegistry_);
        TeacherId teacherId = command.getTeacherId();
        Teacher teacher = CommandHandlerUtil.findTeacher(teacherId, teacherRegistry_);
        
        // Start the course.
        StartCourseService.start(course, teacher);
        courseRegistry_.update(course);
        
        // Handle possible domain events.
        this.handleEvents(course);
        
    }

}
