package com.mycompany.mavenchat;
import com.mycompany.mavenchat.chat.ChatView;
import com.mycompany.mavenchat.servis.MongoDB;
import com.mycompany.mavenchat.servis.loginBroadcaster;

import com.mycompany.mavenchat.room.RoomView;

import com.mycompany.mavenchat.user.UserView;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import com.vaadin.annotations.Push;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.mycompany.mavenchat.servis.loginBroadcaster.BroadcastListener;
import com.mycompany.mavenchat.servis.loginBroadcaster;
import com.mycompany.mavenchat.login.LoginView;
import com.mycompany.mavenchat.user.UserData;
import com.vaadin.annotations.Title;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Layout;


@Theme("valo")
@Title("Welcome")
//@Widgetset("com.mycompany.mavenchat.MyAppWidgetset")
@Push
public class MyUI extends UI implements BroadcastListener{
           
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Navigator navigator = new Navigator(this, this);
        navigator.addView("login", LoginView.class);
        navigator.addView("room", RoomView.class);
        navigator.addView("chat", ChatView.class);
        
        navigator.navigateTo("login");   
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
    
    @Override
    public void detach() {
        loginBroadcaster.unregister(this);
        super.detach();
    }

    @Override
    public void receiveBroadcast(final String message) {
        access(new Runnable() {
            @Override
            public void run() {
                ((Layout) getContent()).addComponent(new Label(message));

                /*
                Layout parent = (Layout) getContent();
                String id = "chat";
                for ( Component c : parent ) {
    if ( id.equals( c.getId() ) ) {
      c.
              //addComponent(new Label(message));
    }
  }
                */
                //((Layout) getContent()).addComponent(new Label(message));
               // getPage().findElement(By.id("chat")).addComponent(new Label(message);
               // ((Layout) getContent()).
                        
                        //findElement(By.id("chat")).addComponent(new Label(message);
                
                /*Notification n = new Notification("Message received",
                        message, Type.TRAY_NOTIFICATION);
                n.show(getPage());*/
                
                //getPage().paintContent(target);
            }
        });
    }
}
