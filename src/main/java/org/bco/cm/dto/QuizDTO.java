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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;


/**
 * DTO for Quiz.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Entity( name = "QuizDTO" )
@DiscriminatorValue( value = "Quiz" )
public class QuizDTO extends ModuleItemDTO implements Serializable {
    
    private List<MultipleChoiceQuestionDTO> questions_;
    
    public QuizDTO()
    {
        questions_ = new ArrayList<>();
    }
    
    public void setQuestions(List<MultipleChoiceQuestionDTO> questions)
    {
        questions_ = questions;
    }
    
    @OneToMany(
        mappedBy = "parentQuiz",
        cascade = CascadeType.ALL, 
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )    
    public List<MultipleChoiceQuestionDTO> getQuestions()
    {
        return questions_;
    }
    
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("QuizDTO: {").append(newline);
        s.append(super.toString()).append(newline);
        s.append("questions - ").append(questions_).append(newline);
        s.append("}");
        return s.toString();
    }
    
    private void setName(String name)
    {
        // Ignore.
    }
        
    @Override
    @Transient
    public String getName()
    {
        return "quiz";
    }
}
