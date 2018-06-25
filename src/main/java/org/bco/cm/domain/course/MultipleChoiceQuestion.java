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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bco.cm.dto.MultipleChoiceQuestionDTO;

/**
 * A questions with multiple answer to choose from. Only one answer is correct.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class MultipleChoiceQuestion {

    private UUID id_;
    private Question question_;
    private List<Choice> choices_;
    private Choice answer_;
    
    protected MultipleChoiceQuestion()
    {
        question_ = null;
        choices_ = new ArrayList<>();
        answer_ = null;
    }
    
    private void setId(UUID id)
    {
        id_ = id;
    }
    
    /**
     * Returns identifier assigned or generated by repository.
     * @return Identifier.
     */
    protected UUID getId()
    {
        return id_;
    }
    
    private void setQuestion(Question question)
    {
        if ( question == null ) {
            throw new NullPointerException("Question must be provided.");
        }
        question_ = question;
    }
    
    /**
     * Returns question.
     * @return Question. Never null.
     */
    public Question getQuestion()
    {
        return question_;
    }
    
    private void setChoices(List<Choice> choices)
    {
        if ( choices == null ) {
            throw new NullPointerException("Choices must be provided.");
        }
        if ( choices.isEmpty() ) {
            throw new IllegalArgumentException("Choices must be provided.");
        }
        choices_ = choices;
        if ( answer_ != null ) {
            if ( !choices_.contains(answer_) ) {
                throw new IllegalArgumentException(
                    "Answer is not amonng possible choices."
                );
            }
        }
    }
    
    protected List<Choice> getChoices()
    {
        return choices_;
    }
    
    private void setAnswer(Choice answer)
    {
        if ( answer == null ) {
            throw new NullPointerException("An answer must be provided.");
        }
        if ( !choices_.isEmpty() ) {
            if ( !choices_.contains(answer) ) {
                throw new IllegalArgumentException(
                    "Answer is not amonng possible choices."
                );
            }
        }
        answer_ = answer;
    }
    
    /**
     * Returns correct answer to question.
     * @return Answer. Never null. Is one of possible choices.
     */
    public Choice getAnswer()
    {
        return answer_;
    }

    /**
     * Returns new multiple choice question.
     * @param spec New multiple choice question specification. Must provide choices
     * and correct answer.
     * @return Multiple choice question.
     */
    public static MultipleChoiceQuestion valueOf(MultipleChoiceQuestionDTO spec)
    {
        MultipleChoiceQuestion mcq = new MultipleChoiceQuestion();
        Question question = Question.valueOf(spec.getQuestion());
        mcq.setQuestion(question);
        List<Choice> choices = new ArrayList<>();
        for (String c : spec.getChoices()) {
            Choice choice = Choice.valueOf(c);
            choices.add(choice);
        }
        mcq.setChoices(choices);
        Choice answer = Choice.valueOf(spec.getAnswer());
        mcq.setAnswer(answer);
        return mcq;
    }
    
    /**
     * Is given choice the right answer to the question?
     * @param answer Choice.
     * @return Result.
     */
    public boolean isAnswer(Choice answer)
    {
        return answer_.equals(answer);
    }
        
}
