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

package org.bco.cm.application.command.handler;

import com.tribc.cqrs.domain.command.CommandBus;
import org.bco.cm.application.command.ActivateCourse;
import org.bco.cm.application.command.AddCourseModule;
import org.bco.cm.application.command.DeleteCourse;
import org.bco.cm.application.command.DeleteCourseModule;
import org.bco.cm.application.command.RegisterNewStudent;
import org.bco.cm.application.command.RegisterNewTeacher;
import org.bco.cm.application.command.EnrolStudent;
import org.bco.cm.application.command.PostNewCourse;
import org.bco.cm.application.command.StartCourse;
import org.bco.cm.application.command.UpdateCourse;
import org.bco.cm.application.command.UpdateCourseModule;
import org.bco.cm.application.command.CancelEnrolment;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Simple command bus for matching commands to command handlers.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class CmCommandBus extends CommandBus {    
    
    @Autowired
    public void setRegisterNewStudentHandler(RegisterNewStudentHandler handler)
    {
        this.setHandler(RegisterNewStudent.class, handler);
    }
    
    @Autowired
    public void setRegisterNewTeacherHandler(RegisterNewTeacherHandler handler)
    {
        this.setHandler(RegisterNewTeacher.class, handler);
    }
    
    @Autowired
    public void setEnrolStudentHandler(EnrolStudentHandler handler)
    {
        this.setHandler(EnrolStudent.class, handler);
    }
    
    @Autowired
    public void setPostNewCourseHandler(PostNewCourseHandler handler)
    {
        this.setHandler(PostNewCourse.class, handler);
    }
    
    @Autowired
    public void setAddCourseModuleHandler(AddCourseModuleHandler handler)
    {
        this.setHandler(AddCourseModule.class, handler);
    }
    
    @Autowired
    public void setUpdateCourseHandler(UpdateCourseHandler handler)
    {
        this.setHandler(UpdateCourse.class, handler);
    }
    
    @Autowired
    public void setDeleteCourse(DeleteCourseHandler handler)
    {
        this.setHandler(DeleteCourse.class, handler);
    }
    
    @Autowired
    public void setUpdateCourseModuleHandler(UpdateCourseModuleHandler handler)
    {
        this.setHandler(UpdateCourseModule.class, handler);
    }
    
    @Autowired
    public void setDeleteCourseModuleHandler(DeleteCourseModuleHandler handler)
    {
        this.setHandler(DeleteCourseModule.class, handler);
    }
    
    @Autowired
    public void setActivateCourseHandler(ActivateCourseHandler handler)
    {
        this.setHandler(ActivateCourse.class, handler);
    }
    
    @Autowired
    public void setStartCourseHandler(StartCourseHandler handler)
    {
        this.setHandler(StartCourse.class, handler);
    }
    
    @Autowired
    public void setCancelEnrolmentHandler(CancelEnrolmentHandler handler)
    {
        this.setHandler(CancelEnrolment.class, handler);
    }
    
    private void setHandler(Class clazz, CmCommandHandler handler)
    {
        this.match(clazz, handler);
    }

}
