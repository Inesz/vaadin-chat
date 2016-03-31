package com.mycompany.mavenchat.servis;
import com.vaadin.server.VaadinSession;

public class Session {
    
    public void setAttribute(String attribute, String value){
            VaadinSession.getCurrent().setAttribute(attribute, value);
    }
    
    public String getAttribute(String attribute){
        Object room = VaadinSession.getCurrent().getAttribute(attribute);
        return room == null ? null : room.toString();
    }
    
    public void clearAttribute(String attribute){
        VaadinSession.getCurrent().setAttribute(attribute, null);
    }
    
}
