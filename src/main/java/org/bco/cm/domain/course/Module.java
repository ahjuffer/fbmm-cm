/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer, Biocenter Oulu.
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

import java.util.Collection;
import java.util.HashSet;
import org.bco.cm.dto.AssignmentDTO;
import org.bco.cm.dto.LearningPathDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.dto.OnlineMaterialDTO;
import org.bco.cm.dto.QuizDTO;

/**
 * Course module. A valid module must consist of a non-empty learning path, and 
 * may include an assignment and/or quiz.
 * @author André H. Juffer, Biocenter Oulu
 */
public class Module {
    
    private int moduleId_;
    private LearningPath learningPath_;
    private final Assignment assignment_;
    private final Quiz quiz_;
    private Module next_;
    
    private Module()
    {
        moduleId_ = -1;
        learningPath_ = null;
        assignment_ = null;
        quiz_ = null;
        next_ = null;
    }

    /**
     * Sets module identifier. Its value is provided by the owning course.
     * @param id Identifier.
     */
    void setModuleId(int id)
    {
        moduleId_ = id;
    }
    
    /**
     * Returns module identifier.
     * @return Identifier. It is guaranteed unique only in context of the 
     * owning course.
     */
    public int getModuleId()
    {
        return moduleId_;
    }
    
    private void setLearningPath(LearningPath learningPath)
    {
        if ( learningPath == null ) {
            throw new NullPointerException(
                "A course module must always include a learning path."
            );
        }
        learningPath_ = learningPath;
    }
    
    /**
     * Sets the next module.
     * @param next Next module.
     */
    void setNext(Module next)
    {
        next_ = next;
    }
    
    private Module getNext()
    {
        return next_;
    }
    
    /**
     * Were online material added to this module's learning path.
     * @return Result.
     */
    boolean isLearningPathEmpty()
    {
        return learningPath_.isEmpty();
    }
    
    /**
     * Does this module include an assignment.
     * @return Result.
     */
    public boolean hasAssignment()
    {
        return assignment_ != null;
    }
    
    /**
     * Does this module include a quiz?
     * @return Result.
     */
    public boolean hasQuiz()
    {
        return quiz_ != null;
    }
    
    /**
     * Adds new online material.
     * @param spec New online material specification.
     */
    void addOnlineMaterialToLearningPath(OnlineMaterialDTO spec)
    {
        learningPath_.addOnlineMaterial(spec);
    }
    
    /**
     * Creates new module.
     * @param moduleId New module identifier.
     * @param spec New module specification.
     * @return New module.
     */
    static Module createNew(int moduleId, ModuleDTO spec)
    {
        Module module = new Module();
        module.setModuleId(moduleId);
        LearningPath learningPath;
        if ( spec.getLearningPath() != null ) {
            learningPath = LearningPath.valueOf(spec.getLearningPath());
        } else {
            learningPath = LearningPath.empty();
        }
        module.setLearningPath(learningPath);
        
        // Add assignment and/or quiz.
        
        return module;
    }
    
    /**
     * Another module?
     * @return Result.
     */
    boolean hasNext()
    {
        return next_ != null;
    }
    
    /**
     * Returns next module. If there is no next module, this module is returned.
     * @return Module.
     */
    Module toNext()
    {
        if ( this.hasNext() ) {
            return this.getNext();
        } else {
            return next_;
        }
    }
    
    /**
     * Returns a data transfer object.
     * @return DTO.
     */
    ModuleDTO toDTO()
    {
        ModuleDTO dto = new ModuleDTO();
        LearningPathDTO learningPath = learningPath_.toDTO();
        dto.setLearningPath(learningPath);
        dto.setModuleId(this.getModuleId());
        if ( this.hasAssignment() ) {
            AssignmentDTO assignment = assignment_.toDTO();
            dto.setAssignment(assignment);
        }
        if ( this.hasQuiz() ) {
            QuizDTO quiz = new QuizDTO();
            dto.setQuiz(quiz);
        }
        if ( this.hasNext() ) {
            dto.setNextModuleId(next_.getModuleId());
        }
        return dto;
    }
    
    /**
     * Returns list data transfer objects 
     * @param modules Modules.
     * @return List.
     */
    static Collection<ModuleDTO> toDTOs(Collection<Module> modules)
    {
        Collection<ModuleDTO> dtos = new HashSet<>();        
        for (Module module : modules) {
            ModuleDTO dto = module.toDTO();
            dtos.add(dto);
        }
        return dtos;
    }
        
}
