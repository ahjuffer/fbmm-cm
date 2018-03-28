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

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.bco.cm.dto.AssignmentDTO;
import org.bco.cm.dto.LearningPathDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.dto.OnlineMaterialDTO;
import org.bco.cm.dto.QuizDTO;
import org.hibernate.annotations.NaturalId;

/**
 * Course module. A valid module must consist of a non-empty learning path, and 
 * may include an assignment and/or quiz.
 * @author André H. Juffer, Biocenter Oulu
 */
@Entity( name = "Module" )
@Table ( name = "modules")
public class Module implements Serializable {
    
    private UUID id_;
    private int moduleId_;
    private String name_;
    private LearningPath learningPath_;
    private final Assignment assignment_;
    private final Quiz quiz_;
    private Module next_;
    
    protected Module()
    {
        moduleId_ = -1;
        name_ = null;
        learningPath_ = null;
        assignment_ = null;
        quiz_ = null;
        next_ = null;
    }
    
    private void setId(UUID id)
    {
        id_ = id;
    }
    
    @Id
    @GeneratedValue
    protected UUID getId()
    {
        return id_;
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
    @Column( name = "module_id" )
    @NaturalId
    public int getModuleId()
    {
        return moduleId_;
    }
    
    private void setName(String name)
    {
        if ( name == null ) {
            throw new NullPointerException("Missing module name.");
        }
        if ( name.isEmpty() ) {
            throw new IllegalArgumentException("Missing module name.");            
        }
        name_ = name;
    }     
    
    @Column ( name = "name" )
    protected String getName()
    {
        return name_;
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
     * Returns learning path.
     * @return Learning path. Returns null if not yet assigned.
     */
    @Transient
    protected LearningPath getLearningPath()
    {
        return learningPath_;
    }
    
    /**
     * Sets the next module.
     * @param next Next module.
     */
    void setNext(Module next)
    {
        next_ = next;
    }
    
    @OneToOne
    @JoinColumn(name = "next_module_id")
    protected Module getNext()
    {
        return next_;
    }
    
    /**
     * Does this module already include a learning path?
     * @return Result.
     */
    boolean hasLearningPath()
    {
        if ( learningPath_ != null ) {
            return learningPath_.isEmpty() == false;
        }
        return false;
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
     * @param spec New module specification. Must include module name. May include
     * learning path, assignment and quiz.
     * @return New module.
     */
    static Module valueOf(int moduleId, ModuleDTO spec)
    {
        Module module = new Module();
        module.setModuleId(moduleId);
        module.setName(spec.getName());
        /*
        LearningPath learningPath;
        if ( spec.getLearningPath() != null ) {
            learningPath = LearningPath.valueOf(spec.getLearningPath());
        } else {
            learningPath = LearningPath.empty();
        }
        module.setLearningPath(learningPath);
        */
        
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
            return this;
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
        dto.setName(name_);
        if ( this.hasAssignment() ) {
            AssignmentDTO assignment = assignment_.toDTO();
            dto.setAssignment(assignment);
        }
        if ( this.hasQuiz() ) {
            QuizDTO quiz = new QuizDTO();
            dto.setQuiz(quiz);
        }
        if ( this.hasNext() ) {
            dto.setNextModule(next_.toDTO());
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
        modules.forEach((module) -> {
            ModuleDTO dto = module.toDTO();
            dtos.add(dto);
        });
        return dtos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.moduleId_;
        hash = 41 * hash + Objects.hashCode(this.name_);
        return hash;
    }
    
    /**
     * Comparing two modules has meaning only in context of the owning
     * course.
     * @param other Other object.
     * @return Result.
     */
    @Override
    public boolean equals(Object other)
    {
        if ( other == null ) {
            return false;
        }
        if ( this == other ) {
            return true;
        }
        if ( this.getClass() != other.getClass() ) {
            return false;
        }
        Module module = (Module)other;
        return ( moduleId_ == module.getModuleId() );
    }
        
}
