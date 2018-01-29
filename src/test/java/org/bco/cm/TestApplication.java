/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer, Biocenter Oulu
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

import java.util.Collection;
import java.util.HashSet;
import org.bco.cm.application.query.CourseRepository;
import org.bco.cm.domain.course.Course;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.dto.CourseDTO;
import org.bco.cm.dto.LearningPathDTO;
import org.bco.cm.dto.ModuleDTO;
import org.bco.cm.dto.OnlineMaterialDTO;
import org.bco.cm.infrastructure.persistence.memory.InMemoryCourseCatalog;
import org.bco.cm.infrastructure.persistence.memory.InMemoryCourseRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 *
 * @author Andr&#233; Juffer, Biocenter Oulu
 */
@SpringBootApplication
public class TestApplication implements ApplicationRunner {
    
    @Bean
    @Primary
    CourseRepository courseRepository()
    {
        return new InMemoryCourseRepository();
    }
    
    @Bean 
    CourseCatalog courseCatalog()
    {
        return new InMemoryCourseCatalog();
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
        
        CourseDTO dto = new CourseDTO();
        dto.setDescription("Chromatography Practical November 2018.");
        dto.setTitle("Chromatography Practical");
        dto.setObjective("Learning chromatography.");
        Course cg = Course.start(catalog.generateCourseId(), dto);
        catalog.add(cg);
        
        dto.setTitle("In Silico Methodologies");
        dto.setDescription("In Silico Methodologies January 2019");
        dto.setObjective("Learning modeling and simulation tools.");
        Course insilico = Course.start(catalog.generateCourseId(), dto);
        ModuleDTO spec1 = new ModuleDTO();
        LearningPathDTO lp1 = new LearningPathDTO();
        OnlineMaterialDTO mat1 = new OnlineMaterialDTO();
        mat1.setMaterialType("reading");
        mat1.setObjective("Understand this and that...");
        mat1.setResource("http://www.example.com");
        Collection<OnlineMaterialDTO> mats = new HashSet<>();
        mats.add(mat1);
        OnlineMaterialDTO mat2 = new OnlineMaterialDTO();
        mat2.setMaterialType("video");
        mat2.setObjective("Some objective.");
        mat2.setResource("http://www.example2.com");
        mats.add(mat2);
        lp1.setOnlineMaterials(mats);
        spec1.setLearningPath(lp1);
        insilico.addModule(spec1);
        
        ModuleDTO spec2 = new ModuleDTO();
        LearningPathDTO lp2 = new LearningPathDTO();
        spec2.setLearningPath(lp2);
        insilico.addModule(spec2);
        
        catalog.add(insilico);
    }
}
