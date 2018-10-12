/*
 * The MIT License
 *
 * Copyright 2018 André J. Juffer, Triacle Biocomputing
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

package org.bco.cm.application.command;

import com.tribc.cqrs.domain.command.AbstractCommand;
import org.bco.cm.util.TeacherId;
import org.bco.cm.util.CourseDescriptionId;
import org.bco.cm.dto.CourseDescriptionDTO;

/**
 * Command for posting a new course.
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
public class PostNewCourse extends AbstractCommand {
    
    private final TeacherId teacherId_;
    private final CourseDescriptionId courseId_;
    private final CourseDescriptionDTO spec_;
    
    /**
     * Constructor.
     * @param teacherId Identifier of responsible teacher.
     * @param courseId New course identifier.
     * @param spec New course specification.
     */
    public PostNewCourse(TeacherId teacherId,
                         CourseDescriptionId courseId,
                         CourseDescriptionDTO spec)
    {
        super(PostNewCourse.class);
        teacherId_ = teacherId;
        courseId_ = courseId;
        spec_ = spec;
    }
    
    public TeacherId getTeacherId()
    {
        return teacherId_;
    }
    
    public CourseDescriptionId getCourseId()
    {
        return courseId_;
    }
    
    public CourseDescriptionDTO getSpecification()
    {
        return spec_;
    }
}
