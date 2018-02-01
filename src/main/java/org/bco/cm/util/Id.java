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

/**
 * Identifier.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 * @param <T> Identifier value type.
 */
public class Id<T> {
    
    private final T value_;

    protected Id(T value)
    {
        value_ = value;
        this.validate();
    }
    
    /**
     * Returns identifier value.
     * @return Value.
     */
    protected T value()
    {
        return value_;
    }
    
    /**
     * Returns string representation of identifier value.
     * @return String representation.
     */
    public String stringValue()
    {
        return value_.toString();
    }
    
    /**
     * Validates this identifier.
     * @throws NullPointerException if identifier value is null.
     * @throws IllegalStateException if identifier value is an empty string.
     */
    private void validate()
    {
        String name = this.getClass().getName();
        if ( this.value() == null ) {
            throw new NullPointerException(
                "Identifier value of '" + name + "' must not be null."
            );
        }
        if ( this.stringValue().isEmpty() ) {
            throw new IllegalStateException(
                "Identifier value of '" + name + "' must not be empty."
            );
        }
    }
    
    /**
     * Returns string representation of identifier value..
     * @return String representation.
     * @see #stringValue() 
     */
    @Override
    public String toString()
    {
        return this.stringValue();
    }

    @Override
    public boolean equals(Object other)
    {
        if ( other == null ) {
            return false;
        }
        if ( other == this ) {
            return true;
        }
        if ( !(other instanceof Id) ) {
            return false;
        }
        final Id id = (Id)other;
        return this.stringValue().equals(id.stringValue());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.value_);
        return hash;
    }
}
