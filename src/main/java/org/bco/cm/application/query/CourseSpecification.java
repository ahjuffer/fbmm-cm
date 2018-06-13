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

import java.util.ArrayList;
import java.util.List;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.dto.CourseDTO;
import org.bco.cm.dto.CourseDescriptionDTO;

/**
 * Specifies a particular set of courses.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class CourseSpecification {
    
    private String teacherId_;
    private boolean all_;
    private boolean ongoing_;
    private boolean active_;
    
    // If false, all attributes have their default values.
    private boolean specified_;
    
    public CourseSpecification()
    {
        teacherId_ = null;
        all_ = false;
        ongoing_ = false;
        active_ = false;
        specified_ = false;
    }
    
    public void setTeacherId(String teacherId)
    {
        teacherId_ = teacherId;
        this.markSpecified();
    }

    public void setAll(boolean all)
    {
        all_ = all;
        this.markSpecified();
    }
    
    public void setOngoing(boolean ongoing)
    {
        ongoing_ = ongoing;
        this.markSpecified();
    }
    
    public void setActive(boolean active)
    {
        active_ = active;
        this.markSpecified();
    }
    
    /**
     * Queries for courses in the catalog according to the specification.
     * @param readOnlyCourseCatalog Course catalog.
     * @return Courses. May be empty.
     */
    public List<CourseDescriptionDTO> query(ReadOnlyCourseCatalog readOnlyCourseCatalog)
    {
        if ( teacherId_ != null ) {
            TeacherId teacherId = new TeacherId(teacherId_);
            return readOnlyCourseCatalog.getTeachersCourses(teacherId);
        }
        List<CourseDescriptionDTO> courses = new ArrayList<>();
        if ( all_ ) {
            courses.addAll(readOnlyCourseCatalog.getAll());
        }
        return courses;
    }
    
    /**
     * Queries for active courses in the registry according to the specification.
     * @param readOnlyCourseRegistry Course registry.
     * @return Courses. May be empty.
     */
    public List<CourseDTO> query(ReadOnlyCourseRegistry readOnlyCourseRegistry)
    {
        if ( teacherId_ != null ) {
            TeacherId teacherId = new TeacherId(teacherId_);
            return readOnlyCourseRegistry.getTeachersCourses(teacherId);
        }
        if ( all_ ) {
            return readOnlyCourseRegistry.getAll();
        }
        List<CourseDTO> courses = new ArrayList<>();
        if ( active_ ) {
            courses.addAll(readOnlyCourseRegistry.getActive());
        }
        if ( ongoing_ ) {
            courses.addAll(readOnlyCourseRegistry.getOngoing());
        }
        return courses;
    }
    
    private void markSpecified()
    {
        specified_ = true;
    }
    
    /**
     * Any of the attributes were specified? That is, are -not- having their default
     * values.
     * @return Result
     */
    public boolean isSpecified()
    {
        return specified_;
    }

}
