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

import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.CourseDescription;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.domain.course.Student;
import org.bco.cm.domain.course.StudentRegistry;
import org.bco.cm.domain.course.StudentId;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.domain.course.Teacher;
import org.bco.cm.domain.course.TeacherRegistry;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class CommandHandlerUtil {
    
    private CommandHandlerUtil()
    {        
    }
    
    /**
     * Returns teacher.
     * @param teacherId Teacher identifier.
     * @param teacherRegistry All teachers.
     * @return Teacher.
     * @throws NullPointerException if requested teacher cannot be found.
     */
    public static Teacher findTeacher(TeacherId teacherId, TeacherRegistry teacherRegistry)
    {
        Teacher teacher = teacherRegistry.forOne(teacherId);
        if ( teacher == null ) {
            throw new NullPointerException(
                teacherId.stringValue() + ": No such teacher."
            );
        }
        return teacher;
    }
    
    /**
     * Returns student.
     * @param studentId Student identifier.
     * @param studentRegistry All students.
     * @return Student.
     * @throws NullPointerException if requested student cannot be found.
     */    
    public static Student findStudent(StudentId studentId, StudentRegistry studentRegistry)
    {
        Student student = studentRegistry.forOne(studentId);
        if ( student == null ) {
            throw new NullPointerException(
                studentId.stringValue() + ": No such student."
            );
        }
        return student;
    }
    
    /**
     * Returns course description.
     * @param courseId Course identifier.
     * @param courseCatalog Course catalog.
     * @return Course description.
     * @throws NullPointerException if requested course description cannot be found.
     */
    public static CourseDescription findCourseDescription(CourseId courseId, 
                                                          CourseCatalog courseCatalog)
    {
        CourseDescription course = courseCatalog.forOne(courseId);
        if ( course == null ) {
            throw new NullPointerException(
                courseId.stringValue() + ": No such course."
            );
        }
        return course;
    }

}
