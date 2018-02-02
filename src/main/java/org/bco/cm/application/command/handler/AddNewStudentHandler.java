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

package org.bco.cm.application.command.handler;

import org.bco.cm.application.command.AddNewStudent;
import org.bco.cm.domain.course.Student;
import org.bco.cm.domain.course.StudentId;
import org.bco.cm.domain.course.StudentRepository;
import org.bco.cm.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Handles addition of a new student to student repository.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class AddNewStudentHandler extends CmCommandHandler<AddNewStudent> {
    
    @Autowired
    StudentRepository studentRepository_;

    @Override
    public void handle(AddNewStudent command) 
    {
        StudentId studentId = command.getStudentId();
        StudentDTO spec = command.getSpec();
        Student student = Student.create(studentId);
        studentRepository_.add(student);
    }

}
