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
import javax.persistence.Embedded;
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
    private PersonalNameDTO names_;
    private String emailAddress_;

    protected PersonDTO()
    {
        identifier_ = null;
        names_ = null;
        emailAddress_ = null;
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
    
    public void setNames(PersonalNameDTO names)
    {
        names_ = names;
    }
    
    @Embedded
    public PersonalNameDTO getNames()
    {
        return names_;
    }
    
    public void setEmailAddress(String emailAddress)
    {
        emailAddress_ = emailAddress;
    }
    
    @Column( name = "email_address" )
    public String getEmailAddress()
    {
        return emailAddress_;
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
        if ( names_ != null ) {
            s.append("names - ").append(names_).append(newline);
        }
        s.append("emailAddress - ").append(emailAddress_).append(newline);
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
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.identifier_);
        hash = 29 * hash + Objects.hashCode(this.names_);
        hash = 29 * hash + Objects.hashCode(this.emailAddress_);
        return hash;
    }

}
