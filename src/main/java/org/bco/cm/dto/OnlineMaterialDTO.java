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

import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
public class OnlineMaterialDTO {
    
    private int materialId_;
    private String objective_;
    private URL resource_;
    private String materialType_;
    private int nextMaterialId_;
    
    public OnlineMaterialDTO()
    {
        materialId_ = 0;
        objective_ = null;
        resource_ = null;
        materialType_ = null;
        nextMaterialId_ = -1;
    }
    
    public void setMaterialId(int id)
    {
        materialId_ = id;
    }
    
    public int getMaterialId()
    {
        return materialId_;
    }
    
    public void setObjective(String objective)
    {
        objective_ = objective;
    }
    
    public String getObjective()
    {
        return objective_;
    }
    
    public void setResource(String resource)
    {
        try {
            resource_ = new URL(resource);
        } catch (MalformedURLException exception) {
            throw new IllegalArgumentException(
               resource + ": Not a URL. " + exception.getMessage()
            );
        }
    }
    
    public String getResource()
    {
        if ( resource_ != null ) {
            return resource_.toString();
        } else {
            return null;
        }
    }
    
    public URL getResourceURL()
    {
        if ( resource_ != null ) {
            return resource_;
        } else {
            return null;
        }
    }
    
    public void setMaterialType(String materialType)
    {
        materialType_ = materialType;
    }
    
    public String getMaterialType()
    {
        return materialType_;
    }    
    
    /**
     * Sets identifier value of next online material following the given 
     * online material.
     * @param id Identifier value.
     */
    public void setNextMaterialId(int id)
    {
        nextMaterialId_ = id;
    }
    
    /**
     * Returns identifier value of next online material following the given 
     * online material.
     * @return Identifier value. A value of -1 indicates that it has not (yet) 
     * been specified.
     */
    public int getNextMaterialId()
    {
        return nextMaterialId_;
    }
    
    @Override
    public String toString()
    {
        String newline = System.getProperty("line.separator");
        StringBuilder s = new StringBuilder("OnlineMaterialDTO : {").append(newline);
        s.append("materialId - ").append(materialId_).append(newline);
        s.append("objective - ").append(objective_).append(newline);
        s.append("resource - ").append(this.getResource()).append(newline);
        s.append("materialType - ").append(materialType_).append(newline);
        s.append("nextMaterialId - ").append(nextMaterialId_).append(newline);
        s.append("}").append(newline);
        return s.toString();
    }

}
