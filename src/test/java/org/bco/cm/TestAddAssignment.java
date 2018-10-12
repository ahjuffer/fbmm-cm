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

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bco.cm.api.facade.CourseCatalogFacade;
import org.bco.cm.util.CourseDescriptionId;
import org.bco.cm.util.TeacherId;
import org.bco.cm.dto.AssignmentDTO;
import org.bco.cm.dto.ChoiceDTO;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.dto.ModuleItemDTO;
import org.bco.cm.dto.MultipleChoiceQuestionDTO;
import org.bco.cm.dto.QuizDTO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 *
 * @author ajuffer
 */
public class TestAddAssignment {

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
            
            CourseDescriptionId courseId = 
                new CourseDescriptionId("b71b4635-1eb5-40bf-b973-de5492b1a135");
            
            CourseDescriptionDTO spec = ccf.getCourse(courseId);
            List<ModuleDTO> modules = spec.getModules();
            ModuleDTO module = modules.get(0);
            AssignmentDTO assignment = new AssignmentDTO();
            assignment.setTitle("World Cup simulation.");
            assignment.setContent("Simulate the world cup.");
            module.addModuleItem(assignment);

            TeacherId teacherId = new TeacherId("123");
            ccf.updateCourseModule(courseId, teacherId, module.getModuleId(), module);
            
            CourseDescriptionDTO updated = ccf.getCourse(courseId);
            logger.info("Updated course : " + updated);
        } catch (Exception exception) {
            logger.error(exception.getMessage(), exception);
        } 
    }
    
}
