/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer, Biocenter Oulu.
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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.bco.cm.dto.CourseDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.dto.OnlineMaterialDTO;
import org.bco.cm.util.Identifiable;

/**
 * An unit of teaching that typically lasts one academic term. It is led by one 
 * or more instructors (teachers), and has a fixed roster of 
 * students. The course must specify a title, description, and an objective. 
 * It consists of separate components or modules that are 
 * taken in a sequential order. A student progresses to the next module 
 * possibly conditioned on intermediate results obtained for assignments 
 * and/or quizzes.
 * @author André H. Juffer, Biocenter Oulu
 */
public class Course implements Eventful, Identifiable {
    
    // For generating module identifiers that are unique to a given course.
    private final static Random RANDOM;
    
    static {
        RANDOM = new Random();
        RANDOM.setSeed(Instant.now().toEpochMilli());
    }
    
    private CourseId courseId_;
    private String title_;
    private String description_;
    private String objective_;
    private Map<Integer,Module> modules_;
    private boolean ongoing_;
    private Map<StudentId,StudentMonitor> roster_;
    private Module firstModule_;
    
    private TeacherId teacherId_;
    
    private final Collection<Event> events_;
    
    private Course()
    {
        courseId_ = null;
        title_ = null;
        description_ = null;
        modules_ = new HashMap<>();  // No modules added.
        ongoing_ = false;
        roster_ = new HashMap<>();  // No student registered.
        firstModule_ = null;
        teacherId_ = null;
        events_ = new HashSet<>();
    }
    
    private void setIdentifier(CourseId courseId)
    {
        if ( courseId == null ) {
            throw new NullPointerException("Missing course identifier.");
        }
        courseId_ = courseId;
    }
    
    /**
     * Returns course identifier.
     * @return Identifier. Never null.
     */
    public CourseId getIdentifier()
    {
        return courseId_;
    }
    
    @Override
    public String getIdentifierAsString() 
    {
        return courseId_.stringValue();
    }

    private void setTitle(String title)
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
    public String getTitle()
    {
        return title_;
    }
    
    private void setDescription(String description)
    {
        if ( description == null ) {
            throw new NullPointerException("Missing course description.");
        }
        if ( description.isEmpty() ) {
            throw new IllegalArgumentException("Empty course description.");
        }
        description_ = description;
    }
    
    /**
     * Return course description.
     * @return Description. Never null or empty.
     */
    public String getDescription()
    {
        return description_;
    }
    
    private void setObjective(String objective)
    {
        if ( objective == null ) {
            throw new NullPointerException("Missing course objective.");
        }
        if ( objective.isEmpty() ) {
            throw new IllegalArgumentException("Missing course objective.");
        }
        objective_ = objective;
    }
    
    /**
     * Returns objective.
     * @return Objective. Never null or empty.
     */
    public String getObjective()
    {
        return objective_;
    }
    
    private void setModules(Map<Integer,Module> modules)
    {        
        if ( modules_ == null ) {
            throw new NullPointerException("Missing course modules.");
        }
        modules_ = modules;
    }
    
    private Map<Integer,Module> getModules()
    {
        return modules_;
    }
    
    /**
     * Returns course modules.
     * @return Unmodifiable list of modules. Never null. May be empty.
     */
    public List<Module> modules()
    {
        return Collections.unmodifiableList(new ArrayList<>(modules_.values()));
    }
    
    private void setRoster(Map<StudentId, StudentMonitor> roster)
    {
        if ( roster_ == null ) {
            throw new NullPointerException("Missing student roster.");
        }
        roster_ = roster;
    }
    
    private Map<StudentId, StudentMonitor> getRoster()
    {
        return roster_;
    }
    
    /**
     * Returns student roster.
     * @return Roster. Never null. May be empty.
     */
    public List<StudentMonitor> roster()
    {
        return Collections.unmodifiableList(new ArrayList<>(roster_.values()));
    }
    
    private void setFirstModule(Module first)
    {
        firstModule_ = first;
    }
    
    private Module getFirstModule()
    {
        return firstModule_;
    }
    
    private void setTeacherId(TeacherId teacherId)
    {
        if ( teacherId == null ) {
            throw new NullPointerException("Missing teacher for course.");
        }
        teacherId_ = teacherId;
    }
    
