package com.mycompany.mavenchat.user;

import com.mycompany.mavenchat.MyUI;
import com.mycompany.mavenchat.servis.loginBroadcaster;
import com.mycompany.mavenchat.servis.MongoDB;
import com.mycompany.mavenchat.servis.Session;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class UserView extends CssLayout {
    //public static final String VIEW_NAME = "user";  
    //private UserLogic viewLogic = new UserLogic(this);
    
    Panel userPanel = new Panel();
    VerticalLayout userLayout = new VerticalLayout();
    private Label login = new Label();
    private Label room = new Label();
    private Button logOut = new Button("Wyloguj");

    public Panel createPage() {     
        userLayout.addComponent(login);
        userLayout.addComponent(room);
        userLayout.addComponent(logOut);
        userPanel.setContent(userLayout);
        //setSizeFull();
       
        login.setCaption("Login: " + new Session().getAttribute("Login"));
        login.setContentMode(ContentMode.TEXT);
        room.setCaption("Pokój: " + setRoomLabel());
        room.setContentMode(ContentMode.TEXT);
        logOut.setId("logOut");
        logOut.setCaption("Wyloguj");
        login.setSizeUndefined();
        room.setSizeUndefined();
        
        //userPanel.setWidth("90%");
        //userPanel.setWidth("90%");
        //userPanel.setHeight("10%");
        //userPanel.setHeight("10%");
        userLayout.setMargin(true);
        

        logOut.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                loginBroadcaster.unregisterFromRoom((loginBroadcaster.BroadcastListener)UI.getCurrent(), new Session().getAttribute("Room"));
                loginBroadcaster.unregister((loginBroadcaster.BroadcastListener)UI.getCurrent());
                new MongoDB().removeUser(new Session().getAttribute("Login")); 
                new MongoDB().removeUser(new Session().getAttribute("Room")); 
                UI.getCurrent().getNavigator().navigateTo("login");
                getUI().getSession().close();
            }
        });

        return userPanel;
    }
    
    private String setRoomLabel(){
        if(new Session().getAttribute("Room")==null)
            return "Wybierz pokój";
        else
            return new Session().getAttribute("Room");
    }

}
