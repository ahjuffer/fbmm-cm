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
import org.bco.cm.api.facade.AccountFacade;
import org.bco.cm.api.facade.CourseCatalogFacade;
import org.bco.cm.api.facade.CourseFacade;
import org.bco.cm.api.facade.EnrolmentFacade;
import org.bco.cm.api.facade.StudentFacade;
import org.bco.cm.api.facade.TeacherFacade;
import org.bco.cm.application.AccountService;
import org.bco.cm.application.command.handler.ActivateCourseHandler;
import org.bco.cm.application.command.handler.AddCourseModuleHandler;
import org.bco.cm.application.command.handler.CancelEnrolmentHandler;
import org.bco.cm.application.command.handler.CmCommandBus;
import org.bco.cm.application.command.handler.CompleteQuizHandler;
import org.bco.cm.application.command.handler.DeleteCourseDescriptionHandler;
import org.bco.cm.application.command.handler.DeleteCourseHandler;
import org.bco.cm.application.command.handler.DeleteCourseModuleHandler;
import org.bco.cm.application.command.handler.EndCourseHandler;
import org.bco.cm.application.command.handler.EnrolStudentHandler;
import org.bco.cm.application.command.handler.PostNewCourseHandler;
import org.bco.cm.application.command.handler.RegisterNewStudentHandler;
import org.bco.cm.application.command.handler.RegisterNewTeacherHandler;
import org.bco.cm.application.command.handler.StartCourseHandler;
import org.bco.cm.application.command.handler.UpdateCourseDescriptionHandler;
import org.bco.cm.application.command.handler.UpdateCourseHandler;
import org.bco.cm.application.command.handler.UpdateCourseModuleHandler;
import org.bco.cm.application.event.handler.CmEventBus;
import org.bco.cm.application.event.handler.CompletedQuizHandler;
import org.bco.cm.application.event.handler.CourseStartedHandler;
import org.bco.cm.application.event.handler.EnrolmentCanceledHandler;
import org.bco.cm.application.event.handler.EnrolmentCreatedHandler;
import org.bco.cm.application.event.handler.NewStudentRegisteredHandler;
import org.bco.cm.application.event.handler.NewTeacherRegisteredHandler;
import org.bco.cm.application.query.ReadOnlyStudentRegistry;
import org.bco.cm.application.query.ReadOnlyTeacherRegistry;
import org.bco.cm.application.query.ReadOnlyCourseCatalog;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.student.StudentRegistry;
import org.bco.cm.domain.teacher.TeacherRegistry;
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
import org.bco.cm.application.query.ReadOnlyCourseRegistry;
import org.bco.cm.application.query.ReadOnlyEnrolmentRegistry;
import org.bco.cm.domain.course.CourseRegistry;
import org.bco.cm.domain.enrolment.EnrolmentRegistry;
import org.bco.cm.infrastructure.persistence.hibernate.HibernateCourseRegistry;
import org.bco.cm.infrastructure.persistence.hibernate.HibernateEnrolmentRegistry;
import org.bco.cm.infrastructure.persistence.hibernate.HibernateReadOnlyCourseRegistry;
import org.bco.cm.infrastructure.persistence.hibernate.HibernateReadOnlyEnrolmentRegistry;
import org.bco.cm.api.access.AccessAuthorizer;
import org.bco.cm.api.rest.spring.AccountResource;
import org.bco.cm.api.rest.spring.StudentTasksResource;
import org.bco.cm.api.rest.spring.StudentsResource;
import org.bco.cm.api.rest.spring.TeachersResource;
import org.bco.cm.security.SecurityTokenManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Bean configuration.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Configuration
@ComponentScan(basePackages = "org.bco.cm")
@ImportResource({"spring-beans.cfg.xml"})
public class CmConfiguration 
{
    // Parameters.
    private final static String APP_ID = "98d3a11e-77d4-4aed-8d9c-ce785af8ceff";
    private final static String UMS_URL = "http://localhost:8013";
    
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
    
    @Bean
    @Primary
    CourseRegistry courseRegistry()
    {
        return new HibernateCourseRegistry();
    }
    
    @Bean
    @Primary
    ReadOnlyCourseRegistry readOnlyCourseRegistry()
    {
        return new HibernateReadOnlyCourseRegistry();
    }
    
