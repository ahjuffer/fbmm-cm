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
 * Repository using Hibernate for mapping between persistence storage and entities.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 * @param <T> Entity type.
 * @param <ID> Entity identifier type.
 */
public abstract class HibernateRepository<T,ID> implements Repository<T,ID> {
    
    //private SessionFactory sessionFactory_;
    private EntityManager entityManager_;
    
    protected HibernateRepository()
    {
        //sessionFactory_ = null;
        entityManager_ = null;
    }
    
    /*
    @PersistenceUnit
    @Autowired
    public void setEntityManagerFactory(EntityManagerFactory emf)
    {
        sessionFactory_ = emf.unwrap(SessionFactory.class);
    }
    */
    
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager)
    {
        entityManager_ = entityManager;
    }
    
    
    /*
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        sessionFactory_ = sessionFactory;
    }
    */
    
    @Override
    public void add(T t) 
    {
        this.getSession().save(t);
    }

    @Override
    public void update(T t) 
    {
        this.getSession().update(t);
    }

    @Override
    public void remove(T t) 
    {
        this.getSession().delete(t);
    }
    
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
        List many = this.getSession().createQuery(hql).list();
        if ( many.size() > 1 ) {
            throw new IllegalArgumentException(
                "Many entities in repository found, where one was expected."
            );
        }
        if ( many.isEmpty() ) {
            return null;
        }
        T t = (T) many.get(0);
        return t;
    }
    
    private Session getSession()
    {
        //return sessionFactory_.getCurrentSession();
        return entityManager_.unwrap(Session.class);
    }

    @Override
    abstract public T forEntityId(ID identifier);
    
    @Override
    public boolean contains(ID identifier)
    {
        return this.forEntityId(identifier) != null;
    }
    
}
