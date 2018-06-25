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

import java.util.Objects;

/**
 * One possible outcome of a multiple choice question.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class Choice {

    private String phrase_;
    
    protected Choice()
    {
        phrase_ = null;
    }
    
    private void setPhrase(String phrase)
    {
        if ( phrase == null ) {
            throw new NullPointerException("Choice: A phrase must be provided.");
        }
        if ( phrase.isEmpty() ) {
            throw new IllegalArgumentException("Choice: A phrase must be provided.");
        }
        phrase_ = phrase;
    }
    
    /**
     * Returns phrase.
     * @return Phrase. Neither null nor empty.
     */
    public String getPhrase()
    {
        return phrase_;
    }
    
    /**
     * Returns a new choice.
     * @param phrase Choice value.
     * @return Choice.
     */
    public static Choice valueOf(String phrase)
    {
        Choice choice = new Choice();
        choice.setPhrase(phrase);
        return choice;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.phrase_);
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
        final Choice choice = (Choice)other;
        return phrase_.equals(choice.getPhrase());
    }
    
    /**
     * Returns string value.
     * @return String.
     */
    public String stringValue()
    {
        return phrase_;
    }
    
    @Override
    public String toString()
    {
        return this.stringValue();
    }
}
