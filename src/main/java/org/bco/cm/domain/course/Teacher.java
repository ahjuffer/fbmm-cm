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

package org.bco.cm.domain.course;

import org.bco.cm.dto.TeacherDTO;
import org.bco.cm.util.Person;

/**
 * Course instructor.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class Teacher extends Person<TeacherId> {
    
    private Teacher()
    {
        super();
    }
    
    /**
     * Returns new teacher instance.
     * @param teacherId Identifier. Must not be null.
     * @return Teacher.
     */
    public static Teacher create(TeacherId teacherId)
    {
        Teacher teacher = new Teacher();
        teacher.setIdentifier(teacherId);
        return teacher;
    }
    
    /**
     * Returns data transfer object representation.
     * @return DTO
     */
    public TeacherDTO toDTO()
    {
        TeacherDTO dto = new TeacherDTO();
        this.populateDTO(dto);
        return dto;
    }
}
