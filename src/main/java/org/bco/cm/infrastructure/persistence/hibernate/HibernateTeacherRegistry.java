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

package org.bco.cm.infrastructure.persistence.hibernate;

import java.util.List;
import org.bco.cm.domain.teacher.Teacher;
import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.domain.teacher.TeacherRegistry;
import org.bco.cm.util.HibernateRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Repository
public class HibernateTeacherRegistry 
    extends HibernateRepository<Teacher, TeacherId>
    implements TeacherRegistry 
{
    private static final String FROM = 
        "select teacher from " + Teacher.class.getName() + " teacher ";

    @Override
    public List<Teacher> forAll() 
    {
        return this.forMany(FROM);
    }

    @Override
    public Teacher forOne(TeacherId teacherId) 
    {
        String id = teacherId.stringValue();
        String hql = 
            FROM + 
            "where teacher.teacherId.id = '" + id + "'";
        return this.forSingle(hql);
    }
    
}
