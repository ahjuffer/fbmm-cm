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

package org.bco.cm.dto;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@MappedSuperclass
public abstract class AbstractCourseDTO {
    
    private String title_;
    private String summary_;
    private List<ModuleDTO> modules_;
    private String teacherId_;

    public AbstractCourseDTO()
    {
        title_ = null;
        summary_ = null;
        modules_ = new ArrayList<>();
        teacherId_ = null;
    }
    
    public void setTitle(String title)
    {
        title_ = title;
    }
    
    @Column( name="title" )
    public String getTitle()
    {
        return title_;
    }
    
    public void setSummary(String summary)
    {
        summary_ = summary;
    }
    
    @Column( name = "summary" )
    public String getSummary()
    {
        return summary_;
    }
    
    public void setModules(List<ModuleDTO> modules)
    {
        if ( modules != null ) {
            modules_ = modules;
            modules_.forEach(module -> {
                this.setParent(module);
            });            
        }
    }
        
    public List<ModuleDTO> modules()
    {
        return modules_;
    }
    
    public void setTeacherId(String teacherId)
    {
        teacherId_ = teacherId;
    }
    
    @Column( name="teacher_id" )
    public String getTeacherId()
    {
        return teacherId_;
    }
    
    public abstract void setParent(ModuleDTO module);
        
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("AbstractCourseDTO : {").append(newline);
        s.append("title - ").append(title_).append(newline);
        s.append("summary - ").append(summary_).append(newline);
        s.append("modules - ").append(modules_).append(newline);
        s.append("teacherId - ").append(teacherId_).append(newline);
        s.append("}");
        return s.toString();
    }    

}
