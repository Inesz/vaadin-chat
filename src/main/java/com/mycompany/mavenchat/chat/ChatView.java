package com.mycompany.mavenchat.chat;
import com.mycompany.mavenchat.servis.MongoDB;
import com.mycompany.mavenchat.servis.Session;
import com.mycompany.mavenchat.user.UserData;
import com.mycompany.mavenchat.user.UserView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.AlignmentInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
import java.util.List;

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
    HorizontalLayout chat = new HorizontalLayout();
    //Label chat = new Label();
    
    public void createPage(){
       
        messageLayout.addComponent(message);
        messageLayout.addComponent(emoticon);
        messageLayout.addComponent(send);       
        
        //chatLayout.addComponent(chat);
        chatLayout.addComponent(chat);
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
        
        usersPanel.setCaption("Czatowicze");
        
        //userPanel.setHeight("");
        userPanel.setHeight("30%");
        
        mainLayout.setWidth("100%");        
        mainLayout.setHeight("70%");
      
        setSizeFull();
        
         
 //usersPanel.setWidth(30.0f, Unit.PERCENTAGE);
 //usersLayout.setWidth(30.0f, Unit.PERCENTAGE);
  
 //p.setWidth("90%");
 /*
 usersPanel.setWidth("30%");
 usersLayout.setWidth("30%");
 
 mainLayout.setWidth("100%");
 chatLayout.setWidth("70%");
 //message.setWidth("100%");*/
 //setWidth("90%"); 
// mainMainLayout.setWidth("100%");
 //mainLayout.setSizeFull();
 //mainLayout.setComponentAlignment(chatLayout, new Alignment(AlignmentInfo.Bits.ALIGNMENT_LEFT));
 //mainLayout.setComponentAlignment(usersLayout, new Alignment(AlignmentInfo.Bits.ALIGNMENT_LEFT));
 
        
        //chat.setId("chat");
        
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
    
    public void addListOfUsers(){
        List<UserData> users = new MongoDB().getUsersFromRoom(new Session().getAttribute("Room"));
        
        for(UserData user : users){
            Button l = new Button(user.getLogin());
            l.setId(user.getLogin());
             l.setWidth("100%");
            usersLayout.addComponent(l);
        }  
    }
    
}
