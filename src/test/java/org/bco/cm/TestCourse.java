package org.bco.cm;

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


import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.Teacher;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.dto.CourseDTO;
import org.bco.cm.dto.LearningPathDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.dto.OnlineMaterialDTO;
import org.jboss.logging.Logger;

/**
 *
 * @author ajuffer
 */
public class TestCourse {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        TeacherId teacherId = new TeacherId("qwerty");
        Teacher teacher = Teacher.create(teacherId);
        
        // Create a course.
        CourseDTO spec = new CourseDTO();
        CourseId courseId = new CourseId("123456");
        // Leaving out any of the next three statements causes an exception.
        spec.setDescription("This is a test description.");
        spec.setTitle("This is a test title.");
        spec.setObjective("This is an objective.");
        Course course = Course.start(teacher, courseId, spec);
        Logger.getLogger(TestCourse.class).info("New coursed: " + course.toDTO());
        
        // Course cannot begin if no modules were added.
        //course.begin();
        
        // Create a module.
        ModuleDTO module = new ModuleDTO();
        LearningPathDTO lp = new LearningPathDTO();
        OnlineMaterialDTO material = new OnlineMaterialDTO();
        material.setMaterialType("reading");
        material.setObjective("This is a test objective.");
        material.setResource("Http://www.example.com/something");
        // Leaving out next statement causes an exception.
        lp.addOnlineMaterial(material);
        module.setLearningPath(lp);
        module.setName("Module name");
        course.addModule(module);
        Logger.getLogger(TestCourse.class).info("Updated course: " + course.toDTO());
        
        // Next statement may cause exception.
        course.begin();
        Logger.getLogger(TestCourse.class).info("Course ongoing? " + course.isOngoing());
        Logger.getLogger(TestCourse.class).info("Ongoing course: " + course.toDTO());
        
    }
    
}
