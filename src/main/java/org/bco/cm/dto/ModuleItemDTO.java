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

package org.bco.cm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import static javax.persistence.DiscriminatorType.STRING;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * DTO of ModuleItem
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Entity( name = "ModuleItemDTO" )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table( name= "module_items" )
@DiscriminatorColumn( 
    name="discriminator", 
    discriminatorType = STRING
)
@DiscriminatorValue( "null" )
public abstract class ModuleItemDTO implements Serializable {
    
    private UUID id_;
    private String title_;
    private ModuleDTO parent_;
    
    protected ModuleItemDTO()
    {
        id_ = null;
        title_ = null;
        parent_ = null;
    }

    protected void setId(UUID id)
    {
        id_ = id;
    }
    
    @Id
    @GeneratedValue
    protected UUID getId()
    {
        return id_;
    }
    
    public void setTitle(String title)
    {
        title_ = title;
    }
    
    /**
     * Returns title.
     * @return Title. Neither null nor empty.
     */
    @Column( name = "title" )
    public String getTitle()
    {
        return title_;
    }
    
    public void setParentModule(ModuleDTO parent)
    {
        parent_ = parent;
    }
    
    @ManyToOne()
    @JoinColumn( name="module_id" )
    @JsonIgnore
    public ModuleDTO getParentModule()
    {
        return parent_;
    }
    
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("ModuleItemDTO : {").append(newline);
        s.append("id - ").append(id_).append(newline);
        s.append("title - ").append(title_).append(newline);
        s.append("}");
        return s.toString();
    }
    
    private void setName(String name)
    {
        // Ignore.
    }

    /**
     * Returns name of this item.
     * @return Name.
     */
    @Transient
    public abstract String getName();

}
