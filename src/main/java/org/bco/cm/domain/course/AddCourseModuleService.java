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

package org.bco.cm.domain.course;

import org.bco.cm.dto.ModuleDTO;

/**
 * Domain service for adding a module to a course.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class AddCourseModuleService {
    
    private AddCourseModuleService()
    {        
    }
    
    /**
     * Adds course module. Updates course catalog.
     * @param teacher Responsible teacher.
     * @param course Course.
     * @param spec New module specification.
     * @param courseCatalog Course catalog.
     * @return Updated course description.
     * @throws IllegalArgumentException if teacher is not responsible for course.
     */
    public static CourseDescription add(Teacher teacher, 
                                        CourseDescription course, 
                                        ModuleDTO spec,
                                        CourseCatalog courseCatalog)
    {
        if ( !teacher.getTeacherId().equals(course.getTeacherId()) ) {
            throw new IllegalArgumentException("Teacher is not responsible for course.");
        }
        course.addModule(spec);
        courseCatalog.update(course);
        return course;
    }

}
