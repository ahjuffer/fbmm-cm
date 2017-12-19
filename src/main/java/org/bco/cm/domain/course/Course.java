/*
 * The MIT License
 *
 * Copyright 2017 ajuffer.
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
 * @author ajuffer
 */
public class Course {
    
    private final CourseId courseId_;
    private final String title_;
    private final List<Module> modules_;
    private final Set<Student> roster_;
    private boolean ongoing_;
    
    private Course(CourseId courseId, String title)
    {
        courseId_ = courseId;
        title_ = title;
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
     * Starts a new course.
     * @param courseId Course identifier.
     * @param title Course title.
     * @return Course.
     * @throws NullPointerException if any argument is null.
     * @throws IllegalArgumentException if title is an empty string.
     */
    public static Course start(CourseId courseId, String title)
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
        return new Course(courseId, title);
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
     * Lets the course begin.
     */
    public void begin()
    {
        ongoing_ = true;
        this.giveStudentsAccessToFirstModule();
    }
    
    /**
     * Stops course.
     */
    public void stop()
    {
        ongoing_ = false;
    }
    
    /**
     * Did the course already start?
     * @return Result.
     */
    public boolean isOngoing()
    {
        return ongoing_ == true;
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
