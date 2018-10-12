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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import org.bco.cm.dto.ModuleItemDTO;

/**
 * A single item of a module.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Entity( name = "ModuleItem" )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table( name= "module_items" )
@DiscriminatorColumn( 
    name="discriminator", 
    discriminatorType = STRING
)
@DiscriminatorValue( "null" )
public abstract class ModuleItem implements Serializable {
    
    private UUID id_;
    private String title_;
    private Module parent_;
    private int moduleItemId_;
    
    protected ModuleItem()
    {        
        id_ = null;
        title_ = null;
        parent_ = null;
        moduleItemId_ = -1;
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
    
    protected void setTitle(String title)
    {
        if ( title == null ) {
            throw new NullPointerException("ModuleItem: A title must be provided.");
        }
        if ( title.isEmpty() ) {
            throw new IllegalArgumentException("ModuleItem: A title must be provided.");
        }
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
    
    void setModuleItemId(int moduleItemId)
    {
        moduleItemId_ = moduleItemId;
    }
    
    @Column( name = "module_item_id" )
    public int getModuleItemId()
    {
        return moduleItemId_;
    }

    /**
     * Sets parent (or owning) module.
     * @param parent Parent module.
     */    
    protected void setParentModule(Module parent)
    {
        parent_ = parent;
    }
    
    /**
     * Returns parent (or owning) module.
     * @return Parent module.
     */
    @ManyToOne()
    @JoinColumn( name="module_id" )
    protected Module getParentModule()
    {
        return parent_;
    }
    
    /**
     * Sets common properties.
     * @param common Common properties. Must include title.
     */
    protected void populate(ModuleItemDTO common)
    {
        this.setTitle(common.getTitle());
    }
    
    /**
     * Set common DTOI properties.
     * @param dto DTO.
     */
    protected void populateDTO(ModuleItemDTO dto)
    {
        dto.setTitle(title_);
    }
    
    public abstract ModuleItemDTO toDTO();
    
    /**
     * Returns list of data transfer objects.
     * @param moduleItems Module items.
     * @return List.
     */
    public static List<ModuleItemDTO> toDTOs(List<ModuleItem> moduleItems)
    {
        List<ModuleItemDTO> dtos = new ArrayList<>();
        moduleItems.forEach(moduleItem -> {
            ModuleItemDTO dto = moduleItem.toDTO();
            dtos.add(dto);
        });
        return dtos;
    }

}
