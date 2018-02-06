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
import org.bco.cm.application.command.handler.RegisterNewStudentHandler;
import org.bco.cm.application.command.handler.CmCommandBus;
import org.bco.cm.application.command.handler.EnrolStudentHandler;
import org.bco.cm.application.command.handler.StartNewCourseHandler;
import org.bco.cm.application.event.handler.CmEventBus;
import org.bco.cm.application.event.handler.NewStudentRegisteredHandler;
import org.bco.cm.application.event.handler.StudentEnrolledInCourseHandler;
import org.bco.cm.domain.course.ClassRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Bean configuration.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Configuration
public class CmConfiguration {
    
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
    
    @Bean
    RegisterNewStudentHandler registerNewStudentHandler()
    {
        return new RegisterNewStudentHandler();
    }
    
    @Bean 
    EnrolStudentHandler enrolStudentHandler()
    {
        return new EnrolStudentHandler();
    }
    
    @Bean
    StartNewCourseHandler startNewCourseHandler()
    {
        return new StartNewCourseHandler();
    }
    
    @Bean
    CommandBus commandBus()
    {
        return new CmCommandBus();
    }
    
    @Bean
    NewStudentRegisteredHandler newStudentRegisteredHandler()
    {
        return new NewStudentRegisteredHandler();
    }
    
    @Bean
    StudentEnrolledInCourseHandler studentEnrolledInCourseHandler()
    {
        return new StudentEnrolledInCourseHandler();
    }
    
    @Bean 
    EventBus eventBus()
    {
        return new CmEventBus();
    }
    
    @Bean
    ClassRegister classRegister()
    {
        return new ClassRegister();
    }

}
