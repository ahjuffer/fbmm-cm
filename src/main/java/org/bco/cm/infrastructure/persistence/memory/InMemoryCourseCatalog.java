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

package org.bco.cm.infrastructure.persistence.memory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.CourseId;

/**
 * Stores courses in memory.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class InMemoryCourseCatalog implements CourseCatalog {
    
    private final Map<String, Course> map_;
    
    public InMemoryCourseCatalog()
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
        // Course should already be in map_, so any update made to the course 
        // is already accounted for.
        if ( !map_.containsKey(course.getCourseId().getValue()) ) {
            throw new IllegalStateException(
                "Trying to update a course that is not in the course catalog."
            );
        }
    }

    @Override
    public void remove(Course course) 
    {
        String key = this.key(course);
        map_.remove(key);
    }

    @Override
    public CourseId generateCourseId() 
    {
        UUID uuid = UUID.randomUUID();
        return CourseId.valueOf(uuid.toString());
    }
    
    Collection<Course> allCourses()
    {
        return map_.values();
    }

    @Override
    public Course forCourseId(CourseId courseId) 
    {
        return map_.get(courseId.getValue());
    }

    private String key(Course course)
    {
        return course.getCourseId().getValue();
    }

}
