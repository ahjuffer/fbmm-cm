/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer, Biocenter Oulu
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
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.bco.cm.util.Id;

/**
 * Identifies course.
 * @author André H. Juffer, Biocenter Oulu
 */
@Embeddable
public class CourseId extends Id<String> implements Serializable {
    
    protected CourseId()
    {
        super();
    }
    
    public CourseId(String id)
    {
        super(id);
    }    
    
    private void setId(String id)
    {
        this.setValue(id);
    }
    
    /**
     * Returns identifier value.
     * @return Value.
     */
    @Column(name="course_id")
    protected String getId()
    {
        return this.getValue();
    }
    
    /**
     * Returns new identifier.
     * @return Identifier.
     */
    public static CourseId generate()
    {
        UUID uuid = UUID.randomUUID();
        return new CourseId(uuid.toString());
    }
        
}
