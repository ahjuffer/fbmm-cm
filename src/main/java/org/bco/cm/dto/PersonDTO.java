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
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * DTO for Person.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@MappedSuperclass
public class PersonDTO implements Serializable
{
    private String identifier_;
    private String firstName_;
    private String surname_;

    protected PersonDTO()
    {
        identifier_ = null;
        firstName_ = null;
        surname_ = null;
    }
    
    public void setIdentifier(String identifier)
    {
        identifier_ = identifier;
    }
    
    @Transient
    public String getIdentifier()
    {
        return identifier_;
    }
    
    public void setFirstName(String firstName)
    {
        firstName_ = firstName;
    }
    
    @Column(name = "first_name")
    public String getFirstName()
    {
        return firstName_;
    }
    
    public void setSurname(String surname)
    {
        surname_ = surname;       
    }

    @Column(name = "surname")
    public String getSurname()
    {
        return surname_;
    }
    
    /**
     * Returns full name, as "surname, firstName".
     * @return Full name.
     */
    @Transient
    public String getFullName()
    {
        return surname_ + ", " + firstName_;
    }
    
    /**
     * Append properties for the <code>toString</code> implementation of 
     * derived class.
     * @param s String
     */
    protected final void appendTo(StringBuilder s)
    {
        String newline = System.getProperty("line.separator");
        s.append("identifier - ").append(identifier_).append(newline);
        s.append("firstName - ").append(firstName_).append(newline);
        s.append("surname - ").append(surname_).append(newline);
        s.append("fullName - ").append(this.getFullName()).append(newline);
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
        if ( this.getClass() != other.getClass() ) {
            return false;
        }
        PersonDTO person = (PersonDTO)other;
        return identifier_.equals(person.getIdentifier());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.identifier_);
        hash = 53 * hash + Objects.hashCode(this.firstName_);
        hash = 53 * hash + Objects.hashCode(this.surname_);
        return hash;
    }
}
