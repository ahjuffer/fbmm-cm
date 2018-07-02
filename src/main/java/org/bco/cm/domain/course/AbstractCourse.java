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

import org.bco.cm.domain.teacher.TeacherId;
import org.bco.cm.domain.teacher.Teacher;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import org.bco.cm.dto.AbstractCourseDTO;
import org.bco.cm.dto.ModuleDTO;

/**
 * Serves as a base class for all course types.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 * @see org.bco.cm.domain.course.Module
 */
@MappedSuperclass
public abstract class AbstractCourse 
{    
    // For generating module identifiers that are unique to a given course.
    static final Random RANDOM;
    
    static {
        RANDOM = new Random();
        RANDOM.setSeed(Instant.now().toEpochMilli());
    }
    
    private String title_;
    private String summary_;
    private List<Module> modules_;
    private TeacherId teacherId_;
    
    protected AbstractCourse()
    {
        title_ = null;
        summary_ = null;
        modules_ = new ArrayList<>();
        teacherId_ = null;
    }

    /**
     * Sets the title.
     * @param title Title. Must neither be null nor empty.
     */
    protected void setTitle(String title)
    {
        if ( title == null ) {
            throw new NullPointerException("Missing course title.");
        }
        if ( title.isEmpty() ) {
            throw new IllegalArgumentException("Empty course title.");
        }
        title_ = title;
    }
    
    /**
     * Returns course title.
     * @return Title. Never null or empty.
     */
    @Column( name = "title")
    public String getTitle()
    {
        return title_;
    }
    
    /**
     * Sets course summary. 
     * @param summary Summary. Must neither be null nor empty.
     */
    protected void setSummary(String summary)
    {
        if ( summary == null ) {
            throw new NullPointerException("Missing course summary.");
        }
        if ( summary.isEmpty() ) {
            throw new IllegalArgumentException("Empty course summary.");
        }
        summary_ = summary;
    }
    
    /**
     * Returns course summary.
     * @return Summary. Never null or empty.
     */
    @Column( name = "summary" )
    public String getSummary()
    {
        return summary_;
    }

    /**
     * Sets modules.
     * @param modules Modules. Must not be null. May be empty.
     */
    protected void setModules(List<Module> modules)
    {
        if ( modules == null ) {
            throw new NullPointerException("Missing modules.");
        }
        modules_ = modules;
        modules_.forEach(module -> {
            this.setParentCourse(module);
        });
    }
    
    /**
     * Returns modules.
     * @return Modules. Never null. May be empty.
     */
    protected List<Module> modules()
    {
        return modules_;
    }
    
    /**
     * Sets the owning course.
     * @param module Module.
     */
    protected abstract void setParentCourse(Module module);
    
    /**
     * Creates and adds new module.
     * @param spec New module specification.
     * @see #addModule(org.bco.cm.domain.course.Module) 
     */    
    protected void addModule(ModuleDTO spec)
    {
        if ( spec == null ) {
            throw new NullPointerException(
                "Trying to add an undefined module."
            );
        }
                
        // Create new module according to specification.
        int moduleId = this.generateModuleId();
        Module next = Module.valueOf(moduleId, spec);
        this.addModule(next);
    }
    
    /**
     * Adds new module. This module is appended to the list 
     * of modules.
     * @param module New module.
     */
    protected void addModule(Module module)
    {
        this.setParentCourse(module);
        modules_.add(module);
    }
        
    /**
     * Returns new module identifier value that is unique in the context of 
     * this course.
     * @return Identifier, always > 0.
     */
    protected int generateModuleId()
    {
        int bound = Integer.MAX_VALUE - 100;
        int id = RANDOM.nextInt(bound);
        while (this.containsModule(id) && id == 0) {
            id = RANDOM.nextInt(bound);
        }
        return id;
    }

    /**
     * Sets the teacher identifier.
     * @param teacherId Identifier. Must not be null.
     */
    protected void setTeacherId(TeacherId teacherId)
    {
        if ( teacherId == null ) {
            throw new NullPointerException("Missing teacher for course.");
        }
        teacherId_ = teacherId;
    }
    
    /**
     * Returns identifier responsible teacher.
     * @return Identifier. Never null.
     */
    @Embedded
    public TeacherId getTeacherId()
    {
        return teacherId_;
    }
    
    /**
     * Is given teacher responsible for this course.
     * @param teacher Teacher.
     * @return Result.
     */
    public boolean isResponsibleTeacher(Teacher teacher)
    {
        return teacherId_.equals(teacher.getTeacherId());
    }
    
    /**
     * Any modules?
     * @return Result.
     */
    boolean hasModules()
    {
        return !modules_.isEmpty();
    }
    
    /**
     * Finds module.
     * @param moduleId Module identifier value.
     * @return Module or null if nonexistent.
     */
    Module findModule(int moduleId)
    {
        if ( moduleId > 0 && !modules_.isEmpty() ) {
            for (Module module : modules_) {
                if (module.getModuleId() == moduleId ) {
                    return module;
                }
            }
        }
        return null;
    }

    /**
     * Sets summary, title, and modules.
     * @param spec Course specification.
     */
    protected void populate(AbstractCourseDTO spec)
    {
        this.setSummary(spec.getSummary());
        this.setTitle(spec.getTitle());
        this.clearModules();
        spec.modules().forEach(module -> {
            this.addModule(module);
        });
    }
    
    /**
     * Populates DTO with summary, title, modules and teacher identifier.
     * @param dto DTO.
     */
    protected void populateDTO(AbstractCourseDTO dto)
    {
        dto.setSummary(summary_);
        dto.setTitle(title_);
        List<ModuleDTO> modules = Module.toDTOs(modules_);
        dto.setModules(modules);
        dto.setTeacherId(teacherId_.stringValue());        
    }
    
    /**
     * Contains a particular module?
     * @param moduleId Module identifier.
     * @return Result.
     */
    protected boolean containsModule(int moduleId)
    {
        return modules_.stream()
                       .anyMatch((module) -> ( module.getModuleId() == moduleId ));
    }
    
    /**
     * Removes all modules.
     */
    protected void clearModules()
    {
        for (Module module : modules_) {
            module.setCourseDescription(null);
            module.setCourse(null);
        }
        modules_.clear();
    }
    
    
    
}
