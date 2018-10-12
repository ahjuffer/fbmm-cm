/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer, Biocenter Oulu.
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

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.bco.cm.dto.AssignmentDTO;

/**
 * A set of tasks assigned to a student by the teacher. The student returns a 
 * report covering the assignment.
 * @author André H. Juffer, Biocenter Oulu
 */
@Entity( name = "Assignment" )
@DiscriminatorValue( value = "Assignment" )
public class Assignment extends ModuleItem implements Serializable {
    
    private String content_;
    private String simulator_;
    
    protected Assignment()
    {
        super();
        content_ = null;
        simulator_ = null;
    }
    
    private void setContent(String content)
    {
        if ( content == null ) {
            throw new NullPointerException("Assignment: Content must be provided.");
        }
        if ( content.isEmpty() ) {
            throw new IllegalArgumentException("Assignment: Content must be provided.");
        }
        content_ = content;
    }
    
    @Column( name = "content" )
    public String getContent()
    {
        return content_;
    }
    
    private void setSimulator(String simulator)
    {
        simulator_ = simulator;
    }
    
    /**
     * Returns simulator name.
     * @return Name. May be null or "none" if no simulator is required.
     */
    @Column( name = "simulator" )
    protected String getSimulator()
    {
        return simulator_;
    }
    
    /**
     * Does this assignment require a simulator?
     * @return Result.
     */
    public boolean requiresSimulator()
    {
        if ( simulator_ == null ) {
            return false;
        }
        if ( simulator_.isEmpty() ) {
            return false;
        }
        return ( !simulator_.equals("none"));
    }
    
    /**
     * Returns new assignment.
     * @param spec New assignment specification. Must specify content and may include 
     * a simulator name.
     * @return New assignment.
     */
    public static Assignment valueOf(AssignmentDTO spec)
    {
        Assignment assignment = new Assignment();
        assignment.populate(spec);
        assignment.setContent(spec.getContent());
        assignment.setSimulator(spec.getSimulator());
        return assignment;
    }
    
    @Override
    public AssignmentDTO toDTO() 
    {
        AssignmentDTO dto = new AssignmentDTO();
        super.populateDTO(dto);
        dto.setContent(content_);
        dto.setSimulator(simulator_);
        return dto;
    }
    
}
