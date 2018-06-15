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
import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.dto.TeacherDTO;

/**
 * Command for adding a new teacher to teacher registry.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class RegisterNewTeacher extends AbstractCommand {

    private final TeacherId teacherId_;
    private final TeacherDTO spec_;
    
    public RegisterNewTeacher(TeacherId teacherId, TeacherDTO spec)
    {
        super(RegisterNewTeacher.class);
        teacherId_ = teacherId;
        spec_ = spec;        
    }
    
    /**
     * Returns new teacher identifier.
     * @return Identifier.
     */
    public TeacherId getTeacherId()
    {
        return teacherId_;
    }
    
    /**
     * Returns new teacher specification.
     * @return Specification.
     */
    public TeacherDTO getSpec()
    {
        return spec_;
    }
}
