package com.mycompany.mavenchat.login;

import com.mycompany.mavenchat.servis.MongoDB;
import com.mycompany.mavenchat.servis.Session;
import com.mycompany.mavenchat.user.UserData;
import com.vaadin.data.Validator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Page.Styles;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.AlignmentInfo.Bits;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class LoginView extends VerticalLayout implements View {

    VerticalLayout layout = new VerticalLayout();
    Label naglowek = new Label();
    Label opis = new Label();
    TextField login = new TextField();
    Label validacja = new Label();
    Button btn = new Button();

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        this.createPage();
    }

    public void createPage() {
        layout.addComponent(naglowek);
        layout.addComponent(opis);
        layout.addComponent(login);
        layout.addComponent(validacja);
        layout.addComponent(btn);
        this.addComponent(layout);

        naglowek.setCaption("Witaj!");
        naglowek.setContentMode(ContentMode.TEXT);
        naglowek.setId("naglowek");
        opis.setCaption("Chat z użyciem frameworku Vaadin. Wpisz login i kliknij \"Login\"");
        opis.setContentMode(ContentMode.TEXT);

        login.setRequired(true);
        login.setRequiredError("Login jest wymagany");
        login.setImmediate(true);
        login.setValidationVisible(true);
        login.setMaxLength(15);
        //login.addValidator(new StringLengthValidator("Must not be empty", 1, 100, false));
        //login.
        login.setInputPrompt("Login");
        login.setDescription("Login");
        login.focus();

        btn.setCaption("Zaloguj");
        btn.setWidth("40%");
        
        naglowek.setSizeUndefined();
        opis.setSizeUndefined();
        validacja.setSizeUndefined();
        layout.setComponentAlignment(naglowek, new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
        layout.setComponentAlignment(opis, new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
        layout.setComponentAlignment(login, new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
        layout.setComponentAlignment(btn, new Alignment(Bits.ALIGNMENT_VERTICAL_CENTER | Bits.ALIGNMENT_HORIZONTAL_CENTER));
    
       
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

       
        Styles styles = Page.getCurrent().getStyles();
        styles.add(".naglowek{ font-size: 250%; }");
        styles.add(".v-app .btn{background-color: #33CC00;  color: white;}");
        styles.add(" .mymargins.v-margin-left   {padding:   10pt;}");
        //naglowek.setStyleName("naglowek");
        //btn.setStyleName("btn");
        layout.setStyleName("mymargins");
       
        
        btn.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
               
                if (login.getValue().isEmpty()) {
                    validacja.setCaption("podaj login");
                } else {

                    MongoDB db = new MongoDB();
                    if (db.findLogin(login.getValue())) {
                        validacja.setCaption("login zajęty");
                    } else {
                        UserData user = new UserData(login.getValue());
                        db.addUser(user);
                        new Session().setAttribute("Login", login.getValue());
                        UI.getCurrent().getNavigator().navigateTo("room");
                    }
                }
            }  
     });
    }
}
    /*
    class LoginExistValidator implements Validator {
    @Override
    public void validate(Object value)
            throws Validator.InvalidValueException {
        if (!(value instanceof String &&
                ((String)value).equals("hello")))
            throw new Validator.InvalidValueException("Login zajęty");
    }
        
        
        
        if (login.getValue().isEmpty()) {
            throw new Validator.InvalidValueException("Login zajęty");
                    validacja.setCaption("podaj login");
                } else {

                    MongoDB db = new MongoDB();
                    if (db.findLogin(login.getValue())) {
                        validacja.setCaption("login zajęty");
                    } else {
                        UserData user = new UserData(login.getValue());
                        db.addUser(user);
                        new Session().setAttribute("Login", login.getValue());
                        UI.getCurrent().getNavigator().navigateTo("room");
                    }
                }
    }
 
}
*/


