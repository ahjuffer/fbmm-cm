/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer
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

package org.bco.cm.api.rest.spring;

import java.util.List;
import org.bco.cm.api.CoursesFacade;
import org.bco.cm.application.query.CourseFinder;
import org.bco.cm.dto.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API implementation using Spring.
 * @author Andr&#233; Juffer, Biocenter Oulu
 */
@RestController
@RequestMapping(value="/courses")
public class CoursesController implements CoursesFacade {
    
    private CourseFinder courseFinder_;
    
    @Autowired
    public void setCourseFinder(CourseFinder courseFinder)
    {
        courseFinder_ = courseFinder;
    }
    
    /**
     * @return JSON representation of a course collection.
     */
    @RequestMapping(method=RequestMethod.GET)
    @Override
    public List<CourseDTO> getCourses(@RequestParam(value="spec", defaultValue="all") String spec) 
    {
        if ( !(spec.equals("all") || spec.equals("ongoing")) ) {
            throw new IllegalArgumentException(spec + ": Illegal course specification.");
        }
        return courseFinder_.getCourses(spec);
    }    
}
