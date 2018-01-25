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

import org.bco.cm.application.command.StartNewCourse;
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.course.CourseCatalog;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Handles starting a new course
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class StartNewCourseHandler {
    
    private CourseCatalog courseCatalog_;
    
    @Autowired
    public void setCourseCatalog(CourseCatalog courseCatalog)
    {
        courseCatalog_ = courseCatalog;
    }
    
    public void handle(StartNewCourse command)
    {
        Course course = Course.start(command.getCourseId(), 
                                     command.getCourseSpecification());
        courseCatalog_.add(course);
    }
    
}