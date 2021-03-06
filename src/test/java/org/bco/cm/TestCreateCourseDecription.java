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

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bco.cm.api.facade.CourseCatalogFacade;
import org.bco.cm.domain.course.CourseDescriptionId;
import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author ajuffer
 */
public class TestCreateCourseDecription {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger();
        try {
            AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext();
            context.register(CmConfiguration.class);
            context.refresh();
            
            CourseCatalogFacade ccf = context.getBean(CourseCatalogFacade.class);
            
            CourseDescriptionDTO spec = new CourseDescriptionDTO();
            spec.setSummary("This is the summary.");
            spec.setTitle("This is the title");
            TeacherId teacherId = new TeacherId("123");
            CourseDescriptionId courseId = ccf.generate();
            ccf.postNewCourse(courseId, teacherId, spec);
            
            CourseDescriptionDTO created = ccf.getCourse(courseId);
            logger.info("Newly creaed course : " + created);
            
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
    }
    
}
