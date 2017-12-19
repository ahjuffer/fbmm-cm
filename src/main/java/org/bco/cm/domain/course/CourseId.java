/*
 * The MIT License
 *
 * Copyright 2017 ajuffer.
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
 * Identifies course.
 * @author ajuffer
 */
public class CourseId {
    
    private final String id_;
    
    private CourseId(String id)
    {
        id_ = id;
    }
    
    /**
     * Returns identifier value.
     * @return Value. Never null.
     */
    public String getValue()
    {
        return id_;
    }
    
    /**
     * Creates identifier from string value.
     * @param id Identifier value. Must neither be null nor empty.
     * @return Identifier.
     */
    public static CourseId valueOf(String id)
    {
        if ( id == null ) {
            throw new NullPointerException("Course identifier value must be provided.");
        }
        if ( id.isEmpty() ) {
            throw new IllegalArgumentException("Course identifier value must be provided.");            
        }
        return new CourseId(id);
    }
    
}
