package org.bco.cm;

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

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bco.cm.api.facade.StudentFacade;
import org.bco.cm.application.query.ReadOnlyCourseCatalog;
import org.bco.cm.application.query.ReadOnlyTeacherRepository;
import org.bco.cm.domain.course.CourseCatalog;
import org.bco.cm.domain.course.EnrolmentRepository;
import org.bco.cm.domain.course.StudentId;
import org.bco.cm.domain.course.StudentRegistry;
import org.bco.cm.domain.course.TeacherRegistry;
import org.bco.cm.dto.StudentDTO;
import org.bco.cm.infrastructure.persistence.memory.InMemoryCourseCatalog;
import org.bco.cm.infrastructure.persistence.memory.InMemoryEnrolmentRepository;
import org.bco.cm.infrastructure.persistence.memory.InMemoryReadOnlyCourseCatalog;
import org.bco.cm.infrastructure.persistence.memory.InMemoryReadOnlyTeacherRepository;
import org.bco.cm.infrastructure.persistence.memory.InMemoryTeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
/*@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })
*/
public class TestApplication implements ApplicationRunner {
    
    private static final Logger LOGGER = LogManager.getLogger("com.bco.cm");
    
    @Autowired
    private StudentFacade studentFacade_;
    
    @Bean
    @Primary
    TeacherRegistry teacherRegistry()
    {
        return new InMemoryTeacherRepository();
    }
    
    @Bean
    @Primary
    ReadOnlyTeacherRepository readOnlyTeacherRepository()
    {
        return new InMemoryReadOnlyTeacherRepository();
    }
    
    @Bean
    @Primary
    ReadOnlyCourseCatalog readOnlyCourseCatalog()
    {
        return new InMemoryReadOnlyCourseCatalog();
    }
    
    @Bean 
    @Primary
    CourseCatalog courseCatalog()
    {
        return new InMemoryCourseCatalog();
    }
    
    @Bean
    @Primary    
    EnrolmentRepository enrolmentRepository()
    {
        return new InMemoryEnrolmentRepository();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        SpringApplication app = new SpringApplication(TestApplication.class);
        app.run();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception 
    {
        
        StudentId studentId = StudentId.generate();
        LOGGER.info("StudentId - " + studentId.stringValue());
        StudentDTO spec = new StudentDTO();
        spec.setFirstName("André");
        spec.setSurname("Juffer");
        studentFacade_.register(studentId, spec);
        
        StudentDTO registered = studentFacade_.getStudent(studentId);
        LOGGER.info("Registered - " + registered);
        
        List<StudentDTO> students = studentFacade_.getAllStudents();
        LOGGER.info("All students: " + students);
        
        
        /*
        TeacherRegistry teacherRepository = this.teacherRegistry();
        TeacherId teacherId = new TeacherId("12345");
        Teacher teacher = Teacher.create(teacherId);
        teacherRepository.add(teacher);
        
        CourseCatalog catalog = this.courseCatalog();
        
        CourseDTO dto = new CourseDTO();
        dto.setDescription("Chromatography Practical November 2018.");
        dto.setTitle("Chromatography Practical");
        dto.setObjective("Learning chromatography.");
        Course cg = Course.start(teacher, catalog.generateId(), dto);
        catalog.add(cg);
        
        dto.setTitle("In Silico Methodologies");
        dto.setDescription("In Silico Methodologies January 2019");
        dto.setObjective("Learning modeling and simulation tools.");
        Course insilico = Course.start(teacher, catalog.generateId(), dto);
        ModuleDTO spec1 = new ModuleDTO();
        spec1.setName("Module 1 of 'in silico'");
        LearningPathDTO lp1 = new LearningPathDTO();
        OnlineMaterialDTO mat11 = new OnlineMaterialDTO();
        mat11.setMaterialType("reading");
        mat11.setObjective("Understand this and that...");
        mat11.setResource("http://www.example.com");
        Collection<OnlineMaterialDTO> mats1 = new HashSet<>();
        mats1.add(mat11);
        OnlineMaterialDTO mat12 = new OnlineMaterialDTO();
        mat12.setMaterialType("video");
        mat12.setObjective("Some objective.");
        mat12.setResource("http://www.example2.com");
        mats1.add(mat12);
        lp1.setOnlineMaterials(mats1);
        spec1.setLearningPath(lp1);
        insilico.addModule(spec1);
        
        ModuleDTO spec2 = new ModuleDTO();
        spec2.setName("Module 2 of 'in silico'");
        LearningPathDTO lp2 = new LearningPathDTO();
        spec2.setLearningPath(lp2);
        OnlineMaterialDTO mat21 = new OnlineMaterialDTO();
        mat21.setMaterialType("reading");
        mat21.setObjective("Understand something ...");
        mat21.setResource("http://www.example.net");
        Collection<OnlineMaterialDTO> mats2 = new HashSet<>();
        mats2.add(mat21);
        lp2.setOnlineMaterials(mats2);
        insilico.addModule(spec2);
        
        catalog.add(insilico);
        
        //insilico.begin();
        */
    }
}
