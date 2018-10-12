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
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * DTO for Assignment.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Entity( name = "AssignmentDTO" )
@DiscriminatorValue( value = "Assignment" )
public class AssignmentDTO extends ModuleItemDTO implements Serializable {
    
    private String content_;
    private String simulator_;
    
    public AssignmentDTO()
    {
        super();
        content_ = null;
        simulator_ = null;
    }

    public void setContent(String content)
    {
        content_ = content;
    }
    
    @Column( name = "content" )
    public String getContent()
    {
        return content_;
    }
    
    public void setSimulator(String simulator)
    {
        simulator_ = simulator;
    }
    
    @Column( name = "simulator" )
    public String getSimulator()
    {
        return simulator_;
    }
    
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("AssignmentDTO : {").append(newline);
        s.append(super.toString()).append(newline);
        s.append("content - ").append(content_).append(newline);
        s.append("simulator - ").append(simulator_).append(newline);
        s.append("}");
        return s.toString();
    }
    
    private void setName(String name)
    {
        // Ignore.
    }

    @Override
    @Transient
    public String getName() 
    {
        return "assignment";
    }

}
