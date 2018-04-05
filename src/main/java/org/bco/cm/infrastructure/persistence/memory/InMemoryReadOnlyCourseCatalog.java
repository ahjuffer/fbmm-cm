/*
 * The MIT License
 *
 * Copyright 2017 Pivotal Software, Inc..
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

package org.bco.cm.infrastructure.persistence.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bco.cm.application.query.CourseSpecification;
import org.bco.cm.application.query.ReadOnlyCourseCatalog;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.CourseDescription;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
@Repository
public class InMemoryReadOnlyCourseCatalog implements ReadOnlyCourseCatalog {

    private CourseCatalog courseCatalog_;
    
    @Autowired
    public void setCourseCatalog(CourseCatalog courseCatalog)
    {
        courseCatalog_ = courseCatalog;
    }
    
    @Override    
    public List<CourseDescriptionDTO> getSpecifiedCourses(CourseSpecification spec) 
    {
        return spec.query(this);
    }
    
    @Override
    public List<CourseDescriptionDTO> getAllCourses()
    {
        Collection<CourseDescription> courses = courseCatalog_.forAll();
        List<CourseDescriptionDTO> dtos = new ArrayList<>();
        courses.forEach((course) -> {
            CourseDescriptionDTO dto = this.toDTO(course);
            dtos.add(dto);             
        });
        return dtos;
    }
    
    private CourseDescriptionDTO toDTO(CourseDescription course)
    {
        return course.toDTO();
    }

    @Override
    public CourseDescriptionDTO getCourse(CourseId courseId) 
    {
        CourseDescription course = courseCatalog_.forCourseId(courseId);
        if ( course == null ) {
            throw new NullPointerException(courseId.stringValue() + ": No such course.");
        }
        return this.toDTO(course);
    }

    @Override
    public List<CourseDescriptionDTO> getTeachersCourses(TeacherId teacherId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
