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

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;

/**
 * Read-only repository using Hibernate for mapping between persistence storage 
 * and entities.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 * @param <T> Entity type.
 * @param <ID> Entity identifier type.
 */
public abstract class ReadOnlyHibernateRepository<T,ID> 
    implements ReadOnlyRepository<T,ID> {

    private EntityManager entityManager_;
    
    protected ReadOnlyHibernateRepository()
    {
        //sessionFactory_ = null;
        entityManager_ = null;
    }   
    
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        entityManager_ = entityManager;
    }
    
    
    @Override
    abstract public T getOne(ID identifier);
    
    @Override
    abstract public List<T> getAll();
    
    /**
     * Returns many entities.
     * @param hql HQL query string.
     * @return Entities. May be empty,
     */
    public List<T> forMany(String hql)
    {
        List many = this.getSession().createQuery(hql).list();
        List<T> list = new ArrayList<>();
        many.forEach(e -> {
            T t = (T)e;
            list.add(t);
        });
        return list;
    }
    
    /**
     * Returns a single entity.
     * @param hql HQL query string.
     * @return Entity, or null if nonexistent.
     * @throws IllegalArgumentException if more than one entity was found.
     */
    public T forSingle(String hql)
    {
        List<T> many = this.getSession().createQuery(hql).list();
        if ( many.size() > 1 ) {
            throw new IllegalArgumentException(
                "Many entities in read-only repository found, where one was expected."
            );
        }
        if ( many.isEmpty() ) {
            return null;
        }
        return (T) many.get(0);       
    }
    
    private Session getSession()
    {
        return entityManager_.unwrap(Session.class);
    }
    
}
