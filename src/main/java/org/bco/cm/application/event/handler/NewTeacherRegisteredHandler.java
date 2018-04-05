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

package org.bco.cm.application.event.handler;

import com.tribc.ddd.domain.event.EventHandler;
import org.apache.logging.log4j.LogManager;
import org.bco.cm.domain.course.event.NewTeacherRegistered;
import org.apache.logging.log4j.Logger;
import org.bco.cm.domain.course.Teacher;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class NewTeacherRegisteredHandler 
    extends EventHandler<NewTeacherRegistered> 
{
    static final Logger LOGGER = LogManager.getLogger("com.bco.cm");
    
    @Override
    public void handle(NewTeacherRegistered event) 
    {        
        Teacher teacher = event.getTeacher();
        
        LOGGER.info("Creating an account for teacher: " + teacher.getSurname());
    }
}