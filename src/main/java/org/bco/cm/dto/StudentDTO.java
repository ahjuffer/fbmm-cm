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
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NaturalId;

/**
 * DTO for student.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Entity(name = "StudentDTO")
@Table(name = "students")
public class StudentDTO extends PersonDTO implements Serializable 
{    
    private UUID id_;
    
    public StudentDTO()
    {
        super();
        id_ = null;
    }
    
    private void setId(UUID id)
    {
        id_ = id;
    }
    
    /**
     * Returns identifier assigned or generated by repository.
     * @return Identifier.
     */
    @Id
    @GeneratedValue
    protected UUID getId()
    {
        return id_;
    }
        
    public void setStudentId(String studentId)
    {
        this.setIdentifier(studentId);
    }

    @Column(name = "student_id")
    @NaturalId
    public String getStudentId()
    {
        return this.getIdentifier();
    }

    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("StudentDTO : {").append(newline);
        s.append("studentId - ").append(this.getStudentId()).append(newline);
        s.append("id - ").append(id_).append(newline);
        this.appendTo(s);
        s.append("}");
        return s.toString();        
    }
}
