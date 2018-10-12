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
import org.bco.cm.application.query.ReadOnlyEnrolmentRegistry;
import org.bco.cm.util.CourseId;
import org.bco.cm.util.EnrolmentNumber;
import org.bco.cm.util.StudentId;
import org.bco.cm.dto.EnrolmentDTO;
import org.bco.cm.util.ReadOnlyHibernateRepository;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class HibernateReadOnlyEnrolmentRegistry 
    extends ReadOnlyHibernateRepository<EnrolmentDTO, EnrolmentNumber> 
    implements ReadOnlyEnrolmentRegistry
{
    private static final String FROM = 
        "select enrolment from " + EnrolmentDTO.class.getName() + " enrolment ";  
    
    @Override
    public EnrolmentDTO getOne(EnrolmentNumber enrolmentNumber) 
    {
        String en = enrolmentNumber.stringValue();
        String hql = 
            FROM + 
            "where enrolment.enrolmentNumber = '" + en + "'";
        return this.forSingle(hql);
    }

    @Override
    public List<EnrolmentDTO> getAll() 
    {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public List<EnrolmentDTO> getCourseEnrolments(CourseId courseId) 
    {
        String id = courseId.stringValue();
        String hql =
            FROM +
            "where enrolment.courseId = '" + id + "'";
        return this.forMany(hql);
    }

    @Override
    public EnrolmentDTO getCourseEnrolment(CourseId courseId, StudentId studentId) 
    {
        String cid = courseId.stringValue();
        String sid = studentId.stringValue();
        String hql = 
            FROM + "where " +
            "enrolment.courseId = '" + cid + "' and " +
            "enrolment.studentId = '" + sid + "'";
        return this.forSingle(hql);
    }

    @Override
    public List<EnrolmentDTO> getStudentEnrolments(StudentId studentId) 
    {
        String sid = studentId.stringValue();
        String hql =
            FROM + "where " +
            "enrolment.studentId = '" + sid + "'";
        return this.forMany(hql);
    }

}
