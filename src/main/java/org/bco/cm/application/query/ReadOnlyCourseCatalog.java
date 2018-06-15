/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer
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

package org.bco.cm.application.query;

import java.util.List;
import org.bco.cm.domain.course.CourseDescriptionId;
import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.bco.cm.util.ReadOnlyRepository;

/**
 * A read-only catalog of all available courses. Courses are stored as DTOs.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public interface ReadOnlyCourseCatalog 
    extends ReadOnlyRepository<CourseDescriptionDTO, CourseDescriptionId>{
    
    /**
     * Finds course according to a specification.
     * @param spec Specification, either "all" or "ongoing".
     * @return Courses. May be empty.
     * @throws IllegalArgumentException if spec is neither "all" nor "ongoing".
     */
    List<CourseDescriptionDTO> getSpecifiedCourses(CourseSpecification spec);
    
    /**
     * Returns all courses the given teacher is responsible for.
     * @param teacherId Teacher identifier.
     * @return Courses. May be empty.
     */
    List<CourseDescriptionDTO> getTeachersCourses(TeacherId teacherId);
    
}
