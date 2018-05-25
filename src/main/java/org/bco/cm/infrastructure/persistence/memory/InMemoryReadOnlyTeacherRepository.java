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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bco.cm.domain.course.Teacher;
import org.bco.cm.dto.TeacherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.bco.cm.domain.course.TeacherRegistry;
import org.bco.cm.application.query.ReadOnlyTeacherRegistry;
import org.bco.cm.domain.course.TeacherId;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class InMemoryReadOnlyTeacherRepository implements ReadOnlyTeacherRegistry {
    
    private TeacherRegistry teacherRepository_;
    
    @Autowired
    public void setTeacherRepository(TeacherRegistry teacherRepository)
    {
        teacherRepository_ = teacherRepository;
    }

    @Override
    public List<TeacherDTO> getAll() 
    {
        Collection<Teacher> teachers = teacherRepository_.forAll();
        List<TeacherDTO> dtos = new ArrayList<>();
        teachers.forEach(((teacher) -> {
            TeacherDTO dto = teacher.toDTO();
            dtos.add(dto);
        }));
        return dtos;
    }

    @Override
    public TeacherDTO getOne(TeacherId teacherId) 
    {
        return teacherRepository_.forOne(teacherId).toDTO();
    }

}
