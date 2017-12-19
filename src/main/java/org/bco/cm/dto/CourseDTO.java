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

/**
 * Date transfer object for a course.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class CourseDTO {
    
    private String courseId_;
    private String title_;
    private boolean ongoing_;
    
    public CourseDTO()
    {
        courseId_ = null;
        title_ = null;
        ongoing_ = false;
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
    
    public void setOngoing(boolean ongoing)
    {
        ongoing_ = ongoing;
    }
    
    public boolean getOngoing()
    {
        return ongoing_;
    }
    
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("CourseDTO : {").append(newline);
        s.append("courseId - ").append(courseId_).append(newline);
        s.append("title - ").append(title_).append(newline);
        s.append("ongoing - ").append(ongoing_).append(newline);
        s.append("}");
        return s.toString();
    }
}
