package com.mycompany.mavenchat.user;

import com.mongodb.BasicDBObject;
import com.mongodb.ReflectionDBObject;
import java.io.Serializable;

public class UserData extends ReflectionDBObject{
    String login;
    String room;
    
    public UserData(){
    }
    
    public UserData(String login){
        this.login = login;
    }
    
    public UserData(String login, String room){
        this.login = login;
        this.room = room;
    }
    
    public String getLogin(){
        return login;
    }
    
    public void setLogin(String login){
        this.login = login;
    }  
    
    public String getRoom(){
        return room;
    }
    
    public void setRoom(String room){
        this.room = room;
    } 
    
}
