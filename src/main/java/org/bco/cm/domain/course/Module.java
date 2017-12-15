/*
 * The MIT License
 *
 * Copyright 2017 ajuffer.
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

package org.bco.cm.domain.course;

/**
 * Course module.
 * @author ajuffer
 */
public class Module {

    private final LearningPath learningPath_;
    private final Assignment assignment_;
    private final Quiz quiz_;
    private Module next_;
    
    private Module(LearningPath learningPath)
    {
        learningPath_ = learningPath;
        assignment_ = null;
        quiz_ = null;
    }
    
    /**
     * Does this module include an assignment.
     * @return Result.
     */
    public boolean hasAssignment()
    {
        return assignment_ != null;
    }
    
    /**
     * Does this module include a quiz?
     * @return Result.
     */
    public boolean hasQuiz()
    {
        return quiz_ != null;
    }
    
    /**
     * Creates a module without assignment and quiz.
     * @param learningPath Learning path Must not be null.
     * @return Module
     */
    public static Module create(LearningPath learningPath)
    {
        if ( learningPath == null ) {
            throw new NullPointerException("Each module must specify a learning path");
        }
        return new Module(learningPath);
    }
    
    Module next()
    {
        return next_;
    }
        
}