    @Bean
    ReadOnlyEnrolmentRegistry readOnlyEnrolmentRegistry()
    {
        return new HibernateReadOnlyEnrolmentRegistry();
    }
    
    @Bean
    EnrolmentRegistry enrolmentRegistry()
    {
        return new HibernateEnrolmentRegistry();
    }
    
    
    // Application services
    
    @Bean
    AccountService accountService()
    {
        AccountService accountService = new AccountService();
        accountService.setUmsUrl(UMS_URL + "/users");
        return accountService;
    }
    
    
    // Facades
    
    @Bean
    CourseCatalogFacade courseCatalogFacade()
    {
        return new CourseCatalogFacade();
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
    
    @Bean
    CourseFacade courseFacade()
    {
        return new CourseFacade();
    }
    
    @Bean
    EnrolmentFacade enrolmentFacade()
    {
        return new EnrolmentFacade();
    }
    
    @Bean
    AccountFacade accountFacade()
    {
        return new AccountFacade();
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
    CancelEnrolmentHandler cancelEnrolmentHandler()
    {
        return new CancelEnrolmentHandler();
    }
    
    @Bean
    PostNewCourseHandler postNewCourseHandler()
    {
        return new PostNewCourseHandler();
    }
    
    @Bean
    UpdateCourseDescriptionHandler updateCourseDescriptionHandler()
    {
        return new UpdateCourseDescriptionHandler();
    }
    
    @Bean
    UpdateCourseHandler updateCourseHandler()
    {
        return new UpdateCourseHandler();
    }
    
    @Bean
    DeleteCourseDescriptionHandler deleteCourseDescriptionHandler()
    {
        return new DeleteCourseDescriptionHandler();
    }
    
    @Bean
    AddCourseModuleHandler addCourseModuleHandler()
    {
        return new AddCourseModuleHandler();
    }
    
    @Bean
    UpdateCourseModuleHandler updateCourseModuleHandler()
    {
        return new UpdateCourseModuleHandler();
    }
    
    @Bean 
    DeleteCourseModuleHandler deleteCourseModuleHandler()
    {
        return new DeleteCourseModuleHandler();
    }
    
    @Bean
    NewStudentRegisteredHandler newStudentRegisteredHandler()
    {
        NewStudentRegisteredHandler handler = new NewStudentRegisteredHandler();
        handler.setApplicationId(APP_ID);
        handler.setStudentRole("student");
        handler.setUmsUrl(UMS_URL + "/users");
        return handler;
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
    EnrolmentCanceledHandler enrolmentCanceledHandler()
    {
        return new EnrolmentCanceledHandler();
    }
    
    @Bean
    ActivateCourseHandler activateCourseHandler()
    {
        return new ActivateCourseHandler();
    }
    
    @Bean
    StartCourseHandler startCourseHandler()
    {
        return new StartCourseHandler();
    }
    
    @Bean 
    EndCourseHandler endCourseHandler()
    {
        return new EndCourseHandler();
    }
    
    @Bean
    DeleteCourseHandler deleteCourseHandler()
    {
        return new DeleteCourseHandler();
    }
    
    @Bean
    CourseStartedHandler courseStartedHandler()
    {
        return new CourseStartedHandler();
    }
    
    @Bean
    CompleteQuizHandler completeQuizHandler()
    {
        return new CompleteQuizHandler();
    }
    
    @Bean
    CompletedQuizHandler completedQuizHandler()
    {
        return new CompletedQuizHandler();
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
    
    @Bean
    AccessAuthorizer accessAuthorizer()
    {
        return new AccessAuthorizer();
    }
    
    @Bean
    SecurityTokenManager SecurityTokenManager()
    {
        return new SecurityTokenManager();
    }
    
    @Bean
    StudentsResource studentsResource()
    {
        return new StudentsResource();
    }
    
    @Bean
    StudentTasksResource studentTasksResource()
    {
        return new StudentTasksResource();
    }
    
    @Bean 
    AccountResource accountResource()
    {
        return new AccountResource();
    }
    
    @Bean
    TeachersResource teachersResource()
    {
        return new TeachersResource();
    }
}
