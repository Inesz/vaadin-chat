package com.mycompany.mavenchat.room;

public class RoomData {
    private String name;
    
    public RoomData(){}
    
    public RoomData(String name){
        this.name=name;
    }
    
    public String getName(){
        return name;
    };
    
    void setName(String name){
    this.name=name;
    }    
}
