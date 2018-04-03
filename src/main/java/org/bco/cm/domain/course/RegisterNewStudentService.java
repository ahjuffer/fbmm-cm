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

import org.bco.cm.dto.StudentDTO;

/**
 * Domain service for registering a new student.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class RegisterNewStudentService {
    
    private RegisterNewStudentService()
    {        
    }
    
    /**
     * Register new student. he new student is added to the student registry.
     * @param studentId Student identifier.
     * @param spec New student specification.
     * @param studentRegistry Student registry.
     * @return New student.
     * @throws IllegalArgumentException if studentId is already in use.
     */
    public static Student register(StudentId studentId,
                                   StudentDTO spec,
                                   StudentRegistry studentRegistry)
    {
        if ( studentRegistry.contains(studentId) ) {
            throw new IllegalArgumentException(
                studentId.stringValue() + ": Identifier already in use."
            );
        }
        Student student = Student.valueOf(studentId, spec);
        studentRegistry.add(student);
        student.registered();
        return student;
    }

}
