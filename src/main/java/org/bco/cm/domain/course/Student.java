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

import java.util.Objects;

/**
 * Course participant.
 * @author André H. Juffer, Biocenter Oulu
 */
public class Student {
    
    private final StudentId studentId_;
    private Module currentModule_;
    
    private Student(StudentId studentId)
    {
        studentId_ = studentId;
        currentModule_ = null;
    }
    
    /**
     * Returns identifier.
     * @return Identifier.
     */
    StudentId getStudentId()
    {
        return studentId_;
    }
    
    /**
     * Returns new student instance.
     * @param studentId Student identifier. Must not be null.
     * @return Student.
     */
    public static Student create(StudentId studentId)
    {
        if ( studentId == null ) {
            throw new NullPointerException("Student: StudentId must be provided.");
        }
        return new Student(studentId);
    }
    
    @Override
    public boolean equals(Object other)
    {
        if ( other == null ) {
            return false;
        }
        if ( !(other instanceof Student) ) {
            return false;
        }
        Student student = (Student)other;
        return studentId_.equals(student.studentId_);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.studentId_);
        return hash;
    }
    
    /**
     * Allows student to enter next module.
     * @param module Next module.
     */
    void toNextModule(Module next)
    {
        currentModule_ = next;
    }
    
}
