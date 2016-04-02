package com.mycompany.mavenchat.chat;

import com.mycompany.mavenchat.servis.MongoDB;
import com.mycompany.mavenchat.servis.Session;
import com.mycompany.mavenchat.user.UserData;
import com.mycompany.mavenchat.user.UserView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.AlignmentInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatView extends CssLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        this.createPage();
    }

    HorizontalLayout mainLayout = new HorizontalLayout();
    Panel usersPanel = new Panel();
    VerticalLayout usersLayout = new VerticalLayout();
    VerticalLayout chatLayout = new VerticalLayout();
    HorizontalLayout messageLayout = new HorizontalLayout();

    Button send = new Button();
    Button emoticon = new Button();
    TextField message = new TextField();
    Panel userPanel;
    Panel chatPanel = new Panel();
    VerticalLayout chat = new VerticalLayout();

    public void createPage() {

        messageLayout.addComponent(message);
        messageLayout.addComponent(emoticon);
        messageLayout.addComponent(send);

        chatPanel.setContent(chat);
        chatLayout.addComponent(chatPanel);
        chatLayout.addComponent(messageLayout);

        mainLayout.addComponent(chatLayout);
        usersPanel.setContent(usersLayout);
        mainLayout.addComponent(usersPanel);

        userPanel = new UserView().createPage();
        addComponent(userPanel);
        addComponent(mainLayout);

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.setSizeFull();
        chat.setId("chat");
        emoticon.setIcon(new ThemeResource("icons/vaadin-icons-png/smiley-o.png"), ":)");

        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".icon img{ width: 1em; }");
        emoticon.setStyleName("icon");
              
        chatLayout.addComponent(new Emoticons().EmoticonsPanel());

        usersPanel.setCaption("Czatowicze");
        userPanel.setHeight("30%");

        mainLayout.setWidth("100%");
        mainLayout.setHeight("70%");

        setSizeFull();
        chat.setHeight("10em");
        //chatPanel.setWidth("5em");
        chatPanel.setHeight("10em");
        chat.setHeight("100%");
        chat.setMargin(true);

        send.setCaption("Wyślij");
        send.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                //import com.mycompany.mavenchat.server.loginBroadcaster;
                String msg = new Session().getAttribute("Login") + " : " + message.getValue();
                com.mycompany.mavenchat.servis.loginBroadcaster.broadcastRoom(msg, new Session().getAttribute("Room"));
                message.setValue("");
            }
        });

        addListOfUsers();      
    }

    public void addListOfUsers() {
        List<UserData> users = new MongoDB().getUsersFromRoom(new Session().getAttribute("Room"));

        for (UserData user : users) {
            Button l = new Button(user.getLogin());
            l.setId(user.getLogin());
            l.setWidth("100%");
            usersLayout.addComponent(l);
        }
    }
}

class Emoticons extends CssLayout {

    Panel panel = new Panel();
    GridLayout grid = new GridLayout();

    public Emoticons() {
    }

    public Panel EmoticonsPanel() {
        grid.setColumns(2);
        grid.setRows(2);
        grid.setId("Emo");
        grid.setMargin(true);
        grid.setSpacing(true);
        panel.setContent(grid);
   
        addEmoButtons();
        return panel;
    }

   void addEmoButtons() {
        String themes = "http://localhost:8080/VAADIN/themes/mytheme";
        String icons = "icons/emoticons";
 
        List<String> names = new ArrayList<String>();
        names.add("amor.gif");
        names.add("hejka.gif");
        names.add("kumpel.gif");
        names.add("nakoniu.gif");
        names.add("tanytany.gif");

        for (String name : names) { 
            String path = icons+"/"+name;
                System.out.println(path);
                Button l = new Button();
                l.setId(name);
                //l.setWidth("100%");
                l.setIcon(new ThemeResource(path), ":)");
                grid.addComponent(l);
                
                l.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                String src = "<img src=" + themes+"/"+l.getIcon() + ">";
                String msg = new Session().getAttribute("Login") + " : " + src;
                com.mycompany.mavenchat.servis.loginBroadcaster.broadcastRoom(msg, new Session().getAttribute("Room"));
            }
        });
        }
    }
}