package com.mycompany.mavenchat.room;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;

public class RoomLogic extends CustomComponent {
    
    public void buttonClick(ClickEvent event) {
        event.getButton().getCaption();
    }
}
