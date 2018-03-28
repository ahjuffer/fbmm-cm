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

package org.bco.cm.domain.course;

import org.bco.cm.dto.CourseDescriptionDTO;

/**
 * Domain service for registering a new course.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class RegisterNewCourseService {
    
    private RegisterNewCourseService()
    {        
    }
    
    /**
     * Registers new course.
     * @param teacher Responsible teacher.
     * @param courseId New course identifier.
     * @param spec New course specification.
     * @param courseCatalog Course catalog.
     * @return Newly registered course.
     */
    public static CourseDescription register(Teacher teacher,
                                             CourseId courseId, 
                                             CourseDescriptionDTO spec, 
                                             CourseCatalog courseCatalog)
    {
        if ( courseCatalog.contains(courseId) ) {
            throw new IllegalArgumentException(
                courseId.stringValue() + ": Identifier already in use."
            );
        }
        
        // Create course and add it to catalog.
        CourseDescription course = CourseDescription.valueOf(teacher, courseId, spec);
        courseCatalog.add(course);
        course.addedToCourseCatalog();
        
        return course;
    }

}
