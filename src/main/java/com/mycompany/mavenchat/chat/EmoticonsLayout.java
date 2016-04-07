package com.mycompany.mavenchat.chat;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Panel;
import java.util.List;

public class EmoticonsLayout extends CssLayout {
    private ChatLogic logic = new ChatLogic(this);
    
    Panel panel = new Panel();
    GridLayout grid = new GridLayout();

    public EmoticonsLayout() {
    }

    public Panel EmoticonsPanel() {
        grid.setColumns(3);
        grid.setRows(3);
        grid.setId("Emo");
        grid.setMargin(true);
        grid.setSpacing(true);
        panel.setContent(grid);

        addEmoButtons();
        return panel;
    }

    void addEmoButtons() {
        String themes ="\""+ logic.getBasePath() + "/VAADIN/themes/mytheme";
        //String themes = "http://localhost:8080/VAADIN/themes/mytheme";
        String icons = "icons/emoticons";

        logic.addBtnBgTransparent();
        
        List<String> names = logic.getEmoNames();
                
        for (String name : names) {
            Button EmoBtn = new Button();
            EmoBtn.setId(name);
            EmoBtn.setIcon(new ThemeResource(icons + "/" + name), ":)");
            grid.addComponent(EmoBtn);
            EmoBtn.setStyleName("btnBgTransparent");

            EmoBtn.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent event) {
                   logic.sendRoomMsg(logic.addLogin(logic.imgSrc(themes + "/" + EmoBtn.getIcon())));
                }
            });
        }
    }
}