package com.mycompany.mavenchat.room;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mycompany.mavenchat.servis.loginBroadcaster;
import com.mycompany.mavenchat.servis.MongoDB;
import com.mycompany.mavenchat.servis.Session;
import com.mycompany.mavenchat.user.UserView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class RoomView extends CssLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        if (new Session().getAttribute("Login") != null) {
            this.createPage();
        } else {
            UI.getCurrent().getNavigator().navigateTo("login");
        }
    }

    public void createPage() {
        GridLayout userLayout = new GridLayout();

        userLayout.setMargin(true);
        userLayout.setSpacing(true);
        userLayout.setColumns(4);
        userLayout.setRows(4);

        addComponent(new UserView().createPage());
        addComponent(userLayout);

        initRooms();
        List<RoomData> roomsList = new MongoDB().findRooms();

        for (RoomData room : roomsList) {
            Button btn = new Button(room.getName());
            btn.setId(room.getName());

            btn.addClickListener(new ClickListener() {
                private static final long serialVersionUID = 1L;

                @Override
                public void buttonClick(ClickEvent event) {
                    loginToRoom(event);
                }
            });

            userLayout.addComponent(btn);
        }
    }

    void initRooms() {
        new MongoDB().addRoom("Gdańsk");
        new MongoDB().addRoom("50+");
        new MongoDB().addRoom("średniowiecze");
    }

    public void loginToRoom(ClickEvent event) {
        new Session().setAttribute("Room", event.getButton().getCaption());
        new MongoDB().updateDBUserRoom(new Session().getAttribute("Login"), new Session().getAttribute("Room"));
        UI.getCurrent().getNavigator().navigateTo("chat");

        loginBroadcaster.registerInRoom((loginBroadcaster.BroadcastListener) UI.getCurrent(), new Session().getAttribute("Room"));
    }
}
