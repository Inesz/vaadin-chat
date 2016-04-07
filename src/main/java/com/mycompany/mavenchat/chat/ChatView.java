package com.mycompany.mavenchat.chat;

import com.mycompany.mavenchat.user.UserData;
import com.mycompany.mavenchat.user.UserView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupView;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.List;

public class ChatView extends CssLayout implements View {

    private ChatLogic logic = new ChatLogic(this);

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
    PopupView popup;

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

        logic.addIconWidth();
        emoticon.setStyleName("icon");

        popup = new PopupView(null, new EmoticonsLayout().EmoticonsPanel());
        chatLayout.addComponent(popup);

        usersPanel.setCaption("Czatowicze");
        usersPanel.setHeight("15em");

        mainLayout.setWidth("100%");
        mainLayout.setHeight("70%");

        setWidth("100%");
        chatPanel.setHeight("15em");
        chat.setSizeUndefined();
        chat.setMargin(true);

        send.setCaption("Wyślij");
        
        emoticon.addClickListener(event -> popup.setPopupVisible(true));
        send.addClickListener(event -> {
            logic.sendRoomMsg(logic.addLogin(message.getValue()));
            message.setValue("");
        }
        );

        addListOfUsers();
    }

    public void addListOfUsers() {
        List<UserData> users = logic.getUsersFromRoom();

        for (UserData user : users) {
            Button btn = new Button(user.getLogin());
            btn.setId(user.getLogin());
            btn.setWidth("100%");
            usersLayout.addComponent(btn);
        }
    }
}
