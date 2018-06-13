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

package org.bco.cm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.NaturalId;
import javax.persistence.CascadeType;

/**
 * DTO for Module.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Entity( name = "ModuleDTO" )
@Table ( name = "modules" )
public class ModuleDTO implements Serializable {
    
    private UUID id_;
    private int moduleId_;
    private String name_;
    private LearningPathDTO learningPath_;
    private AssignmentDTO assignment_;
    private QuizDTO quiz_;
    
    private CourseDescriptionDTO courseDescription_;
    private CourseDTO course_;
    
    private String courseDescriptionId_;
    private String courseId_;
    
    public ModuleDTO()
    {
        moduleId_ = -1;
        name_ = null;
        learningPath_ = null;
        assignment_ = null;
        quiz_ = null;
        courseDescription_ = null;
        course_ = null;
        courseDescriptionId_ = null;
        courseId_ = null;
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
     * Sets the identifier value of the given module.
     * @param id Identifier value.
     */
    public void setModuleId(int id)
    {
        moduleId_ = id;
    }
    
    /**
     * Returns the identifier value of the given module.
     * @return Identifier value. A value of -1 indicates that it was not (yet) 
     * specified.
     */
    @Column( name = "module_id" )
    @NaturalId
    public int getModuleId()
    {
        return moduleId_;
    }
    
    public void setName(String name)
    {
        name_ = name;
    }
    
    @Column ( name = "name" )
    public String getName()
    {
        return name_;
    }
    
    public void setLearningPath(LearningPathDTO learningPath)
    {
        learningPath_ = learningPath;
        if ( learningPath_ != null ) {
            learningPath_.setModule(this);
        }
    }
    
    @OneToOne(
        mappedBy = "module", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true
    )
    public LearningPathDTO getLearningPath()
    {
        return learningPath_;
    }
    
    public void setAssignment(AssignmentDTO assignment)
    {
        assignment_ = assignment;
    }
    
    @Transient
    public AssignmentDTO getAssignment()
    {
        return assignment_;
    }
    
    public void setQuiz(QuizDTO quiz)
    {
        quiz_ = quiz;
    }
    
    @Transient
    public QuizDTO getQuiz()
    {
        return quiz_;
    }    
        
    public void setCourseDescriptionId(String id)
    {
        courseDescriptionId_ = id;
    }

    @Transient
    @JsonIgnore
    public String getCourseDescriptionId()
    {
        if ( courseDescription_ != null ) {
            return courseDescription_.getCourseDescriptionId();
        } else {
            return courseDescriptionId_;
        }
    }
    
    public void setCourseDescription(CourseDescriptionDTO courseDescription)
    {
        courseDescription_ = courseDescription;
    }
    
    @ManyToOne()
    @JoinColumn( name="course_description_id" )
    @JsonIgnore
    public CourseDescriptionDTO getCourseDescription()
    {
        return courseDescription_;
    }
    
    public void setCourseId(String courseId)
    {
        courseId_ = courseId;
    }
    
    @Transient
    @JsonIgnore
    public String getCourseId()
    {
        if ( course_ != null ) {
            return course_.getCourseId();
        } else {
            return courseId_;
        }
    }
    
    public void setCourse(CourseDTO course)
    {
        course_ = course;
    }
    
    @ManyToOne()
    @JoinColumn( name="course_id" )
    @JsonIgnore
    public CourseDTO getCourse()
    {
        return course_;
    }
    
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("ModuleDTO : {").append(newline);
        s.append("id - ").append(id_).append(newline);
        s.append("moduleId - ").append(moduleId_).append(newline);
        s.append("name - ").append(name_).append(newline);
        if ( learningPath_ != null ) {
            s.append("learningPath - ").append(learningPath_).append(newline);
        }
        if ( assignment_ != null ) {
            s.append("assignment - ").append(assignment_).append(newline);
        }
        if ( quiz_ != null ) {
            s.append("quiz - ").append(quiz_).append(newline);
        }
        s.append("courseDescriptionId - ").append(this.getCourseDescriptionId())
                                          .append(newline);
        s.append("courseId - ").append(this.getCourseId()).append(newline);
        s.append("}");
        return s.toString();
    }
}
