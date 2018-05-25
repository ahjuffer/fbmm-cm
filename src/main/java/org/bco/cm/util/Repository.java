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
 * Holds entities of type T.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 * @param <T> Entity type.
 * @param <ID> Entity identifier type.
 */
public interface Repository<T,ID> {
    
    /**
     * Adds new entity
     * @param t Entity.
     */
    void add(T t);
    
    /**
     * Updates existing entity.
     * @param t Entity.
     */
    void update(T t);
    
    /**
     * Removes existing entity.
     * @param t Entity.
     */
    void remove(T t);
    
    /**
     * Returns entity with given identifier.
     * @param identifier Identifier.
     * @return Entity.
     */
    public T forOne(ID identifier);
    
    /**
     * Returns all entities in the repository.
     * @return Entities. May be empty.
     */
    public List<T> forAll();    
    
    /**
     * Inquires whether this repository holds an entity with given identifier.
     * @param identifier Identifier.
     * @return Result.
     */
    boolean contains(ID identifier);
    
}
