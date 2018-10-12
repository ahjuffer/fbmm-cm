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

import java.io.Serializable;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.persistence.Column;
import javax.persistence.Embeddable;
/**
 * An email address
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 */
@Embeddable
public class EmailAddress implements Serializable {
    
    private InternetAddress value_;
    
    protected EmailAddress()
    {
        value_ = null;
    }
    
    private void setValue(String value) throws AddressException
    {
        value_ = new InternetAddress(value);
    }
    
    @Column( name = "email_address" )
    protected String getValue()
    {
        return value_.toString();
    }
    
    /**
     * Returns email address as <code>something@example.com</code>.
     * @return Value.
     */
    public String stringValue()
    {
        return this.getValue();
    }

    /**
     * Returns new email address.
     * @param value Email address. Must be of the form 
     * <code>something@example.com</code>.
     * @return Email address.
     */
    public static EmailAddress valueOf(String value)
    {
        try {
            if ( value == null ) {
                throw new NullPointerException("No email address provided.");
            }
            if ( value.isEmpty() ) {
                throw new IllegalArgumentException("Empty email address provided.");
            }
            if ( value.indexOf('@') == -1 ) {
                throw new AddressException("Missing @ character.");
            }
            EmailAddress emailAddress = new EmailAddress();            
            emailAddress.setValue(value);
            return emailAddress;
        } catch (AddressException exception) {
            throw new IllegalArgumentException(
                value + ": Not a valid email address. " + exception.getMessage(),
                exception
            );
        }
    }
    
    /**
     * Returns string representation.
     * @return Email address.
     * @see #stringValue() 
     */
    @Override
    public String toString()
    {
        return this.stringValue();
    }
}
