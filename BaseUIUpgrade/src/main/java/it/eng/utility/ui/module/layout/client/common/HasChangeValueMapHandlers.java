package it.eng.utility.ui.module.layout.client.common;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasChangeValueMapHandlers extends HasHandlers {
   
    HandlerRegistration addChangeValueMapHandler(ChangeValueMapHandler handler);
    
}
