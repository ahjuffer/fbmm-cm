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

package org.bco.cm;

import org.bco.cm.application.query.CourseFinder;
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.CourseId;
import org.bco.cm.infrastructure.persistence.memory.InMemoryCourseRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author Andr&#233; Juffer, Triacle Biocomputing
 */
@SpringBootApplication

public class TestApplication implements ApplicationRunner {
    
    private static final InMemoryCourseRepository inMemory_;
    
    static {
        inMemory_ = new InMemoryCourseRepository();
    }
    
    @Bean
    CourseFinder courseFinder()
    {
        return inMemory_;
    }
    
    @Bean 
    CourseCatalog courseCatalog()
    {
        return inMemory_;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        SpringApplication.run(TestApplication.class, args); 
    }

    @Override
    public void run(ApplicationArguments args) throws Exception 
    {
        CourseCatalog catalog = this.courseCatalog();
        Course course1 = Course.start(CourseId.valueOf("12345"), "Some course title");
        catalog.add(course1);
        Course course2 = Course.start(CourseId.valueOf("54321"), "Some other course");
        catalog.add(course2);
    }
}