    private TeacherId getTeacherId()
    {
        return teacherId_;
    }
    
    /**
     * Starts (creates) a new course.
     * @param teacher Responsible teacher.
     * @param courseId New course identifier.
     * @param spec New course specification. Must include title, description, and
     * an objective.
     * @return New course.
     */
    public static Course start(Teacher teacher, CourseId courseId, CourseDTO spec)
    {
        if ( teacher == null ) {
            throw new NullPointerException("Missing new course teacher.");
        }
        if ( spec == null ) {
            throw new NullPointerException("Missing new course specification.");
        }
        Course course = new Course();
        course.setIdentifier(courseId);
        course.setDescription(spec.getDescription());
        course.setTitle(spec.getTitle());
        course.setObjective(spec.getObjective());
        course.setTeacherId(teacher.getIdentifier());
        return course;
    }
    
    /**
     * Updates title, description, and objective.
     * @param spec Update specification.
     */
    public void update(CourseDTO spec)
    {
        this.setDescription(spec.getDescription());
        this.setTitle(spec.getTitle());
        this.setObjective(spec.getObjective());
    }
    
    /**
     * Notification of course enrolment. If the course is 
     * ongoing, the student gains access to the first module.
     * @param student Student.
     * @throws IllegalStateException if no more seats are available in the course.
     */
    public void enrolled(Student student)
    {
        if ( !this.hasSeatsAvailable() ) {
            throw new IllegalStateException("No more seats available in course.");
        }
        StudentMonitor monitor = new StudentMonitor(student);        
        roster_.put(student.getIdentifier(), monitor);
        
        // Give access to first module.
        if ( this.isOngoing() ) {
            Module first = this.firstModule();
            monitor.toFirstModule(first);
        }        
    }
    
    /**
     * Notification of module completion. Student is allowed to transfer to 
     * the next module.
     * @param student Student.
     * @throws IllegalStateException if this course is not ongoing.
     */
    public void completedModule(Student student)
    {
        if ( !this.isOngoing() ) {
            throw new IllegalStateException("Course is currently not ongoing.");
        }
        StudentMonitor monitor = roster_.get(student.getIdentifier());
        monitor.toNextModule();
    }
    
    /**
     * Notification of canceling of enrolment.
     * @param student Student.
     */
    public void enrolmentCanceled(Student student)
    {
        roster_.remove(student.getIdentifier());
    }
    
    /**
     * Begins this course. Enrolled students can now start with the first module. 
     * A course can only begin if the course consists of at least one module 
     * with a non-empty learning path. A module may have an assignment and/or a quiz.
     * @see #isOngoing() 
     * @see #addModule(org.bco.cm.dto.ModuleDTO)
     * @see #addOnlineMaterialToLearningPathOfModule(int, org.bco.cm.dto.OnlineMaterialDTO) 
     * @throws IllegalStateException if this courses has no modules or 
     * if any of the added modules still holds an empty learning path.
     */
    public void begin()
    {
        if ( !this.hasModules() ) {
            throw new IllegalStateException(
                "Course cannot begin. No modules were added to this course."
            );
        }
        Module empty = this.findModuleWithEmptyLearninPath();
        if ( empty != null ) {
            throw new IllegalStateException(
                "Course cannot begin. Module '" + empty.getName() + "' " +
                "still contains an empty learning path. " + 
                "Please add online material to learning path."
            );
        }
        ongoing_ = true;
        this.giveStudentsAccessToFirstModule();
    }
    
    /**
     * Ends this course. This means that all activities undertaken by students 
     * enrolled in this course are halted.
     * @see #isOngoing()
     */
    public void end()
    {
        ongoing_ = false;
    }
    
    /**
     * Is this course currently ongoing? A course is ongoing if the course has 
     * begun and has not yet ended.
     * @return Result.
     * @see #begin()
     * @see #end() 
     */
    public boolean isOngoing()
    {
        return ongoing_ == true;
    }
    
    /**
     * Adds new module to this course.
     * @param spec Module
     */
    public void addModule(ModuleDTO spec)
    {
        if ( spec == null ) {
            throw new NullPointerException(
                "Trying to add an undefined module to course."
            );
        }
                
        // Create new module according to specification.
        int moduleId = this.generateModuleId();
        Module next = Module.valueOf(moduleId, spec);
        
        // The new module becomes the first module of this course, if no other modules
        // are present.
        if ( this.hasModules() ) {
            Module last = this.lastModule();
            last.setNext(next);
        } else {
            this.setFirstModule(next);
        }
        
        // Save new module.
        modules_.put(moduleId, next);
    }
    
