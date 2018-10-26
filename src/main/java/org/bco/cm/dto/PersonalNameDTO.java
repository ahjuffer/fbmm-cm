/*
 * The MIT License
 *
 * Copyright 2018 André H. Juffer, Triacle Biocomputing
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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;

/**
 * DTO for PersonalName.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Embeddable
@JsonInclude(Include.NON_NULL)
public class PersonalNameDTO implements Serializable, Comparable<PersonalNameDTO> {

    private String firstName_;
    private String middleNames_;
    private String surname_;
    
    public PersonalNameDTO()
    {
        firstName_ = null;
        middleNames_ = null;
        surname_ = null;        
    }
    
    public void setFirstName(String firstName)
    {
        firstName_ = firstName;
    }
    
    @Column( name = "first_name" )
    public String getFirstName()
    {
        return firstName_;
    }
    
    public void setMiddleNames(String middleNames)
    {
        middleNames_ = middleNames;
    }
    
    @Column( name = "middle_names" )
    public String getMiddleNames()
    {
        return middleNames_;
    }
    
    public void setSurname(String surname)
    {
        surname_ = surname;
    }
    
    @Column( name = "surname" )
    public String getSurname()
    {
        return surname_;
    }
    
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder().append("PersonalNameDTO : {").append(newline);
        s.append("firstName - ").append(firstName_).append(newline);
        s.append("middleNames - ").append(middleNames_).append(newline);
        s.append("surname - ").append(surname_).append(newline);
        s.append("fullName - ").append(this.getFullName()).append(newline);
        s.append("}");
        return s.toString();
    }

    /**
     * Compares surnames.
     * @param names Other names.
     * @return Result.
     */
    @Override
    public int compareTo(PersonalNameDTO names) 
    {
        return this.getSurname().compareTo(names.getSurname());
    }
    
    /**
     * Returns full name. This is of the form John F. Kennedy.
     * @return Full name.
     */
    @Transient
    public String getFullName()
    {
        if ( middleNames_ != null ) {
            return surname_ + ", " + firstName_ + " " + middleNames_;
        } else {
            return surname_ + ", " + firstName_;
        }
    }
}
