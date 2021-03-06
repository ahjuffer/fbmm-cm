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

package org.bco.cm.application.command;

import com.tribc.cqrs.domain.command.AbstractCommand;
import org.bco.cm.domain.student.StudentId;
import org.bco.cm.dto.StudentDTO;

/**
 * Command for adding a new student to student registry.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class RegisterNewStudent extends AbstractCommand {
    
    private final StudentId studentId_;
    private final StudentDTO spec_;
    
    /**
     * Constructor.
     * @param studentId New student identifier-
     * @param spec New student specification.
     */
    public RegisterNewStudent(StudentId studentId, StudentDTO spec)
    {
        super(RegisterNewStudent.class);
        studentId_ = studentId;
        spec_ = spec;
    }
    
    /**
     * Returns new student identifier.
     * @return Identifier.
     */
    public StudentId getStudentId()
    {
        return studentId_;
    }
    
    /**
     * Returns new student specification.
     * @return Specification.
     */
    public StudentDTO getSpec()
    {
        return spec_;
    }

}
