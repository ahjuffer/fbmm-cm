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

import java.util.Collection;
import java.util.HashSet;

/**
 * DTO for LearningPath.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class LearningPathDTO {
    
    private final Collection<OnlineMaterialDTO> onlineMaterials_;
    private int firstMaterialId_;
    
    public LearningPathDTO()
    {
        onlineMaterials_ = new HashSet<>();
        firstMaterialId_ = -1;
    }
    
    public void addOnlineMaterial(OnlineMaterialDTO material)
    {
        onlineMaterials_.add(material);
    }
    
    public void setOnlineMaterials(Collection<OnlineMaterialDTO> onlineMaterials)
    {
        onlineMaterials_.clear();
        onlineMaterials_.addAll(onlineMaterials);
    }
    
    public Collection<OnlineMaterialDTO> getOnlineMaterials()
    {
        return onlineMaterials_;
    }
    
    /**
     * Set identifier value of the first online material in the learning path.
     * @param id Identifier value.
     */
    public void setFirstMaterialId(int id)
    {
        firstMaterialId_ = id;
    }

    /**
     * Returns identifier value of the online material in the learning path.
     * @return Identifier value. A value of -1 indicates that it has not (yet) been
     * specified.
     */
    public int getFirstMaterialId()
    {
        return firstMaterialId_;
    }
    
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("LearningPathDTO : {").append(newline);
        s.append("onlineMaterials - ").append(onlineMaterials_).append(newline);
        s.append("number of online materials - ").append(onlineMaterials_.size()).append(newline);
        s.append("firstMaterialId - ").append(firstMaterialId_).append(newline);
        s.append("}");
        return s.toString();
    }

}
