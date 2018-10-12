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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.bco.cm.api.facade.CourseFacade;
import org.bco.cm.application.query.CourseSpecification;
import org.bco.cm.util.CourseDescriptionId;
import org.bco.cm.dto.CourseDTO;
/**
 *
 * @author ajuffer
 */
public class TestFindCourses {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger("org.bco.cm");
        try {
            AnnotationConfigApplicationContext context = 
                new AnnotationConfigApplicationContext();
            context.register(CmConfiguration.class);
            context.refresh();
            
            CourseFacade cf = context.getBean(CourseFacade.class);
            CourseDescriptionId courseDescriptionId =
                new CourseDescriptionId("12128b4e-938f-4196-b275-7279ec7610bd");
            CourseSpecification spec = new CourseSpecification();
            spec.setCourseDescriptionId(courseDescriptionId.stringValue());
            List<CourseDTO> courses = cf.getSpecified(spec);
            logger.info("Activated courses: " + courses);
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        }
    }
    
}
