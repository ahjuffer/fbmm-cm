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

package org.bco.cm.infrastructure.persistence.memory;

import java.util.UUID;
import org.bco.cm.domain.course.Teacher;
import org.bco.cm.domain.course.TeacherId;
import org.bco.cm.domain.course.TeacherRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Repository
public class InMemoryTeacherRepository 
    extends InMemoryMapRepository<Teacher> 
    implements TeacherRepository {
    
    @Override
    public Teacher forTeacherId(TeacherId teacherId) 
    {
        return this.forIdentifierAsString(teacherId.stringValue());
    }

    @Override
    public TeacherId generateId() 
    {
        UUID uuid = UUID.randomUUID();
        return new TeacherId(uuid.toString());
    }

}