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
import org.bco.cm.application.query.CourseRepository;
import org.bco.cm.application.query.CourseSpecification;
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
@Repository
public class InMemoryCourseRepository implements CourseRepository {

    private InMemoryCourseCatalog courseCatalog_;
    
    @Autowired
    public void setCourseCatalog(CourseCatalog courseCatalog)
    {
        courseCatalog_ = (InMemoryCourseCatalog)courseCatalog;
    }
    
    @Override
    public List<CourseDTO> getCourses(CourseSpecification spec) 
    {
        return spec.query(this);
    }
    
    @Override
    public List<CourseDTO> getAll()
    {
        Collection<Course> courses = courseCatalog_.allCourses();
        List<CourseDTO> dtos = new ArrayList<>();
        for (Course course : courses) {
            CourseDTO dto = this.toDTO(course);
            dtos.add(dto); 
        }
        return dtos;
    }
    
    @Override
    public List<CourseDTO> getOngoing()
    {
        Collection<Course> courses = courseCatalog_.allCourses();
        List<CourseDTO> dtos = new ArrayList<>();
        for (Course course : courses) {
            if ( course.isOngoing() ) {
                CourseDTO dto = this.toDTO(course);
                dtos.add(dto); 
            }
        }
        return dtos;
        
    }

    private CourseDTO toDTO(Course course)
    {
        return course.toDTO();
    }
    
}
