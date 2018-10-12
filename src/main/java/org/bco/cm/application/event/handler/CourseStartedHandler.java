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

package org.bco.cm.application.event.handler;

import com.tribc.ddd.domain.event.EventHandler;
import java.util.Collection;
import java.util.HashSet;
import org.bco.cm.application.query.ReadOnlyCourseRegistry;
import org.bco.cm.application.query.ReadOnlyStudentRegistry;
import org.bco.cm.util.CourseId;
import org.bco.cm.domain.course.event.CourseStarted;
import org.bco.cm.util.StudentId;
import org.bco.cm.dto.CourseDTO;
import org.bco.cm.dto.StudentDTO;
import org.bco.cm.dto.StudentMonitorDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 * Notifies all enrolled students.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class CourseStartedHandler 
    extends EventHandler<CourseStarted> 
    implements Runnable {
    
    @Autowired
    private ReadOnlyCourseRegistry readOnlyCourseRegistry_;
    
    @Autowired
    private ReadOnlyStudentRegistry readOnlyStudentRegistry_;
    
    @Autowired
    MailSender mailSender_;
    
    @Autowired
    SimpleMailMessage templateMessage_;

    private CourseDTO course_;
    private Collection<StudentDTO> students_;
    
    @Override
    public void handle(CourseStarted event) 
    {
        CourseId courseId = event.getCourseId();
        course_ = readOnlyCourseRegistry_.getOne(courseId);
        Collection<StudentMonitorDTO> monitors = course_.getStudentMonitors();           
        students_ = this.getStudents(monitors);
        Thread thread = new Thread(this);
        thread.start();
   }
    
    private Collection<StudentDTO> getStudents(Collection<StudentMonitorDTO> monitors)
    {
        Collection<StudentDTO> students = new HashSet<>();
        monitors.forEach(monitor -> {
            StudentId studentId = new StudentId(monitor.getStudentId());
            StudentDTO student = readOnlyStudentRegistry_.getOne(studentId);
            students.add(student);
        });
        return students;
    }
    
    private Collection<String> getEmailAddresses(Collection<StudentDTO> students)
    {
        Collection<String> emailAddresses = new HashSet<>();
        students.forEach(student -> {
            emailAddresses.add(student.getEmailAddress());
        });
        return emailAddresses;
    }
    
    @Override
    public void run() 
    {
        try {
            String title = course_.getTitle();
            String newline = System.getProperty("line.separator");
            students_.forEach(student -> {
                SimpleMailMessage msg = new SimpleMailMessage(templateMessage_);
                msg.setTo(student.getEmailAddress());
                StringBuilder text = new StringBuilder();
                text.append("Dear ").append(student.getFirstName()).append(",").append(newline);
                text.append("The course '").append(title).append("'").append(" has started.").append(newline);
                text.append("You now can begin with the first course module at crapp.oulu.fi.").append(newline);
                msg.setText(text.toString());
                mailSender_.send(msg);
            });
        }
        catch (MailException exception) {
            throw new IllegalStateException(
                "Cound not notify students. " + exception.getMessage(),
                exception
            );
        }
    }

}
