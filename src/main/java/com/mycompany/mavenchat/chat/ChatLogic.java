
package com.mycompany.mavenchat.chat;

import com.mycompany.mavenchat.servis.MongoDB;
import com.mycompany.mavenchat.servis.Session;
import com.mycompany.mavenchat.user.UserData;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ChatLogic {
    private ChatView chatView;
    private EmoticonsLayout emoLayout;

    ChatLogic(ChatView chatView) {
        this.chatView = chatView;  
    }
    
    ChatLogic(EmoticonsLayout emoLayout){
        this.emoLayout = emoLayout;        
    }
    
    public ArrayList<String> getEmoNames(){
        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+ "\\VAADIN\\themes\\mytheme\\icons\\emoticons\\";
        File file = new File(basepath);
        return new ArrayList<String>(Arrays.asList(file.list()));
    }
    
    public String imgSrc(String src){
        return "<img src=" + src + "\"/>";
    }
    
    public String addLogin(String msg){
        return new Session().getAttribute("Login") + " : " + msg;
    }
    
    public void sendRoomMsg(String msg){
         com.mycompany.mavenchat.servis.loginBroadcaster.broadcastRoom(msg, new Session().getAttribute("Room"));
    }
    
    //http://localhost:8080
    public String getBasePath(){
        return Page.getCurrent().getLocation().getScheme() + "://" + 
                Page.getCurrent().getLocation().getHost() + ":" +
                Page.getCurrent().getLocation().getPort();
    }
    
    public void addBtnBgTransparent(){
        Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".btnBgTransparent{background: transparent !important; border: none !important; box-shadow:none !important;}") ;
    }
    
    public void addIconWidth(){
    Page.Styles styles = Page.getCurrent().getStyles();
        styles.add(".icon img{ width: 1em; }");
    }
    
    public List<UserData> getUsersFromRoom(){
        return new MongoDB().getUsersFromRoom(new Session().getAttribute("Room"));
    }
}
