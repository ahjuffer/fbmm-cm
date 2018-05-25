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


package org.bco.cm.util;

import java.util.List;

/**
 * Holds entities of type T. One can read from the repository. Updating existing
 * or adding new entities is not possible.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 * @param <T> Entity type.
 * @param <ID> Entity identifier type.
 */
public interface ReadOnlyRepository<T,ID> {
    
    /**
     * Queries for entity with given identifier.
     * @param identifier Entity identifier.
     * @return Entity.
     * @throws NullPointerException if nonexistent.
     */
    T getOne(ID identifier);
    
    /**
     * Returns all entities.
     * @return All entities. May be empty.
     */
    List<T> getAll();

}
