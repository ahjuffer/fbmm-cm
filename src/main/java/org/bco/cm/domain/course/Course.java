/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer, Biocenter Oulu.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * An unit of teaching that typically lasts one academic term, is led by one 
 * or more instructors (teachers or professors), and has a fixed roster of 
 * students. A course consists of separate components or modules that are 
 * taken in a sequential order. A student progresses to the next module 
 * possibly conditioned on intermediate results obtained for assignments 
 * and/or quizzes.
 * @author André H. Juffer, Biocenter Oulu
 */
public class Course {
    
    private final CourseId courseId_;
    private final String title_;
    private final String description_;
    private final List<Module> modules_;
    private final Set<Student> roster_;
    private boolean ongoing_;
    
    private Course(CourseId courseId, String title, String description)
    {
        courseId_ = courseId;
        title_ = title;
        description_ = description;
        modules_ = new ArrayList<>();
        roster_ = new HashSet<>();
        ongoing_ = false;
    }
    
    /**
     * Returns course identifier.
     * @return Identifier. Never null.
     */
    public CourseId getCourseId()
    {
        return courseId_;
    }
    
    /**
     * Returns course title.
     * @return Title. Never null.
     */
    public String getTitle()
    {
        return title_;
    }
    
    /**
     * Return course description.
     * @return Description.
     */
    public String getDescription()
    {
        return description_;
    }
    
    /**
     * Starts a new course. Neither one of the parameters must be null or empty.
     * @param courseId Course identifier.
     * @param title Course title.
     * @param description Course description.
     * @return Course.
     * @throws NullPointerException if a parameter is null.
     * @throws IllegalArgumentException if title and/or description is an empty string.
     */
    public static Course start(CourseId courseId, String title, String description)
    {
        if ( courseId == null ) {
            throw new NullPointerException("Course identifier must be provided.");
        }
        if ( title == null ) {
            throw new NullPointerException("Course title must be provided.");
        }
        if ( title.isEmpty() ) {
            throw new IllegalArgumentException("Course title must be provided.");
        }
        if ( description == null ) {
            throw new NullPointerException("Course description must be provided.");
        }
        if ( description.isEmpty() ) {
            throw new IllegalArgumentException("Course description must be provided.");
        }
        return new Course(courseId, title, description);
    }
    
    /**
     * Registers student for course. if the course is ongoing, the student also 
     * gain access to the first module.
     * @param student Student. Must not be null.
     * @throws IllegalStateException If student was already registered.
     */
    public void enrol(Student student)
    {
        // Add student to roster.
        if ( roster_.contains(student) ) {
             throw new IllegalStateException("Student already registered for course.");
        }
        roster_.add(student);
        
        // Give access to first module.
        if ( this.isOngoing() ) {
            Module first = this.getFirstModule();
            student.toNextModule(first);
        }        
    }
    
    /**
     * Begins this course. This means that enrolled students can now start with 
     * the first module and <a href="#isOngoing--">isOngoing()</a> will return
     * true. Enrolled students can now start with the first module.
     * @see #isOngoing() 
     */
    public void begin()
    {
        ongoing_ = true;
        this.giveStudentsAccessToFirstModule();
    }
    
    /**
     * Ends this course. This means that all activities undertaken by students 
     * enrolled in this course are halted. This signals the end of the course 
     * and <a href="#isOngoing--">isOngoing()</a> will return false.
     * @see #isOngoing()
     */
    public void end()
    {
        ongoing_ = false;
    }
    
    /**
     * Is this course currently ongoing?
     * @return Result.
     * @see #begin() 
     */
    public boolean isOngoing()
    {
        return ongoing_ == true;
    }
    
    /**
     * Adds new module to course.
     * @param module Module
     */
    public void addModule(Module module)
    {
        if ( module == null ) {
            throw new NullPointerException(
                "Trying to add an undefined module to course."
            );
        }
        modules_.add(module);
    }
    
    private Module getFirstModule()
    {
        if ( modules_.isEmpty() ) {
            throw new IllegalStateException("No modules specified for course.");
        }
        return modules_.get(0);
    }
    
    private void giveStudentsAccessToFirstModule()
    {
        Module first = this.getFirstModule();
        roster_.forEach((student) -> {
            student.toNextModule(first);
        });
    }

}
