/*
 * The MIT License
 *
 * Copyright 2017 Pivotal Software, Inc..
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

package org.bco.cm.dto;

import java.util.Collection;
import java.util.HashSet;

/**
 * DTO for Course.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class CourseDTO {
    
    private String courseId_;
    private String title_;
    private String description_;
    private String objective_;
    private final Collection<ModuleDTO> modules_;
    private boolean ongoing_;
    private final Collection<StudentMonitorDTO> roster_;
    private int firstModuleId_;
    
    public CourseDTO()
    {
        courseId_ = null;
        title_ = null;
        description_ = null;
        objective_ = null;
        modules_ = new HashSet<>();
        ongoing_ = false;
        roster_ = new HashSet<>();
        firstModuleId_ = -1;
    }
    
    public void setCourseId(String courseId)
    {
        courseId_ = courseId;
    }
    
    public String getCourseId()
    {
        return courseId_;
    }
    
    public void setTitle(String title)
    {
        title_ = title;
    }
    
    public String getTitle()
    {
        return title_;
    }
    
    public void setDescription(String description)
    {
        description_ = description;
    }
    
    public String getDescription()
    {
        return description_;
    }
    
    public void setObjective(String objective)
    {
        objective_ = objective;
    }
    
    public String getObjective()
    {
        return objective_;
    }
    
    public void setModules(Collection<ModuleDTO> modules)
    {
        modules_.clear();
        modules_.addAll(modules);
    }
    
    public Collection<ModuleDTO> getModules()
    {
        return modules_;
    }
    
    public void setOngoing(boolean ongoing)
    {
        ongoing_ = ongoing;
    }
    
    public boolean getOngoing()
    {
        return ongoing_;
    }
    
    public void setRoster(Collection<StudentMonitorDTO> roster)
    {
        roster_.clear();
        roster_.addAll(roster);
    }
    
    public Collection<StudentMonitorDTO> getRoster()
    {
        return roster_;
    }
    
    /**
     * Sets module identifier value of the first module in the course.
     * @param id Identifier.
     */
    public void setFirstModuleId(int id)
    {
        firstModuleId_ = id;
    }
    
    /**
     * Returns module identifier value of the first module in the course.
     * @return Identifier. if set, its value is &gt; 0.
     */
    public int getFirstModuleId()
    {
        return firstModuleId_;
    }
    
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("CourseDTO : {").append(newline);
        s.append("courseId - ").append(courseId_).append(newline);
        s.append("title - ").append(title_).append(newline);
        s.append("description - ").append(description_).append(newline);
        s.append("objective - ").append(objective_).append(newline);
        s.append("ongoing - ").append(ongoing_).append(newline);
        s.append("modules - ").append(modules_).append(newline);
        s.append("roster - ").append(roster_).append(newline);
        s.append("firstModuleId - ").append(firstModuleId_).append(newline);
        s.append("}");
        return s.toString();
    }
}
