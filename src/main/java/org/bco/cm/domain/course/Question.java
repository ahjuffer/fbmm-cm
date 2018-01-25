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

/**
 * A sentence, phrase, or gesture that seeks information through a reply.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class Question {
    
    private String phrase_;
    
    private Question()
    {
        phrase_ = null;
    }
    
    private void setPhrase(String phrase)
    {
        phrase_ = phrase;
    }
    
    /**
     * Returns phrase.
     * @return Phrase.
     */
    String getPhrase()
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
        if ( phrase == null ) {
            throw new NullPointerException("A phrase for a question must be provided.");
        }
        if ( phrase.isEmpty() ) {
            throw new IllegalArgumentException("A phrase for a question must be provided.");
        }
        Question question = new Question();
        question.setPhrase(phrase);
        return question;                
    }

}