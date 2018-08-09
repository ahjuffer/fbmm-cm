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

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.bco.cm.domain.course.CourseDescriptionId;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.student.StudentId;
import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.dto.CourseDTO;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.bco.cm.dto.EnrolmentDTO;

/**
 * Specifies a particular set of courses.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class CourseSpecification {
    
    private TeacherId teacherId_;
    private StudentId studentId_;
    private CourseDescriptionId courseDescriptionId_;
    private boolean all_;
    private boolean excludePast_;
    private boolean ongoing_;
    private boolean active_;
    
    public CourseSpecification()
    {
        teacherId_ = null;
        studentId_ = null;
        courseDescriptionId_ = null;
        all_ = false;
        excludePast_ = true;
        ongoing_ = false;
        active_ = false;
    }
    
    /**
     * Sets teacher identifier. If set, all teacher's courses or course 
     * descriptions are selected. All other specifications are ignored.
     * @param teacherId Identifier.
     */
    public void setTeacherId(String teacherId)
    {
        teacherId_ = new TeacherId(teacherId);
    }
    
    /**
     * Sets student identifier. If set, all courses student has enrolled in are 
     * selected. All other specifications are ignored.
     * @param studentId Identifier.
     */
    public void setStudentId(String studentId)
    {
        studentId_ = new StudentId(studentId);
    }
    
    /**
     * Sets course description identifier. If set, all activated courses 
     * associated with the given course description (identifier) with an
     * end date later than today's date are selected. All other 
     * specifications are ignored.
     * @param courseDescriptionId Identifier.
     */
    public void setCourseDescriptionId(String courseDescriptionId)
    {
        courseDescriptionId_ = new CourseDescriptionId(courseDescriptionId);
    }

    /**
     * Specifies to select all activated courses or all course descriptions. 
     * @param excludePast If true, only courses with the end date later than today's
     * date are selected. This flag only applies if querying the course registry.
     * @see #selectAll() 
     */
    public void selectAll(boolean excludePast)
    {
        all_ = true;
        excludePast_ = excludePast;
    }
    
    /**
     * Specifies to select all activated courses or all course descriptions. If
     * querying the course registry, only courses with the end date later than today's
     * date are selected.
     * @see #selectAll(boolean) 
     */
    public void selectAll()
    {
        all_ = true;
         excludePast_ = false;
    }
    
    /**
     * Specifies to select all ongoing courses. Only applies if querying the course
     * registry.
     */
    public void selectOngoing()
    {
        ongoing_ = true;
    }
    
    /**
     * Specifies to select all courses that are active right now. That is, 
     * courses are selected for which today's date is later than the 
     * start date and earlier than the end date of a course. Only applies 
     * if querying the course registry.
     */
    public void selectActive()
    {
        active_ = true;
    }
    
    /**
     * Queries for courses in the catalog according to a specification.
     * @param readOnlyCourseCatalog Course catalog.
     * @return Courses. May be empty.
     */
    public List<CourseDescriptionDTO> query(ReadOnlyCourseCatalog readOnlyCourseCatalog)
    {
        if ( teacherId_ != null ) {
            return readOnlyCourseCatalog.getTeachersCourses(teacherId_);
        }
        List<CourseDescriptionDTO> courses = new ArrayList<>();
        if ( all_ ) {
            courses.addAll(readOnlyCourseCatalog.getAll());
        }
        return courses;
    }
    
    /**
     * Queries for active courses in the registry according to a specification.
     * @param readOnlyCourseRegistry Course registry.
     * @param readOnlyEnrolmentRegistry
     * @return Courses. May be empty.
     */
    public List<CourseDTO> query(ReadOnlyCourseRegistry readOnlyCourseRegistry,
                                 ReadOnlyEnrolmentRegistry readOnlyEnrolmentRegistry)
    {
        if ( teacherId_ != null ) {
            return this.findTeachersCourses(readOnlyCourseRegistry);
        }        
        if ( studentId_ != null ) {
            List<CourseId> courseIds = 
                this.findEnrolments(readOnlyEnrolmentRegistry);
            return this.findStudentsCourses(courseIds, readOnlyCourseRegistry);
        }
        if ( courseDescriptionId_ != null ) {
            return this.findActivatedCourses(readOnlyCourseRegistry);
        }
        if ( all_ ) {
            return readOnlyCourseRegistry.getAll(excludePast_);
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
    
    private List<CourseDTO> findTeachersCourses(ReadOnlyCourseRegistry readOnlyCourseRegistry)
    {
        return readOnlyCourseRegistry.getTeachersCourses(teacherId_);
    }
    
    private List<CourseId> findEnrolments(ReadOnlyEnrolmentRegistry readOnlyEnrolmentRegistry)
    {
        List<EnrolmentDTO> enrolments = 
            readOnlyEnrolmentRegistry.getStudentEnrolments(studentId_);
        List<CourseId> courseIds = new ArrayList<>();
        enrolments.forEach(enrolment -> {
            courseIds.add(new CourseId(enrolment.getCourseId()));
        });
        return courseIds;
    }
    
    private List<CourseDTO> findStudentsCourses(List<CourseId> courseIds,
                                                ReadOnlyCourseRegistry readOnlyCourseRegistry)
    {
        List<CourseDTO> courses = new ArrayList<>();
        courseIds.forEach(courseId -> {
            CourseDTO course = readOnlyCourseRegistry.getOne(courseId);
            courses.add(course);
        });
        return courses;
    }
    
    private List<CourseDTO> findActivatedCourses(ReadOnlyCourseRegistry readOnlyCourseRegistry)
    {
        List<CourseDTO> courses = 
            readOnlyCourseRegistry.getActivatedCourses(courseDescriptionId_);
        List<CourseDTO> activated = new ArrayList<>();
        Instant now = Instant.now();
        courses.forEach(course -> {
            Instant endDate = course.getEndDate();
            if (endDate.isAfter(now) ) {
                activated.add(course);
            }
        });
        return activated;
    }

}
