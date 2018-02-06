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

import com.tribc.ddd.domain.event.Event;
import com.tribc.ddd.domain.event.Eventful;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import org.bco.cm.domain.course.event.NewStudentRegistered;
import org.bco.cm.dto.StudentDTO;
import org.bco.cm.util.Person;

/**
 * Course participant.
 * @author André H. Juffer, Biocenter Oulu
 */
public class Student extends Person<StudentId> implements Eventful {
    
    private final Collection<Event> events_;
    
    private Student()
    {
        super();
        events_ = new HashSet<>();
    }
    
    /**
     * Registers new student. Two things happen:
     * <ol>
     *   <li> A new student instance is created.</li>
     *   <li> A corresponding domain event is raised.</lI>
     * </ol>
     * @param studentId New student identifier. Must not be null.
     * @param spec New student specification. Must not be null. Must hold first name and 
     * surname.
     * @return Newly created student.
     * @see #raiseRegistered() 
     */
    public static Student register(StudentId studentId, StudentDTO spec)
    {
        Student student = new Student();
        student.setIdentifier(studentId);
        student.setFirstName(spec.getFirstName());
        student.setSurname(spec.getSurname());
        student.raiseRegistered();
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
    
    @Override
    public Collection<Event> getEvents() 
    {
        return Collections.unmodifiableCollection(events_);
    }

    @Override
    public void clearEvents() 
    {
        events_.clear();
    }
    
    /**
     * Raise event to signal that this student did register with the client 
     * application.
     */
    public void raiseRegistered()
    {
        events_.add(new NewStudentRegistered(this.getIdentifier()));
    }
}
