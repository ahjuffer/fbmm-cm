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

import java.util.List;
import org.bco.cm.application.query.ReadOnlyCourseCatalog;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Simplified interface for courses.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Transactional( rollbackFor = { Throwable.class } )
public class CourseFacade {
    
    @Autowired    
    private ReadOnlyCourseCatalog readOnlyCourseCatalog_;
    
    /**
     * Generates a new course identifier.
     * @return Identifier.
     */
    @Transactional( readOnly = true )
    public CourseId generateCourseId()
    {
        return CourseId.generate();
    }
    
    /**
     * Returns all courses.
     * @return Courses.
     */
    @Transactional( readOnly = true )
    public List<CourseDescriptionDTO> getAllCourses() 
    {
        return readOnlyCourseCatalog_.getAllCourses();
    }
    
    /**
     * Returns specified course.
     * @param courseId Course identifier.
     * @return Course description.
     */
    @Transactional( readOnly = true )
    public CourseDescriptionDTO getCourse(CourseId courseId)
    {
        return readOnlyCourseCatalog_.getCourse(courseId);
    }
}
