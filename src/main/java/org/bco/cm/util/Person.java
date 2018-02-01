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
import org.bco.cm.dto.PersonDTO;

/**
 * An actual living person.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 * @param <ID> Person identifier type.
 */
public class Person<ID extends Id<String>> implements Identifiable {
    
    private ID identifier_;
    
    protected Person()
    {
        identifier_ = null;
    }
    
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
    public ID getIdentifier()        
    {
        return identifier_;
    }
    
    @Override
    public String getIdentifierAsString()
    {
        return identifier_.stringValue();
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
        if ( !(other instanceof Person) ) {
            return false;
        }
        Person person = (Person)other;
        return identifier_.equals(person.getIdentifier());
    }

    @Override
    public int hashCode() 
    {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.identifier_);
        return hash;
    }
    
    /**
     * Populate DTO.
     * @param <T> DTO type.
     * @param dto DTO
     */
    protected final <T extends PersonDTO> void populateDTO(T dto)
    {
        if ( identifier_ != null ) {
            dto.setIdentifier(identifier_.toString());
        }
    }

}
