/*
 * The MIT License
 *
 * Copyright 2017 André H. Juffer, Biocenter Oulu
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

import java.time.Instant;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import org.bco.cm.dto.LearningPathDTO;
import org.bco.cm.dto.OnlineMaterialDTO;

/**
 * A route, taken by the student through a range of (commonly) e-learning 
 * activities, which allows the student to build knowledge progressively.
 * @author André H. Juffer, Biocenter Oulu
 */
public class LearningPath {
    
    // For generating online material identifiers that are unique to a given 
    // learning path.
    private final static Random RANDOM;
    
    static {
        RANDOM = new Random();
        RANDOM.setSeed(Instant.now().toEpochMilli());
    }
    
    private Map<Integer, OnlineMaterial> onlineMaterials_;
    private OnlineMaterial firstOnlineMaterial_;
    
    private LearningPath()
    {
        onlineMaterials_ = new HashMap<>();
        firstOnlineMaterial_ = null;
    }
    
    private void setOnlineMaterials(Map<Integer,OnlineMaterial> materials)
    {
        onlineMaterials_ = materials;
    }
    
    private Map<Integer,OnlineMaterial> getOnlineMaterials()
    {
        return onlineMaterials_;
    }
    
    private void setFirstOnlineMaterial(OnlineMaterial first)
    {
        firstOnlineMaterial_ = first;
    }
    
    private OnlineMaterial getFirstOnlineMaterial()
    {
        return firstOnlineMaterial_;
    }
    
    /**
     * Returns new but empty learning path.
     * @return New learning path.
     */
    static LearningPath empty()
    {
        return new LearningPath();
    }
    
    /**
     * Returns new learning path according to specification.
     * @param spec Learning path specification.
     * @return New learning path. May be empty still, if spec did not include
     * online materials.
     */
    static LearningPath valueOf(LearningPathDTO spec)
    {
        if ( spec == null ) {
            throw new NullPointerException("Missing learning path specification.");
        }
        LearningPath learningPath = new LearningPath();
        for (OnlineMaterialDTO dto : spec.getOnlineMaterials()) {
            learningPath.addOnlineMaterial(dto);
        }
        return learningPath;
    }
    
    /**
     * Adds new online material.
     * @param spec New online material.
     */
    void addOnlineMaterial(OnlineMaterialDTO spec)
    {
        // Create material according to specification.
        int materialId = this.generateMaterialId();
        OnlineMaterial next = OnlineMaterial.valueOf(materialId, spec);
        
        // Set new next material for last material, if needed. Otherwise set
        // first online material.
        if ( this.isEmpty() ) {
            this.setFirstOnlineMaterial(next);
        } else {                    
            OnlineMaterial last = this.lastOnlineMaterial();
            last.setNext(next);
        }
        
        // Save material.
        onlineMaterials_.put(materialId, next);        
    }
    
    /**
     * Was online material added to this learning path.
     * @return Result.
     */
    boolean isEmpty()
    {
        return onlineMaterials_.isEmpty();
    }

    /**
     * Returns a data transfer object.
     * @return DTO.
     */
    LearningPathDTO toDTO()
    {
        LearningPathDTO dto = new LearningPathDTO();
        Collection<OnlineMaterialDTO> omdtos = new HashSet<>();
        for (OnlineMaterial material : onlineMaterials_.values()) {
            OnlineMaterialDTO omdto = material.toDTO();
            omdtos.add(omdto);
        }
        dto.setOnlineMaterials(omdtos);
        if ( !onlineMaterials_.isEmpty() ) {
            dto.setFirstMaterialId(this.getFirstOnlineMaterial().getMaterialId());
        }
        return dto;
    }
    
    private OnlineMaterial lastOnlineMaterial()
    {
        if ( onlineMaterials_.isEmpty() ) {
            throw new IllegalStateException(
                    "No online material specified for course learning path."
            );
        }
        OnlineMaterial last = this.firstOnlineMaterial();
        OnlineMaterial next = last.toNext();
        while ( next != last ) {
            last = next;
            next = last.toNext();
        }
        return last;
    }
    
    private OnlineMaterial firstOnlineMaterial()
    {
        if ( this.isEmpty() ) {
            throw new IllegalStateException(
                "No online material provided for learning material."
            );
        }
        return this.getFirstOnlineMaterial();
    }
    
    /**
     * Returns identifier only unique in context of this learning path.
     * @return Identifier.
     */
    private int generateMaterialId()
    {
        int bound = Integer.MAX_VALUE - 1;
        int id = RANDOM.nextInt(bound);
        while ( onlineMaterials_.containsKey(id) ) {
            id = RANDOM.nextInt(bound);
        }
        return id;
    }
    
}
