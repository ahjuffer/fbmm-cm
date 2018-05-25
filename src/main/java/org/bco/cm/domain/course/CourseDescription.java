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

package org.bco.cm.domain.course;

import com.tribc.cqrs.util.EventUtil;
import com.tribc.ddd.domain.event.Event;
import com.tribc.ddd.domain.event.Eventful;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.bco.cm.domain.course.event.NewCourseAddedToCatalog;
import org.bco.cm.dto.CourseDescriptionDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.util.Identifiable;

/**
 * Describes a course. Consists of a title, summary, and separate components 
 * ("modules"). It is created and maintained by a teacher.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Entity( name = "CourseDescription" )
@Table( name = "course_descriptions" )
public class CourseDescription 
    extends AbstractCourse
    implements Eventful, Identifiable, Serializable {
    
    private UUID id_;
    
    private final Collection<Event> events_;
    
    protected CourseDescription()
    {
        super();
        events_ = new HashSet<>();
    }
    
    private void setId(UUID id)
    {
        id_ = id;
    }
    
    /**
     * Returns identifier generated by repository.
     * @return Identifier.
     */
    @Id
    @GeneratedValue
    protected UUID getId()
    {
        return id_;
    }
    
    private void setIdentifier(CourseId courseId)
    {
        if ( courseId == null ) {
            throw new NullPointerException("Missing course identifier.");
        }
        super.setCourseId(courseId);
    }
    
    /**
     * Returns course identifier.
     * @return Identifier. Never null.
     */
    @Transient
    public CourseId getIdentifier()
    {
        return super.getCourseId();
    }
    
    @Override
    @Transient
    public String getIdentifierAsString() 
    {
        return this.getIdentifier().stringValue();
    }
    
    
   /**
     * Returns modules.
     * @return Modules. May be empty.
     */
    @OneToMany( 
        mappedBy = "courseDescription", 
        cascade = CascadeType.ALL, 
        orphanRemoval = true
    )    
    protected List<Module> getModules()
    {
        return super.modules();
    }
    
    @Override
    protected void setParent(Module module)
    {
        module.setCourseDescription(this);
    }
    
   /**
     * Creates a new course description. Arguments must not be null.
     * @param teacher Responsible teacher.
     * @param courseId New course identifier.
     * @param spec New course specification. Must include title and summary, no
     * modules.
     * @return New course description.
     * @see #addModule(org.bco.cm.dto.ModuleDTO) 
     */
    public static CourseDescription valueOf(Teacher teacher, 
                                            CourseId courseId,
                                            CourseDescriptionDTO spec)
    {
        if ( teacher == null ) {
            throw new NullPointerException("Missing new course teacher.");
        }
        if ( spec == null ) {
            throw new NullPointerException("Missing new course specification.");
        }
        CourseDescription course = new CourseDescription();
        course.setCourseId(courseId);
        course.setTeacherId(teacher.getTeacherId());
        course.setSummary(spec.getSummary());
        course.setTitle(spec.getTitle());
        spec.getModules().forEach(module -> {
            course.addModule(module);
        });
        return course;
    }
    
    /**
     * Updates course description.
     * @param spec Course update specification. Must not be null, and 
     * must include title and summary. Modules are ignored.
     * @see #addModule(org.bco.cm.dto.ModuleDTO) 
     */
    public void update(CourseDescriptionDTO spec)
    {
        this.setSummary(spec.getSummary());
        this.setTitle(spec.getTitle());
        this.clearModules();
        spec.getModules().forEach(module -> {
            this.addModule(module);
        });
    }
    
    /**
     * Returns a data transfer object.
     * @return DTO.
     */
    public CourseDescriptionDTO toDTO()
    {
        CourseDescriptionDTO dto = new CourseDescriptionDTO();
        this.populate(dto);
        return dto;
    }
    
    /**
     * Updates existing module.
     * @param moduleId Module identifier.
     * @param spec Module update specification.
     */
    public void updateModule(int moduleId, ModuleDTO spec)
    {
        Module module = this.findModule(moduleId);
        module.update(spec);
    }
    
    /**
     * Removes a module.
     * @param moduleId Module identifier.
     */
    public void removeModule(int moduleId)
    {
        Module module = this.findModule(moduleId);
        module.setCourseDescription(null);
        super.modules().remove(module);
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
    
    @Override
    public boolean equals(Object other)
    {
        if ( this == other ) {
            return true;
        }
        if ( other == null ) {
            return false;
        }
        if ( !(other instanceof CourseDescription) ) {
            return false;
        }
        CourseDescription course = (CourseDescription)other;
        return this.getCourseId().equals(course.getCourseId());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id_);
        return hash;
    }

    /**
     * Signals that this course is newly added to the course catalog.
     */
    void addedToCourseCatalog()
    {
        this.events_.add(new NewCourseAddedToCatalog(this));
    }
    
}
