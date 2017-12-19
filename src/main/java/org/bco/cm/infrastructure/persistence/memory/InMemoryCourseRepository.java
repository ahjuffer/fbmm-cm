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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bco.cm.application.query.CourseFinder;
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.dto.CourseDTO;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
@Repository
public class InMemoryCourseRepository implements CourseCatalog, CourseFinder {
    
    private final Map<String, Course> map_;
    
    public InMemoryCourseRepository()
    {
        map_ = new HashMap<>();
    }
    
    @Override
    public void add(Course course)
    {
        String key = this.key(course);
        if ( map_.containsKey(key) ) {
            throw new IllegalArgumentException(
                key + ": A course with this course identifier is already " + 
                "in course catalog."
            );
        }
        map_.put(key, course);
    }
    
    @Override
    public void update(Course course)
    {
        // Should already be in map, so any update to course is already there.
    }
    
    @Override
    public void remove(Course course)
    {
        String key = this.key(course);
        map_.remove(key);
    }

    @Override
    public CourseId generate() {
        UUID uuid = UUID.randomUUID();
        return CourseId.valueOf(uuid.toString());
    }

    @Override
    public Course forCourseId(CourseId courseId) 
    {
        return map_.get(courseId.getValue());
    }
    
    @Override
    public List<CourseDTO> getCourses(String spec) 
    {
        Collection<Course> courses = map_.values();
        List<CourseDTO> dtos = new ArrayList<>();
        if ( spec.equals("ongoing") ) {
            for (Course course : courses) {
                if ( course.isOngoing() ) {
                    CourseDTO dto = this.toDTO(course);
                    dtos.add(dto);
                }
            }
        } else {
            for (Course course : courses) {
                CourseDTO dto = this.toDTO(course);
                dtos.add(dto);
            }
        }
        return dtos;
    }

    private String key(Course course)
    {
        return course.getCourseId().getValue();
    }

    private CourseDTO toDTO(Course course)
    {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId().getValue());
        dto.setOngoing(course.isOngoing());
        dto.setTitle(course.getTitle());
        return dto;
    }
}
