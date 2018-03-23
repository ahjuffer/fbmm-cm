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

package org.bco.cm.util;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import org.bco.cm.dto.PersonDTO;

/**
 * An actual living person.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 * @param <ID> Person identifier type.
 */
@MappedSuperclass
public class Person<ID extends Id<String>> implements Identifiable {
    
    private ID identifier_;
    private String firstName_;
    private String surname_;
    
    protected Person()
    {
        identifier_ = null;
        firstName_ = null;
        surname_ = null;
    }
    
    /**
     * Sets identifier.
     * @param identifier Identifier.
     */
    protected void setIdentifier(ID identifier)
    {
        if ( identifier == null ) {
            throw new NullPointerException(
                "Missing identifier for " + this.getClass().getName()
            );
        }
        identifier_ = identifier;
    }
    
    /**
     * Returns identifier.
     * @return Identifier.
     */
    @Transient
    public ID getIdentifier()        
    {
        return identifier_;
    }
    
    @Override
    @Transient
    public String getIdentifierAsString()
    {
        return identifier_.stringValue();
    }
    
    /**
     * Sets first name.
     * @param firstName First name. Must neither be null nor empty. 
     */
    protected void setFirstName(String firstName)
    {
        if ( firstName == null ) {
            throw new NullPointerException("Missing first name.");
        }
        if ( firstName.isEmpty() ) {
            throw new IllegalArgumentException("Missing first name.");
        }
        firstName_ = firstName;
    }
    
    /**
     * Returns first name.
     * @return First name. Never null or empty.
     */
    @Column(name = "first_name")
    public String getFirstName()
    {
        return firstName_;
    }
    
    /**
     * Sets surname.
     * @param surname Surname. Must neither be null nor empty. 
     */
    protected void setSurname(String surname)
    {
        if ( surname == null ) {
            throw new NullPointerException("Missing surname.");
        }
        if ( surname.isEmpty() ) {
            throw new IllegalArgumentException("Missing surname.");
        }
        surname_ = surname;        
    }
    
    /**
     * Returns surname.
     * @return Surname. Never null or empty.
     */
    @Column(name = "surname")    
    public String getSurname()
    {
        return surname_;
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
        Person person = (Person)other;
        return identifier_.equals(person.getIdentifier());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 11 * hash + Objects.hashCode(this.identifier_);
        hash = 11 * hash + Objects.hashCode(this.firstName_);
        hash = 11 * hash + Objects.hashCode(this.surname_);
        return hash;
    }

    /**
     * Populate DTO.
     * @param <T> DTO type.
     * @param dto DTO
     */
    protected final <T extends PersonDTO> void populateDTO(T dto)
    {
        dto.setIdentifier(identifier_.toString());
        dto.setFirstName(firstName_);
        dto.setSurname(surname_);
    }

}
