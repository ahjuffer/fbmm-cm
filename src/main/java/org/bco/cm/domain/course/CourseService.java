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

import org.bco.cm.util.CourseDescriptionId;
import org.bco.cm.util.CourseId;
import java.util.List;
import org.bco.cm.domain.teacher.Teacher;
import org.bco.cm.dto.CourseDTO;

/**
 * Domain service for managing courses.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class CourseService {

    private CourseService()
    {        
    }
    
    /**
     * Activates a course. Activated course is added to course registry.
     * @param teacher Responsible teacher.
     * @param courseDescription Course description.
     * @param courseId Identifier of newly activated course.
     * @param spec Activation specification. Must hold start and end date plus
     * the number of seats.
     * @param courseRegistry Course registry.
     * @return Activated course.
     */
    public static Course activate(Teacher teacher, 
                                  CourseDescription courseDescription,
                                  CourseId courseId,
                                  CourseDTO spec,
                                  CourseRegistry courseRegistry)
    {
        if ( !courseDescription.isResponsibleTeacher(teacher) ) {
            throw new IllegalArgumentException(
                "Teacher is not responsible for course '" + 
                courseDescription.getTitle() + "'."
            );
        }
        CourseDescriptionId courseDescriptionId = 
            courseDescription.getCourseDescriptionId();
        List<Course> courses = courseRegistry.getCourses(courseDescriptionId);
        if ( CourseService.isStillActive(courses) ) {
            throw new IllegalStateException(
                "An activated course is still available."
            );            
        }
        Course course = Course.activate(courseId, courseDescription, spec);
        courseRegistry.add(course);
        return course;
    }
    
    /**
     * Updates course in the course registry.
     * @param teacher Responsible teacher.
     * @param course Course being updated.
     * @param spec Update specification.
     * @param courseRegistry Course registry.
     * @return Updated course.
     */
    public static Course update(Teacher teacher, 
                                Course course,
                                CourseDTO spec,
                                CourseRegistry courseRegistry)
    {
        if ( !CourseService.isTeacher(course, teacher) ) {
            throw new IllegalArgumentException(
                "Teacher is not responsible for course '" + 
                course.getTitle() + "'."
            );
        }
        course.update(spec);
        courseRegistry.update(course);
        return course;
    }
    
    /**
     * Removes/delete course. Course must not be ongoing.
     * @param teacher Responsible teacher.
     * @param course Course.
     * @param courseRegistry Course registry. 
     */
    public static void remove(Teacher teacher, 
                              Course course, 
                              CourseRegistry courseRegistry)
    {
        if ( !CourseService.isTeacher(course, teacher) ) {
            throw new IllegalArgumentException(
                "Teacher is not responsible for course '" + 
                course.getTitle() + "'."
            );
        }
        if ( course.isOngoing() ) {
            throw new IllegalStateException("Course is currently ongoing.");
        }
        courseRegistry.remove(course);
    }
    
    /**
     * Starts course.
     * @param course Course.
     * @param teacher Responsible teacher.
     */
    public static void start(Course course, Teacher teacher)
    {
        if ( !CourseService.isTeacher(course, teacher) ) {
            throw new IllegalStateException("Teacher is not responsible for course.");
        }
        course.start();
    }
    
    /**
     * Ends course.
     * @param course Course.
     * @param teacher Responsible teacher.
     */
    public static void end(Course course, Teacher teacher)
    {
        if ( !CourseService.isTeacher(course, teacher) ) {
            throw new IllegalStateException("Teacher is not responsible for course.");
        }
        course.end();
    }
    
    private static boolean isTeacher(Course course, Teacher teacher)
    {
        return course.isResponsibleTeacher(teacher);
    }
    
    private static boolean isStillActive(List<Course> courses)
    {
        for (Course course : courses) {
            if ( course.isActive() ) {
                return true;
            }
        }
        return false;
    }
    
}
