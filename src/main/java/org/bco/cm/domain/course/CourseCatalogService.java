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
import org.bco.cm.dto.ModuleDTO;

/**
 * Domain service for handling course descriptions in the course catalog.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class CourseCatalogService {
    
    private CourseCatalogService()
    {        
    }
    
    /**
     * Adds new course description.
     * @param teacher Responsible teacher.
     * @param courseId New course identifier.
     * @param spec New course specification.
     * @param courseCatalog Course catalog.
     * @return Newly added course.
     * @throws IllegalArgumentException if courseId is already in use.
     */
    public static CourseDescription add(Teacher teacher,
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
        
        // Raise event.
        course.addedToCourseCatalog();
        
        return course;
    }
    
    
    /**
     * Updates course description.
     * @param teacher Responsible teacher.
     * @param course Course description.
     * @param spec Update specification.
     * @param courseCatalog Course catalog.
     * @return Updated course description.
     * @throws IllegalArgumentException if teacher is not responsible for course.
     */
    public static CourseDescription update(Teacher teacher, 
                                           CourseDescription course,
                                           CourseDescriptionDTO spec,
                                           CourseCatalog courseCatalog)
    {
        if ( !course.isResponsibleTeacher(teacher) ) {
            throw new IllegalArgumentException(
                "Teacher is not responsible for course '" + course.getTitle() + "'."
            );
        }
        course.update(spec);
        courseCatalog.update(course);
        return course;
    }
    
    /**
     * Removes courses.
     * @param teacher Responsible teacher.
     * @param course Course description.
     * @param courseCatalog Course catalog.
     * @throws IllegalArgumentException if teacher is not responsible for course.
     */
    public static void remove(Teacher teacher, 
                              CourseDescription course, 
                              CourseCatalog courseCatalog)
    {
        if ( !course.isResponsibleTeacher(teacher) ) {
            throw new IllegalArgumentException(
                "Teacher is not responsible for course '" + course.getTitle() + "'."
            );
        }
        courseCatalog.remove(course);
    }
    
    /**
     * Adds new module to course description.
     * @param teacher Responsible teacher.
     * @param course Course description.
     * @param spec New module specification.
     * @param courseCatalog Course catalog.
     * @return Updated course description.
     * @throws IllegalArgumentException if teacher is not responsible for course.
     */
    public static CourseDescription addModule(Teacher teacher, 
                                              CourseDescription course,
                                              ModuleDTO spec,
                                              CourseCatalog courseCatalog)
    {
        if ( !course.isResponsibleTeacher(teacher) ) {
            throw new IllegalArgumentException(
                "Teacher is not responsible for course '" + course.getTitle() + "'."
            );
        }
        course.addModule(spec);
        courseCatalog.update(course);
        return course;
    }
    
    /**
     * Update module in course description.
     * @param teacher Responsible teacher.
     * @param course Course description.
     * @param moduleId Module identifier.
     * @param spec Module update specification.
     * @param courseCatalog Course catalog.
     * @return Updated course description.
     * @throws IllegalArgumentException if teacher is not responsible for course.
     */
    public static CourseDescription updateModule(Teacher teacher, 
                                                 CourseDescription course,
                                                 int moduleId,
                                                 ModuleDTO spec,
                                                 CourseCatalog courseCatalog)
    {
        if ( !course.isResponsibleTeacher(teacher) ) {
            throw new IllegalArgumentException(
                "Teacher is not responsible for course '" + course.getTitle() + "'."
            );
        }
        course.updateModule(moduleId, spec);
        courseCatalog.update(course);
        return course;
    }
    
    /**
     * Removes module from course description.
     * @param teacher Responsible teacher.
     * @param course Course description.
     * @param moduleId Module identifier.
     * @param courseCatalog 
     * @return Updated course description.
     * @throws IllegalArgumentException if teacher is not responsible for course.
     */
    public static CourseDescription deleteModule(Teacher teacher, 
                                                 CourseDescription course,
                                                 int moduleId,
                                                 CourseCatalog courseCatalog)
    {
        if ( !course.isResponsibleTeacher(teacher) ) {
            throw new IllegalArgumentException(
                "Teacher is not responsible for course '" + course.getTitle() + "'."
            );
        }
        course.removeModule(moduleId);
        courseCatalog.update(course);
        return course;
    }

}
