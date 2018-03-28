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

package org.bco.cm;

import com.tribc.cqrs.domain.command.CommandBus;
import com.tribc.ddd.domain.event.EventBus;
import org.bco.cm.api.facade.CourseFacade;
import org.bco.cm.api.facade.StudentFacade;
import org.bco.cm.api.facade.TeacherFacade;
import org.bco.cm.application.command.handler.CmCommandBus;
import org.bco.cm.application.command.handler.EnrolStudentHandler;
import org.bco.cm.application.command.handler.PostNewCourseHandler;
import org.bco.cm.application.command.handler.RegisterNewStudentHandler;
import org.bco.cm.application.command.handler.RegisterNewTeacherHandler;
import org.bco.cm.application.event.handler.CmEventBus;
import org.bco.cm.application.event.handler.EnrolmentCreatedHandler;
import org.bco.cm.application.event.handler.NewStudentRegisteredHandler;
import org.bco.cm.application.event.handler.NewTeacherRegisteredHandler;
import org.bco.cm.application.query.ReadOnlyStudentRegistry;
import org.bco.cm.application.query.ReadOnlyTeacherRegistry;
import org.bco.cm.application.query.ReadOnlyCourseCatalog;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.StudentRegistry;
import org.bco.cm.domain.course.TeacherRegistry;
import org.bco.cm.infrastructure.persistence.hibernate.HibernateCourseCatalog;
import org.bco.cm.infrastructure.persistence.hibernate.HibernateReadOnlyStudentRegistry;
import org.bco.cm.infrastructure.persistence.hibernate.HibernateReadOnlyTeacherRegistry;
import org.bco.cm.infrastructure.persistence.hibernate.HibernateStudentRegistry;
import org.bco.cm.infrastructure.persistence.hibernate.HibernateTeacherRegistry;
import org.bco.cm.infrastructure.persistence.hibernate.HibernateReadOnlyCourseCatalog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.bco.cm.application.event.handler.NewCourseAddedToCatalogHandler;

/**
 * Bean configuration.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Configuration
public class CmConfiguration 
{
    
    // Repositories.
    
    @Bean
    @Primary
    StudentRegistry studentRegistry()
    {
        return new HibernateStudentRegistry();
    }
    
    @Bean
    @Primary
    ReadOnlyStudentRegistry readOnlyStudentRegistry()
    {
        return new HibernateReadOnlyStudentRegistry();
    }
    
    @Bean
    @Primary    
    TeacherRegistry teacherRegistry()
    {
        return new HibernateTeacherRegistry();
    }
    
    @Bean
    @Primary
    ReadOnlyTeacherRegistry readOnlyTeacherRegistry()
    {
        return new HibernateReadOnlyTeacherRegistry();
    }
    
    @Bean
    @Primary
    CourseCatalog courseCatalog()
    {
        return new HibernateCourseCatalog();
    }
    
    @Bean
    @Primary
    ReadOnlyCourseCatalog readOnlyCourseCatalog()
    {
        return new HibernateReadOnlyCourseCatalog();
    }
    
    
    // Facades
    
    @Bean
    CourseFacade courseFacade()
    {
        return new CourseFacade();
    }
    
    @Bean
    TeacherFacade teacherFacade()
    {
        return new TeacherFacade();
    }
    
    @Bean
    StudentFacade studentFacade()
    {
        return new StudentFacade();
    }
    
    
    // Handlers
    
    @Bean
    RegisterNewStudentHandler registerNewStudentHandler()
    {
        return new RegisterNewStudentHandler();
    }
    
    @Bean
    RegisterNewTeacherHandler registerNewTeacherHandler()
    {
        return new RegisterNewTeacherHandler();
    }
    
    @Bean 
    EnrolStudentHandler enrolStudentHandler()
    {
        return new EnrolStudentHandler();
    }
    
    @Bean
    PostNewCourseHandler postNewCourseHandler()
    {
        return new PostNewCourseHandler();
    }
    
    @Bean
    NewStudentRegisteredHandler newStudentRegisteredHandler()
    {
        return new NewStudentRegisteredHandler();
    }
    
    @Bean
    NewTeacherRegisteredHandler newTeacherRegistredHandler()
    {
        return new NewTeacherRegisteredHandler();
    }
    
    @Bean
    NewCourseAddedToCatalogHandler newCourseAddedToCatalogHandler()
    {
        return new NewCourseAddedToCatalogHandler();
    }
    
    @Bean
    EnrolmentCreatedHandler enrolmentCreatedHandler()
    {
        return new EnrolmentCreatedHandler();
    }
    
    @Bean 
    EventBus eventBus()
    {
        return new CmEventBus();
    }

    @Bean
    CommandBus commandBus()
    {
        return new CmCommandBus();
    }
    
}
