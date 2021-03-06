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


package org.bco.cm.application.query;

import java.util.List;
import org.bco.cm.domain.course.CourseDescriptionId;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.dto.CourseDTO;
import org.bco.cm.util.ReadOnlyRepository;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public interface ReadOnlyCourseRegistry extends ReadOnlyRepository<CourseDTO, CourseId> {
    
    /**
     * Returns all courses the given teacher is responsible for.
     * @param teacherId Teacher identifier.
     * @return Courses. May be empty.
     */
    List<CourseDTO> getTeachersCourses(TeacherId teacherId);    
    
    /**
     * Returns currently active courses (these are courses with their end date later
     * than today's date).
     * @return Active courses. May be empty,
     */
    List<CourseDTO> getActive();
    
    /**
     * Returns currently ongoing courses.
     * @return Ongoing courses. may be empty,
     */
    List<CourseDTO> getOngoing();
    
    /**
     * Inquires whether activated courses associated with course description exists.
     * @param courseDescriptionId Course description identifier.
     * @return Result.
     */
    boolean exists(CourseDescriptionId courseDescriptionId);
    
    /**
     * Returns activated courses associated with course description.
     * @param courseDescriptionId Course description identifier.
     * @return Course.
     */
    List<CourseDTO> getActivatedCourses(CourseDescriptionId courseDescriptionId);
    
    /**
     * Queries for all courses.
     * @param includePast If true, only courses with the end date later than today's
     * date are selected.
     * @return Courses. May be empty.
     */
    List<CourseDTO> getAll(boolean includePast);
}
