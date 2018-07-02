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

package org.bco.cm.domain.course;

import org.bco.cm.dto.AssignmentDTO;
import org.bco.cm.dto.ModuleItemDTO;
import org.bco.cm.dto.QuizDTO;

/**
 * Creates and adds module item to modules.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class ModuleItemFactory {
    
    private ModuleItemFactory()
    {        
    }
    
    /**
     * Adds module item to module.
     * @param spec Module item specification.
     * @param module Module to which new module item is added.
     * @throws IllegalArgumentException if spec is not a module item.
     */
    static void addModuleItem(ModuleItemDTO spec, Module module)
    {
        if ( spec.getClass().equals(QuizDTO.class) ) {
            Quiz quiz = ModuleItemFactory.createQuiz(spec);
            module.addQuiz(quiz);
        } else if ( spec.getClass().equals(AssignmentDTO.class) ) {
            Assignment assignment = ModuleItemFactory.createAssignment(spec);
            module.addAssignent(assignment);
        } else {
            throw new IllegalArgumentException(
                spec.getClass().getName() + ": Not a module item."
            );
        }
    }
    
    private static Quiz createQuiz(ModuleItemDTO spec)
    {
        QuizDTO dto = (QuizDTO)spec;
        return Quiz.valueOf(dto);
    }
    
    private static Assignment createAssignment(ModuleItemDTO spec)
    {
        AssignmentDTO dto = (AssignmentDTO)spec;
        return Assignment.valueOf(dto);
    }

}
