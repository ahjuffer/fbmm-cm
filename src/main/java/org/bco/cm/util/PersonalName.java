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

package org.bco.cm.util;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.bco.cm.dto.PersonalNameDTO;

/**
 * Names of a person. Always holds a first name and surname. May hold middle names.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Embeddable
public class PersonalName implements Serializable {
    
    private String firstName_;
    private String middleNames_;
    private String surname_;
    
    protected PersonalName()
    {
        firstName_ = null;
        middleNames_ = null;
        surname_ = null;
    }
    
    private void setFirstName(String firstName)
    {
        if ( firstName == null ) {
            throw new NullPointerException("Missing first name.");
        }
        if ( firstName.isEmpty() ) {
            throw new IllegalArgumentException("Empty first name.");
        }
        firstName_ = firstName;
    }
    
    /**
     * Returns first name.
     * @return First name. Never null or empty.
     */
    @Column( name = "first_name" )
    public String getFirstName()
    {
        return firstName_;
    }
    
    private void setMiddleNames(String middleNames)
    {
        if ( middleNames != null ) {
            if ( !middleNames.isEmpty() ) {
                middleNames_ = middleNames;
            }
        }
    }
    
    /**
     * Returns middle names.
     * @return Middle names. May be null or empty.
     */
    @Column( name = "middle_names" )
    public String getMiddleNames()
    {
        return middleNames_;
    }
    
    private void setSurname(String surname)
    {
        if ( surname == null ) {
            throw new IllegalArgumentException("Missing surname.");
        }
        if ( surname.isEmpty() ) {
            throw new IllegalArgumentException("Surname is empty.");
        }
        surname_ = surname;
    }
    
    /**
     * Returns surname.
     * @return Surname. Never null or empty.
     */
    @Column( name = "surname" )
    public String getSurname()
    {
        return surname_;
    }
    
    /**
     * Returns new instance.
     * @param spec Specification. Must hold firstName and surname, may hold 
     * middleNames.
     * @return New instance.
     */
    public static PersonalName valueOf(PersonalNameDTO spec)
    {
        if ( spec.getMiddleNames() != null ) {
            return PersonalName.valueOf(spec.getFirstName(), 
                                        spec.getMiddleNames(), 
                                        spec.getSurname());
        } else {
            return PersonalName.valueOf(spec.getFirstName(), 
                                        spec.getSurname());
        }
    }
    
    /**
     * Returns names without middle names. Arguments must neither be null nor empty.
     * @param firstName First name.
     * @param surname Surname.
     * @return New instance.
     */
    public static PersonalName valueOf(String firstName, String surname)
    {
        PersonalName names = new PersonalName();
        names.setFirstName(firstName);
        names.setSurname(surname);
        return names;        
    }
    
    /**
     * Returns names including middle names.
     * @param firstName First name.
     * @param middleNames Middle names. May be null.
     * @param surname Surname.
     * @return New instance.
     */
    public static PersonalName valueOf(String firstName, 
                                       String middleNames, 
                                       String surname)
    {
        PersonalName names = PersonalName.valueOf(firstName, surname);
        names.setMiddleNames(middleNames);
        return names;
    }
    
    /**
     * Returns a DTO representation.
     * @return DTO.
     */
    public PersonalNameDTO toDTO()
    {
        PersonalNameDTO dto = new PersonalNameDTO();
        dto.setFirstName(firstName_);
        dto.setMiddleNames(middleNames_);
        dto.setSurname(surname_);
        return dto;
    }

}
