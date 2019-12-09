/*
 * The MIT License
 *
 * Copyright 2019 André H. Juffer, Biocenter Oulu
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

package org.bco.cm.api.rest.spring;

import java.util.List;
import org.bco.cm.api.facade.CourseCatalogFacade;
import org.bco.cm.application.query.CourseSpecification;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.util.CourseDescriptionId;
import org.bco.cm.util.TeacherId;
import org.bco.security.SecurityToken;
import org.bco.security.annotation.Authorizable;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class CourseCatalogResource {
    
    @Autowired
    private CourseCatalogFacade courseCatalogFacade_;
    
    CourseDescriptionDTO getCourse(CourseDescriptionId courseId)
    {
        return courseCatalogFacade_.getCourse(courseId);
    }
    
    List<CourseDescriptionDTO> getSpecified(CourseSpecification spec)
    {
        return courseCatalogFacade_.getSpecified(spec);
    }
    
    @Authorizable
    CourseDescriptionDTO postNewCourse(SecurityToken securityToken,
                                       TeacherId teacherId,
                                       CourseDescriptionDTO spec)
    {
        CourseDescriptionId courseId = courseCatalogFacade_.generate();
        courseCatalogFacade_.postNewCourse(courseId, teacherId, spec);
        return courseCatalogFacade_.getCourse(courseId);     
    }
    
    @Authorizable
    CourseDescriptionDTO updateCourse(SecurityToken securityToken,
                                      CourseDescriptionId courseId,
                                      TeacherId teacherId,
                                      CourseDescriptionDTO spec)
    {
        courseCatalogFacade_.updateCourse(courseId, teacherId, spec);
        return courseCatalogFacade_.getCourse(courseId);
    }
    
    @Authorizable
    CourseDescriptionDTO addCourseModule(SecurityToken securityToken,
                                         CourseDescriptionId courseId,
                                         TeacherId teacherId,
                                         ModuleDTO spec)
    {
        courseCatalogFacade_.addCourseModule(courseId, teacherId, spec);
        return courseCatalogFacade_.getCourse(courseId);
    }
    
    @Authorizable
    CourseDescriptionDTO updateCourseModule(SecurityToken securityToken,
                                            CourseDescriptionId courseId,
                                            TeacherId teacherId,
                                            int moduleId,
                                            ModuleDTO spec)
    {
        courseCatalogFacade_.updateCourseModule(courseId, teacherId, moduleId, spec);
        return courseCatalogFacade_.getCourse(courseId);
    }
    
    @Authorizable
    CourseDescriptionDTO deleteCourseModule(SecurityToken securityToken,
                                            CourseDescriptionId courseId,
                                            TeacherId teacherId,
                                            int moduleId)
    {
        courseCatalogFacade_.deleteCourseModule(courseId, teacherId, moduleId);
        return courseCatalogFacade_.getCourse(courseId);
    }
    
    @Authorizable
    void deleteCourse(SecurityToken securityToken,
                      CourseDescriptionId courseId,
                      TeacherId teacherId)
    {
        courseCatalogFacade_.deleteCourse(courseId, teacherId);
    }

}
