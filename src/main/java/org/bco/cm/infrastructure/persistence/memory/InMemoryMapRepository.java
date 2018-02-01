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

package org.bco.cm.infrastructure.persistence.memory;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.bco.cm.util.Identifiable;

/**
 * Stores entities in a map using the entities' identifiers as keys. This repository
 * may be used as a stub.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 * @param <T> Domain entity type.
 */
public class InMemoryMapRepository<T extends Identifiable> {
    
    private final Map<String, T> map_;
    
    protected InMemoryMapRepository()
    {
        map_ = new HashMap<>();
    }
    
    /**
     * Adds entity.
     * @param t Entity.
     */
    public void add(T t)
    {
        String key = this.key(t);
        if ( map_.containsKey(key) ) {
            throw new IllegalArgumentException(
                key + ": Entity with identifier is already repository."
            );
        }
        map_.put(key, t);
    }
    
    /**
     * Updates entity.
     * @param t Entity.
     */
    public void update(T t) 
    {
        // Entity should already be in map_, so any update made to the entity 
        // should already be accounted for.
        String key = this.key(t);
        if ( !map_.containsKey(key) ) {
            throw new IllegalStateException(
                "Trying to update an entity that is not in the repository."
            );
        }
    }
    
    /**
     * Removes entity.
     * @param t Entity.
     */
    public void remove(T t) 
    {
        String key = this.key(t);
        map_.remove(key);
    }
    
    /**
     * Returns object using an identifier as key.
     * @param id Identifier.
     * @return Object, or null if nonexistent.
     */
    protected T forIdentifierAsString(String id)
    {
        return map_.get(id);
    }
    
    /**
     * Returns all entities.
     * @return Entities.
     */
    protected Collection<T> all()
    {
        return map_.values();
    }

    private String key(T t)
    {
        return t.getIdentifierAsString();
    }

}
