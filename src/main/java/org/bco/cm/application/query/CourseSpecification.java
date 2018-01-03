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

package org.bco.cm.application.query;

import java.util.ArrayList;
import java.util.List;
import org.bco.cm.dto.CourseDTO;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class CourseSpecification {
    
    private static final CourseSpecification ALL;
    private static final CourseSpecification ONGOING;
    
    static {
        ALL = new CourseSpecification("all");
        ONGOING = new CourseSpecification("ongoing");
    }
    
    private final String spec_;
    
    private CourseSpecification()
    {
        spec_ = "all";
    }
    
    private CourseSpecification(String spec)
    {
        spec_ = "all";
    }
    
    /**
     * Specification for all courses.
     * @return Specification.
     */
    public static CourseSpecification all()
    {
        return ALL; 
    }
    
    /**
     * Specification for ongoing courses.
     * @return Specification.
     */
    public static CourseSpecification ongoing()
    {
        return ONGOING;
    }
    
    /**
     * Returns course specification.
     * @param spec Specification.
     * @return Course specification.
     * @throws NullPointerException if spec is null.
     * @throws IllegalArgumentException is spec is empty or if spec holds an 
     * invalid value.
     */
    public static CourseSpecification valueOf(String spec)
    {
        if ( spec == null ) {
            throw new NullPointerException("No course specification provided.");
        }
        if ( spec.isEmpty() ) {
            throw new IllegalArgumentException("No course specification provided.");
        }
        if ( spec.equals(ALL.spec_) ) {
            return CourseSpecification.all();
        } else if ( spec.equals(ONGOING.spec_) ) {
            return CourseSpecification.ongoing();
        } else {
            throw new IllegalArgumentException(spec + ": Illegal course specification.");
        }
    }
    
    /**
     * Queries for courses in the repository that satisfy this specification.
     * @param courseRepository Course repository.
     * @return Courses. May be empty.
     */
    public List<CourseDTO> query(CourseRepository courseRepository)
    {
        if ( spec_.equals(ALL.spec_) ) {
            return courseRepository.getAll();
        }
        if ( spec_.equals(ONGOING.spec_) ) {
            return courseRepository.getOngoing();
        }
        return new ArrayList<>();
    }

}
