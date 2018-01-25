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

/**
 * Data transfer object for a module.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class ModuleDTO {
    
    private int moduleId_;
    private LearningPathDTO learningPath_;
    private AssignmentDTO assignment_;
    private QuizDTO quiz_;
    private int nextModuleId_;
    
    public ModuleDTO()
    {
        moduleId_ = -1;
        learningPath_ = null;
        assignment_ = null;
        quiz_ = null;
        nextModuleId_ = -1;
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
    public int getModuleId()
    {
        return moduleId_;
    }
    
    public void setLearningPath(LearningPathDTO learningPath)
    {
        learningPath_ = learningPath;
    }
    
    public LearningPathDTO getLearningPath()
    {
        return learningPath_;
    }
    
    public void setAssignment(AssignmentDTO assignment)
    {
        assignment_ = assignment;
    }
    
    public AssignmentDTO getAssignment()
    {
        return assignment_;
    }
    
    public void setQuiz(QuizDTO quiz)
    {
        quiz_ = quiz;
    }
    
    public QuizDTO getQuiz()
    {
        return quiz_;
    }
    
    /**
     * Sets the module identifier value of the module following the given module.
     * @param nextModuleId Identifier value. A value of -1 indicates that it 
     * was not (yet) specified.
     */
    public void setNextModuleId(int nextModuleId)
    {
        nextModuleId_ = nextModuleId;
    }
    
    /**
     * Returns the module identifier value of the module following the given module.
     * @return Identifier value. 
     */
    public int getNextModuleId()
    {
        return nextModuleId_;
    }
    
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("ModuleDTO : {").append(newline);
        if ( learningPath_ != null ) {
            s.append("learningPath - ").append(learningPath_).append(newline);
        }
        if ( assignment_ != null ) {
            s.append("assignment - ").append(assignment_).append(newline);
        }
        if ( quiz_ != null ) {
            s.append("quiz - ").append(quiz_).append(newline);
        }
        s.append("nextModuleId - ").append(nextModuleId_).append(newline);
        s.append("}");
        return s.toString();
    }
}
