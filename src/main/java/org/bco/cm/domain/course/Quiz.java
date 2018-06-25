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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bco.cm.dto.MultipleChoiceQuestionDTO;
import org.bco.cm.dto.QuizDTO;

/**
 * List of “quick” multiple choice questions designed to test knowledge.
 * @author André H. Juffer, Biocenter Oulu
 */
public class Quiz {
    
    private UUID id_;
    private List<MultipleChoiceQuestion> questions_;
    
    protected Quiz()
    {
        id_ = null;
        questions_ = new ArrayList<>();
    }
    
    private void setId(UUID id)
    {
        id_ = id;
    }
    
    protected UUID getId()
    {
        return id_;
    }
    
    private void setQuestions(List<MultipleChoiceQuestion> questions)
    {
        if ( questions == null ) {
            throw new NullPointerException("Quiz: Questions must be provided.");
        }
        if ( questions.isEmpty() ) {
            throw new IllegalArgumentException(
                "Quiz: List of questions must not be empty."
            );
        }
        questions_ = questions;
    }
    
    protected List<MultipleChoiceQuestion> getQuestions()
    {
        return questions_;
    }
    
    /**
     * Create a new quiz
     * @param spec New quiz specification. Must provide questions.
     * @return Quiz.
     */
    public static Quiz valueOf(QuizDTO spec)
    {
        Quiz quiz = new Quiz();
        List<MultipleChoiceQuestion> questions = new ArrayList<>();
        spec.getQuestions().forEach(q -> {
            MultipleChoiceQuestion question = MultipleChoiceQuestion.valueOf(q);
            questions.add(question);
        });
        quiz.setQuestions(questions);
        return quiz;
    }
    
    /**
     * Returns data transfer object.
     * @return DTO.
     */
    public QuizDTO toDTO()
    {
        QuizDTO dto = new QuizDTO();
        List<MultipleChoiceQuestionDTO> questions = new ArrayList<>();
        questions_.forEach((question) -> {
            MultipleChoiceQuestionDTO q = question.toDTO();            
            questions.add(q);
        });
        dto.setQuestions(questions);
        return dto;
    }
}
