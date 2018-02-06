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

package org.bco.cm.application.command.handler;

import com.tribc.cqrs.domain.command.AbstractCommand;
import com.tribc.ddd.domain.event.EventBus;
import com.tribc.ddd.domain.event.Eventful;
import com.tribc.ddd.domain.handling.AbstractHandler;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Command handler with an event bus.
 * @author Andr&#233; H. Juffer, Biocenter Oulu
 * @param <C> Command type.
 */
public abstract class CmCommandHandler<C extends AbstractCommand> 
    extends AbstractHandler<C> {
    
    static final Logger LOGGER = LogManager.getLogger("crapp");
    
    @Autowired
    private EventBus eventBus_;
    
    protected CmCommandHandler()
    {
        super();        
    }
    
    @Override
    public abstract void handle(C command);

    /**
     * Handle raised domain events asynchronously.
     * @param eventful Object that may have raised domain events.
     */
    protected void handleEventsAsync(Eventful eventful)
    {
        LOGGER.info(
            "Object of type " + eventful.getClass().getName() + " holds " + 
            eventful.getEvents().size() + " domain event(s)."
        );
        eventBus_.handleAsync(eventful);
    }
    
    /**
     * Handles events in the same thread.
     * @param eventful Object that may have raised domain events.
     */
    protected void handleEvents(Eventful eventful)
    {
        LOGGER.info(
            "Object of type " + eventful.getClass().getName() + " holds " + 
            eventful.getEvents().size() + " domain event(s)."
        );
        eventBus_.handle(eventful);
    }

}
