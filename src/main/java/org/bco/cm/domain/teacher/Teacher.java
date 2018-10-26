/*
 * The MIT License
 *
 * Copyright 2017 Andr&#233; Juffer, Triacle Biocomputing.
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
import com.tribc.cqrs.util.EventUtil;
import com.tribc.ddd.domain.event.Event;
import com.tribc.ddd.domain.event.Eventful;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.teacher.event.NewTeacherRegistered;
import org.bco.cm.dto.TeacherDTO;
import org.bco.cm.util.Person;
import org.bco.cm.util.PersonalName;
import org.hibernate.annotations.NaturalId;

/**
 * Course instructor.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
@Entity(name = "Teacher")
@Table(name = "teachers")
public class Teacher extends Person<TeacherId> implements Eventful, Serializable {
    
    private UUID id_;
    
    private final Collection<Event> events_;
    
    protected Teacher()
    {
        super();
        id_ = null;
        events_ = new HashSet<>();
    }
    
    private void setId(UUID id)
    {
        id_ = id;
    }
    
    /**
     * Returns identifier assignment or generated by repository.
     * @return Identifier.
     */
    @Id
    @GeneratedValue
    protected UUID getId()
    {
        return id_;
    }
    
    private void setTeacherId(TeacherId teacherId)
    {
        this.setIdentifier(teacherId);
    }
    
    /**
     * Returns teacher identifier. This identifier is provided by the
     * client application.
     * @return Teacher identifier.
     */
    @NaturalId
    @Embedded
    public TeacherId getTeacherId()
    {
        return this.getIdentifier();
    }
    
    /**
     * Creates new teacher.
     * @param teacherId Identifier. Must not be null.
     * @param spec New teacher specification. Must hold first name and surname.
     * @return Newly created teacher.
     */
    static public Teacher valueOf(TeacherId teacherId, TeacherDTO spec)
    {
        Teacher teacher = new Teacher();
        teacher.setIdentifier(teacherId);
        PersonalName names = PersonalName.valueOf(spec.getNames());
        teacher.setNames(names);
        return teacher;
    }
    
    /**
     * Returns data transfer object representation.
     * @return DTO
     */
    public TeacherDTO toDTO()
    {
        TeacherDTO dto = new TeacherDTO();
        this.populateDTO(dto);
        return dto;
    }
    
    @Override
    @Transient
    public Collection<Event> getEvents() 
    {
        return EventUtil.selectUnhandled(events_);
    }

    @Override
    public void clearEvents() 
    {
        events_.clear();
    }
    
    /**
     * Signals this teacher is newly registered.
     */
    void registered()
    {
        events_.add(new NewTeacherRegistered(this));
    }      
        
}
