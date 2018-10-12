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

package org.bco.cm.domain.course;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds a simulator
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class Simulator {
    
    private static final Simulator MD;
    private static final List<Simulator> SIMULATORS;
    
    static {
        SIMULATORS = new ArrayList<>();
        MD = new Simulator("md");
        SIMULATORS.add(MD);
    }
    
    private final String name_;
    
    private Simulator(String name)
    {        
        name_ = name;
    }
    
    String getName()
    {
        return name_;
    }

    /**
     * Returns simulator.
     * @param name Simulator name. Must be a single work, no spaces.
     * @return Simulator.
     * @throws IllegalArgumentException if simulator cannot be identifier.
     */
    public static Simulator valueOf(String name)
    {
        for (Simulator sim : SIMULATORS) {
            if ( sim.getName().equals(name) ) {
                return sim;
            }
        }
        throw new IllegalArgumentException(name + ": No such simulator.");
    }
    
    /**
     * Returns all allowed simulator names.
     * @return Names.
     */
    public static List<String> getNames()
    {
        List<String> names = new ArrayList<>();
        for (Simulator sim : SIMULATORS) {
            names.add(sim.getName());
        }
        return names;
    }

}
