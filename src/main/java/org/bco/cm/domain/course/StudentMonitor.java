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

package org.bco.cm.domain.course;

import java.util.Collection;
import java.util.HashSet;
import org.bco.cm.dto.StudentMonitorDTO;

/**
 * Observes, supervises (controls) the activities of a student while going 
 * through course modules.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class StudentMonitor {
    
    private final StudentId studentId_;
    private Module current_;
    
    private StudentMonitor()
    {
        studentId_ = null;
        current_ = null;
    }
    
    StudentMonitor(Student student)
    {
        studentId_ = student.getIdentifier();
        current_ = null;
    }
    
    StudentMonitor(Student student, Module module)
    {
        studentId_ = student.getIdentifier();
        current_ = module;
    }
    
    private StudentId getStudentId()
    {
        return studentId_;
    }

    /**
     * Transfers student to the first module.
     * @param first First (or start) module.
     */
    void toFirstModule(Module first)
    {
        current_ = first;
    }
    
    /**
     * Transfers student from current to next module, if there is one.
     * @throws NullPointerException if current is null.
     */
    void toNextModule()
    {
        if ( current_ == null ) {
            throw new NullPointerException(
                "Next module is not assigned in current module."
            );
        }
        if ( current_.hasNext() ) {
            current_ = current_.toNext();
        }
    }
    
    /**
     * Returns a data transfer object.
     * @return DTO.
     */
    StudentMonitorDTO toDTO()
    {
        StudentMonitorDTO dto = new StudentMonitorDTO();
        dto.setCurrentModule(current_.toDTO());
        dto.setStudentId(studentId_.stringValue());
        return dto;
    }
    
    /**
     * Returns list of data transfer objects.
     * @param monitors Student monitors.
     * @return DTOs.
     */
    static Collection<StudentMonitorDTO> toDTOs(Collection<StudentMonitor> monitors)
    {
        Collection<StudentMonitorDTO> dtos = new HashSet<>();
        for (StudentMonitor monitor : monitors) {
            StudentMonitorDTO dto = monitor.toDTO();
            dtos.add(dto);
        }
        return dtos;
    }

}
