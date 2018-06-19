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
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.enrolment.Enrolment;
import org.bco.cm.domain.enrolment.EnrolmentNumber;
import org.bco.cm.domain.enrolment.EnrolmentRegistry;
import org.bco.cm.domain.student.Student;
import org.bco.cm.util.HibernateRepository;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class HibernateEnrolmentRegistry 
    extends HibernateRepository<Enrolment,EnrolmentNumber>
    implements EnrolmentRegistry
{
    private static final String FROM = 
        "select enrolment from " + Enrolment.class.getName() + " enrolment ";

    @Override
    public Enrolment forOne(EnrolmentNumber enrolmentNumber) 
    {
        String number = enrolmentNumber.stringValue();
        String hql = 
            FROM +
            "where enrolment.enrolmentNumber.id = '" + number + "'";
        return this.forSingle(hql);
    }

    @Override
    public List<Enrolment> forAll() 
    {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public Enrolment forCourse(Course course, Student student) 
    {
        String courseId = course.getIdentifierAsString();
        String studentId = student.getIdentifierAsString();
        String hql =
            FROM + "where " +
            "enrolment.courseId.id = '" + courseId + "' and " +
            "enrolment.studentId.id = '" + studentId + "'";
        return this.forSingle(hql);
    }

}
