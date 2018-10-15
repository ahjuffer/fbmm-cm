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

import org.bco.cm.application.command.CompleteQuiz;
import org.bco.cm.domain.student.Student;
import org.bco.cm.domain.student.StudentRegistry;
import org.bco.cm.util.CourseId;
import org.bco.cm.util.StudentId;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Handles completion of a quiz by a student.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class CompleteQuizHandler extends CmCommandHandler<CompleteQuiz> {

    @Autowired
    private StudentRegistry studentRegistry_;

    @Override
    public void handle(CompleteQuiz command) 
    {
        StudentId studentId = command.getStudentId();
        CourseId courseId = command.getCourseId();        
        Student student = CommandHandlerUtil.findStudent(studentId, studentRegistry_);        
        
        student.completeQuiz(courseId);
        
        this.handleEvents(student);
    }

}