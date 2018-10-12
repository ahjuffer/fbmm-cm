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

package org.bco.cm.domain.teacher;

import org.bco.cm.util.TeacherId;
import org.bco.cm.dto.TeacherDTO;

/**
 * Domain service for registering a new teacher.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class RegisterNewTeacherService {
    
    private RegisterNewTeacherService()
    { 
    }
    
    /**
     * Creates new teacher and adds teacher to teacher registry.
     * @param teacherId New teacher identifier.
     * @param spec New teacher specification.
     * @param teacherRegistry Teacher registry.
     * @return Registered teacher.
     */
    public static Teacher register(TeacherId teacherId, 
                                   TeacherDTO spec, 
                                   TeacherRegistry teacherRegistry)
    {
        if (teacherId == null || spec == null || teacherRegistry == null ) {
            throw new NullPointerException("Missing arguments.");
        }
        if ( teacherRegistry.contains(teacherId) ) {
            throw new IllegalArgumentException(
                teacherId.stringValue() + ": Identifier already in use."
            );
        }
        
        Teacher teacher = Teacher.valueOf(teacherId, spec);
        teacherRegistry.add(teacher);        
        teacher.registered();
        
        return teacher;
    }

}
