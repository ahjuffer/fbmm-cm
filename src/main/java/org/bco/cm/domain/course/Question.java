/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer, Biocenter Oulu
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

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * A sentence, phrase, or gesture that seeks information through a reply.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Embeddable
public class Question {
    
    private String phrase_;
    
    private Question()
    {
        phrase_ = null;
    }
    
    private void setPhrase(String phrase)
    {
        if ( phrase == null ) {
            throw new NullPointerException(
                "Question: A phrase for a question must be provided."
            );
        }
        if ( phrase.isEmpty() ) {
            throw new IllegalArgumentException(
                "Question: A phrase for a question must be provided."
            );
        }
        phrase_ = phrase;
    }
    
    /**
     * Returns phrase.
     * @return Phrase. Neither null nor empty.
     */
    @Column( name = "question" )
    public String getPhrase()
    {
        return phrase_;
    }
    
    /**
     * Formulates new question.
     * @param phrase Question.
     * @return New question.
     */
    public static Question valueOf(String phrase)
    {
        Question question = new Question();
        question.setPhrase(phrase);
        return question;                
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.phrase_);
        return hash;
    }
    
    @Override
    public boolean equals(Object other)
    {
        if ( other == null ) {
            return false;
        }
        if ( this == other ) {
            return true;
        }
        if ( !(this.getClass().equals(other.getClass())) ) {
            return false;
        }
        final Question question = (Question)other;
        return phrase_.equals(question.getPhrase());
    }

}
