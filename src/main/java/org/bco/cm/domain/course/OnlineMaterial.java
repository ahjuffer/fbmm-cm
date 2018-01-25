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

import java.net.MalformedURLException;
import java.net.URL;
import org.bco.cm.dto.OnlineMaterialDTO;

/**
 * Material that should be studied by the student to reach a study objective. 
 * Subsequent material to be consumed -after- the current material may be specified.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public abstract class OnlineMaterial {

    private int materialId_;
    private String objective_;
    private URL resource_;
    private OnlineMaterial next_;
    
    private OnlineMaterial()
    {
        materialId_ = 0;
        objective_ = null;
        resource_ = null;
        next_ = null;
    }
    
    protected OnlineMaterial(int materialId, String objective, URL resource)
    {
        materialId_ = materialId;
        objective_ = objective;
        resource_ = resource;
        next_ = null;
    }
    
    private void setMaterialId(int materialId)
    {
        materialId_ = materialId;
    }
    
    /**
     * Returns material identifier. Its value is unique onlu in context of the owning
     * learning path.
     * @return Identifier.
     */
    public int getMaterialId()
    {
        return materialId_;
    }
    
    private void setObjective(String objective)
    {
        objective_ = objective;
    }
    
    private String getObjective()
    {
        return objective_;
    }
    
    private void setResource(String url) 
        throws MalformedURLException
    {
        resource_ = new URL(url);
    }
    
    private String getResource()
    {
        return resource_.toString();
    }
    
    /**
     * Sets subsequent online material.
     * @param next Online material
     */
    void setNext(OnlineMaterial next)
    {
        next_ = next;
    }
    
    private OnlineMaterial getNext()
    {
        return next_;
    }
    
    /**
     * Returns material type.
     * @return Type.
     */
    protected abstract String getMaterialType();
    
    protected void setMaterialType(String materialType)
    {
        // Do nothing.
    }
    
    /**
     * Creates new online material.
     * @param materialId Local identifier.
     * @param spec New online material specification for course learning path. 
     * Must specify material type, objective and resource.
     * @return New online material.
     */
    static OnlineMaterial valueOf(int materialId, OnlineMaterialDTO spec)
    {
        String materialType = spec.getMaterialType();
        if ( materialType == null ) {
            throw new NullPointerException("Missing online material type.");
        }
        if ( materialType.isEmpty() ) {
            throw new IllegalArgumentException("Missing online material type.");
        }
        String objective = spec.getObjective();
        if ( objective == null ) {
            throw new NullPointerException("Missing objective for online material.");
        }
        if ( objective.isEmpty() ) {
            throw new IllegalArgumentException("Missing objective for online material.");
        }
        URL resource = spec.getResourceURL();
        if ( resource == null ) {
            throw new IllegalArgumentException("Missing resource for online material.");
        }
        switch (materialType) {
            case ReadingMaterial.MATERIAL_TYPE:
                return new ReadingMaterial(materialId, objective, resource);
            case VideoMaterial.MATERIAL_TYPE:
                return new VideoMaterial(materialId, objective, resource);
            default:
                throw new IllegalArgumentException(
                    materialType + ": Not valid online material for course learning path."
                );
        }
    }
    
    /**
     * Is there subsequent material.
     * @return Results
     */
    boolean hasNext()
    {
        return next_ != null;
    }
    
    /**
     * Continues to subsequent material. If there is no subsequent material,
     * this (online material) is returned.
     * @return Online material.
     */
    OnlineMaterial toNext()
    {
        if ( this.hasNext() ) {
            return this.getNext();
        } else {
            return this;
        }
    }
    
    /**
     * Returns date transfer object.
     * @return DTO.
     */
    OnlineMaterialDTO toDTO()
    {
        OnlineMaterialDTO dto = new OnlineMaterialDTO();
        dto.setMaterialId(materialId_);
        dto.setMaterialType(this.getMaterialType());
        dto.setObjective(objective_);
        dto.setResource(resource_.toString());
        if ( next_ != null ) {
            dto.setNextMaterialId(next_.getMaterialId());
        }
        return dto;
    }
    
}
