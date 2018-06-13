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

package org.bco.cm.api.facade;

import com.tribc.cqrs.domain.command.CommandBus;
import java.util.List;
import org.bco.cm.application.command.AddCourseModule;
import org.bco.cm.application.command.DeleteCourse;
import org.bco.cm.application.command.DeleteCourseModule;
import org.bco.cm.application.command.PostNewCourse;
import org.bco.cm.application.command.UpdateCourse;
import org.bco.cm.application.command.UpdateCourseModule;
import org.bco.cm.application.query.CourseSpecification;
import org.bco.cm.application.query.ReadOnlyCourseCatalog;
import org.bco.cm.domain.course.CourseDescriptionId;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.bco.cm.dto.ModuleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
/**
 * Simplified interface for accessing course catalog.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Transactional( rollbackFor = { Throwable.class } )
public class CourseCatalogFacade {
    
    @Autowired    
    private ReadOnlyCourseCatalog readOnlyCourseCatalog_;
    
    @Autowired
    private CommandBus commandBus_;
    
    /**
     * Generates a new course identifier.
     * @return Identifier.
     */
    @Transactional( readOnly = true )
    public CourseDescriptionId generateCourseId()
    {
        return CourseDescriptionId.generate();
    }
    
    /**
     * Returns all courses.
     * @return Courses.
     */
    @Transactional( readOnly = true )
    public List<CourseDescriptionDTO> getAllCourses() 
    {
        return readOnlyCourseCatalog_.getAll();
    }
    
    /**
     * Returns specified course.
     * @param courseId Course identifier.
     * @return Course description.
     */
    @Transactional( readOnly = true )
    public CourseDescriptionDTO getCourse(CourseDescriptionId courseId)
    {
        return readOnlyCourseCatalog_.getOne(courseId);
    }
    
    /**
     * Returns specified courses.
     * @param spec Specification.
     * @return Courses. May be empty.
     */
    @Transactional( readOnly = true )
    public List<CourseDescriptionDTO> getSpecified(CourseSpecification spec)
    {
        return readOnlyCourseCatalog_.getSpecifiedCourses(spec);
    }
    
    /**
     * Returns all courses the given teacher is responsible for.
     * @param teacherId Teacher identifier.
     * @return Courses. May be empty.
     */    
    @Transactional( readOnly = true )
    public List<CourseDescriptionDTO> getTeachersCourses(TeacherId teacherId)
    {
        return readOnlyCourseCatalog_.getTeachersCourses(teacherId);
    }
    
    
    /**
     * Posts new course to course catalog.
     * @param teacherId Identifier of responsible teacher.
     * @param courseId New course identifier.
     * @param spec New course specification. Must hold title and summary and may 
     * hold modules.
     */
    public void postNewCourse(CourseDescriptionId courseId, 
                              TeacherId teacherId, 
                              CourseDescriptionDTO spec)
    {
        PostNewCourse command = new PostNewCourse(teacherId, courseId, spec);
        commandBus_.handle(command);
    }
    
    /**
     * Updates course description.
     * @param courseId Course description identifier.
     * @param teacherId Identifier of responsible teacher.
     * @param spec Update course specification. Must hold title and summary and 
     * may hold modules.
     */
    public void updateCourse(CourseDescriptionId courseId,
                             TeacherId teacherId,
                             CourseDescriptionDTO spec)
    {
        UpdateCourse command = new UpdateCourse(teacherId, courseId, spec);
        commandBus_.handle(command);
    }
    
    /**
     * Deletes course description in course catalog.
     * @param courseId Course identifier.
     * @param teacherId Identifier of responsible teacher.
     */
    public void deleteCourse(CourseDescriptionId courseId, TeacherId teacherId)
    {
        DeleteCourse command = new DeleteCourse(teacherId, courseId);
        commandBus_.handle(command);
    }
    
    /**
     * Adds module to course.
     * @param courseId Course identifier.
     * @param teacherId Identifier of responsible teacher.
     * @param spec Module specification. This module is appended to the last 
     * module.
     * @see #updateCourse(org.bco.cm.domain.course.TeacherId, org.bco.cm.domain.course.CourseId, org.bco.cm.dto.CourseDescriptionDTO) 
     */
    public void addCourseModule(CourseDescriptionId courseId,
                                TeacherId teacherId,
                                ModuleDTO spec)
    {
        AddCourseModule command = new AddCourseModule(teacherId, courseId, spec);
        commandBus_.handle(command);
    }
    
    /**
     * Updates module in course description. 
     * @param courseId Course identifier.
     * @param teacherId Identifier of responsible teacher.
     * @param moduleId Module identifier.
     * @param spec Update specification.
     * @see #updateCourse(org.bco.cm.domain.course.TeacherId, org.bco.cm.domain.course.CourseId, org.bco.cm.dto.CourseDescriptionDTO) 
     */
    public void updateCourseModule(CourseDescriptionId courseId,
                                   TeacherId teacherId,
                                   int moduleId,
                                   ModuleDTO spec)
    {
        UpdateCourseModule command = 
            new UpdateCourseModule(teacherId, courseId, moduleId, spec);
        commandBus_.handle(command);
    }
    
    /**
     * Remove module from course description.
     * @param courseId Course identifier.
     * @param teacherId Identifier of responsible teacher.
     * @param moduleId Module identifier.
     */
    public void deleteCourseModule(CourseDescriptionId courseId, 
                                   TeacherId teacherId,
                                   int moduleId)
    {
        DeleteCourseModule command =
            new DeleteCourseModule(teacherId, courseId, moduleId);
        commandBus_.handle(command);
    }
    
}
