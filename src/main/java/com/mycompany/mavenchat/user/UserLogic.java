package com.mycompany.mavenchat.user;
import com.vaadin.annotations.Push;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.mycompany.mavenchat.servis.loginBroadcaster.BroadcastListener;
import com.mycompany.mavenchat.servis.loginBroadcaster;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import java.util.ArrayList;
import java.util.List;

@Push
public class UserLogic {
    private UserView view;

    public UserLogic(UserView userView) {
        view = userView;
        
    }
    
    public int checkLoginUnique(String login, ArrayList<UserData> usersData){
        for(UserData user : usersData){
            if(user.login.equals(login))
                return 1;
        }
        return 0;
    }
    
    /*
    @Override
    protected void init(VaadinRequest request){
        //
    }
    */
}


/*
public class UserLogic {
   private UserView view;

    public UserLogic(UserView userView) {
        view = userView;
    }

    public void init() {
    }
    
    public void login(String login){
        //check on server if login empty
    }
    */
        /*
        editProduct(null);
        Hide and disable if not admin
        if (!MyUI.get().getAccessControl().isUserInRole("admin")) {
            view.setNewProductEnabled(false);
        }

        view.showProducts(DataService.get().getAllProducts());
*/
        /*
        view.getCo()
        new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                try {
                    binder.commit();
                } catch (EmptyValueException e) {
                    // A required value was missing
                } catch (CommitException e) {
                    for (Component c: formLayout)
                        ((AbstractField)c).setValidationVisible(true);
                }
            }
        }));
    }
(*/
   /*     
}
*/