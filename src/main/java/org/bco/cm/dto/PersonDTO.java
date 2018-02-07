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
 * DTO for Person.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class PersonDTO 
{
    private String identifier_;
    private String firstName_;
    private String surname_;

    public void setIdentifier(String identifier)
    {
        identifier_ = identifier;
    }
    
    public String getIdentifier()
    {
        return identifier_;
    }
    
    public void setFirstName(String firstName)
    {
        firstName_ = firstName;
    }
    
    public String getFirstName()
    {
        return firstName_;
    }
    
    public void setSurname(String surname)
    {
        surname_ = surname;       
    }
    
    public String getSurname()
    {
        return surname_;
    }
    
    /**
     * Returns full name, as "surname, firstName".
     * @return Full name.
     */
    public String getFullName()
    {
        return surname_ + ", " + firstName_;
    }
    
    /**
     * Append properties for the <code>toString</code> implementation of 
     * derived class.
     * @param s String
     */
    protected final void appendToString(StringBuilder s)
    {
        String newline = System.getProperty("line.separator");
        s.append("identifier - ").append(identifier_).append(newline);
        s.append("firstName - ").append(firstName_).append(newline);
        s.append("surname - ").append(surname_).append(newline);
    }
}
