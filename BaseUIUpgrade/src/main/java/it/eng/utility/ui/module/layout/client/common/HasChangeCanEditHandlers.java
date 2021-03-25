package it.eng.utility.ui.module.layout.client.common;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasChangeCanEditHandlers extends HasHandlers {
   
    HandlerRegistration addChangeCanEditHandler(ChangeCanEditHandler handler);
    
}
