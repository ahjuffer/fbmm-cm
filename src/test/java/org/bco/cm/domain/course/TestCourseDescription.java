package org.bco.cm.domain.course;

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


import org.bco.cm.dto.CourseDescriptionDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.dto.TeacherDTO;
import org.jboss.logging.Logger;

/**
 *
 * @author ajuffer
 */
public class TestCourseDescription {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        TeacherId teacherId = new TeacherId("1");
        TeacherDTO tspec = new TeacherDTO();
        tspec.setFirstName("André");
        tspec.setSurname("Juffer");
        Teacher teacher = Teacher.valueOf(teacherId, tspec);
        
        // Create a course.
        CourseDescriptionDTO sspec = new CourseDescriptionDTO();
        CourseId courseId = new CourseId("123456");
        // Leaving out any of the next two statements causes an exception.
        sspec.setSummary("This is a test description.");
        sspec.setTitle("This is a test title.");
        CourseDescription course = CourseDescription.valueOf(teacher, courseId, sspec);
        Logger.getLogger("TestCourseDescription")
              .info("Created course: " + course.toDTO());
        Logger.getLogger("TestCourseDescription").info("--------------------------------");
        
        // Add module #1.
        ModuleDTO m1 = new ModuleDTO();
        m1.setName("MODULE #1");
        course.addModule(m1);
        Logger.getLogger("TestCourseDescription")
              .info("Update #1: " + course.toDTO());
        Logger.getLogger("TestCourseDescription").info("--------------------------------");
        
        // Add module #2.
        ModuleDTO m2 = new ModuleDTO();
        m2.setName("MODULE #2");
        course.addModule(m2);
        Logger.getLogger("TestCourseDescription")
              .info("Update #2: " + course.toDTO());
        Logger.getLogger("TestCourseDescription").info("--------------------------------");
        
        // Add module #3.
        ModuleDTO m3 = new ModuleDTO();
        m3.setName("MODULE #3");
        course.addModule(m3);
        Logger.getLogger("TestCourseDescription")
              .info("Update #3: " + course.toDTO());
        Logger.getLogger("TestCourseDescription").info("--------------------------------");
    }
    
}
