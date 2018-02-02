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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bco.cm.dto.StudentDTO;
import org.bco.cm.util.Person;

/**
 * Course participant.
 * @author André H. Juffer, Biocenter Oulu
 */
public class Student extends Person<StudentId> {
    
    private Student()
    {
        super();
    }
    
    /**
     * Returns new student instance.
     * @param studentId Identifier. Must not be null.
     * @return Student.
     */
    public static Student create(StudentId studentId)
    {
        Student student = new Student();
        student.setIdentifier(studentId);
        return student;
    }
    
    /**
     * Returns data transfer object representation.
     * @return DTO
     */
    public StudentDTO toDTO()
    {
        StudentDTO dto = new StudentDTO();
        this.populateDTO(dto);
        return dto;
    }
    
    /**
     * Returns DTO collection.
     * @param students Students.
     * @return DTOs.
     */
    public static List<StudentDTO> toDTOs(Collection<Student> students)
    {
        List<StudentDTO> dtos = new ArrayList<>();
        students.forEach((student) -> {
            dtos.add(student.toDTO());
        });
        return dtos;
    }
    
}