    /**
     * Adds online material to learning path of a module.
     * @param moduleId Module specification. Corresponds to the module identifier of 
     * the module in question. Must be >= 1. 
     * @param materialSpec New online material. Must not be null.
     * @throws IllegalStateException if course is already ongoing.
     * @throws IllegalArgumentException if moduleId &lt; 1.
     * @throws NullPointerException if materialSpec is null or requested module 
     * is nonexistent.
     */
    public void addOnlineMaterialToLearningPathOfModule(int moduleId, 
                                                        OnlineMaterialDTO materialSpec)
    {
        if ( this.isOngoing() ) {
            throw new IllegalStateException(
                    "Cannot add new material while course is ongoing."
            );
        }
        if ( moduleId < 1 ) {
            throw new IllegalArgumentException(
                "Module identifier must be >= 1."
            );
        }
        if ( materialSpec == null ) {
            throw new NullPointerException("Online material not specified.");
        }
        Module module = this.find(moduleId);
        if ( module == null ) {
            throw new NullPointerException("Specified module does not exist.");
        }
        module.addOnlineMaterialToLearningPath(materialSpec);
    }
    
    /**
     * Returns a data transfer object.
     * @return DTO.
     */
    public CourseDTO toDTO()
    {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(courseId_.stringValue());
        dto.setDescription(description_);
        dto.setTitle(title_);
        dto.setObjective(objective_);
        dto.setModules(Module.toDTOs(modules_.values()));
        dto.setRoster(StudentMonitor.toDTOs(roster_.values()));
        if ( this.hasModules() ) {
            dto.setFirstModuleId(this.getFirstModule().getModuleId());        
        }
        dto.setOngoing(ongoing_);
        dto.setTeacherId(teacherId_.stringValue());
        return dto;
    }
    
    /**
     * Are there still seats available.
     * @return Result.
     */
    public boolean hasSeatsAvailable()
    {
        return true;
    }
    
    private boolean hasModules()
    {
        return !modules_.isEmpty();
    }
    
    private Module firstModule()
    {
        if ( modules_.isEmpty() ) {
            throw new IllegalStateException("No modules specified for course.");
        }
        return this.getFirstModule();
    }
    
    private Module lastModule()
    {
        if ( modules_.isEmpty() ) {
            throw new IllegalStateException("No modules specified for course.");
        }
        Module last = this.firstModule();
        Module next = last.toNext();
        while ( next != last ) {
            last = next;
            next = last.toNext();
        }
        return last;
    }
    
    private void giveStudentsAccessToFirstModule()
    {
        Module first = this.firstModule();
        roster_.values().forEach((monitor) -> {
            monitor.toFirstModule(first);
        });
    }
    
    private boolean modulesHaveNonEmptyLearningPath()
    {        
        return modules_.values().stream().noneMatch((module) -> ( module.hasLearningPath() ));
    }
    
    private Module findModuleWithEmptyLearninPath()
    {
        Collection<Module> modules = this.modules();
        for (Module module : modules) {
            if ( module.hasLearningPath() ) {
                return module;
            }
        }
        return null;
    }
    
    /**
     * Returns module.
     * @param moduleId Module identifier. Has meaning locally only.
     * @return Module, or null if nonexistent.
     */
    private Module find(int moduleId)
    {
        if ( modules_.containsKey(moduleId) ) {
            return modules_.get(moduleId);
        } else {
            return null;
        }
    }
    
    /**
     * Returns identifier only unique in context of this course.
     * @return Identifier.
     */
    private int generateModuleId()
    {
        int bound = Integer.MAX_VALUE - 1;
        int id = RANDOM.nextInt(bound);
        while ( modules_.containsKey(id) ) {
            id = RANDOM.nextInt(bound);
        }
        return id;
    }

    @Override
    public Collection<Event> getEvents() 
    {
        return EventUtil.selectUnhandled(events_);
    }

    @Override
    public void clearEvents() 
    {
        events_.clear();
    }

}
