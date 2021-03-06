/*
 * The MIT License
 *
 * Copyright 2017 Andr&#233; Juffer, Triacle Biocomputing.
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

package org.bco.cm.domain.teacher;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.bco.cm.util.Id;

/**
 * Identifies teacher.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
@Embeddable
public class TeacherId extends Id<String> implements Serializable {
    
    protected TeacherId()
    {
        super();
    }
    
    public TeacherId(String value)
    {
        super(value);
    }
    
    private void setId(String id)
    {
        this.setValue(id);
    }
    
    /**
     * Returns identifier value.
     * @return Value.
     */
    @Column(name="teacher_id")
    protected String getId()
    {
        return this.getValue();
    }
    
    /**
     * Generates new teacher identifier.
     * @return Identifier.
     */
    public static TeacherId generateId()
    {
        UUID uuid = UUID.randomUUID();
        return new TeacherId(uuid.toString());
    }
}
