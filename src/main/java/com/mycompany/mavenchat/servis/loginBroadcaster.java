package com.mycompany.mavenchat.servis;

import com.mycompany.mavenchat.room.RoomData;
import com.vaadin.ui.Notification;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.glassfish.gmbal.logex.Log;

public class loginBroadcaster {

    private static final List<BroadcastListener> listeners = new CopyOnWriteArrayList<BroadcastListener>();
    //private static final List<BroadcastListener> roomListeners = new CopyOnWriteArrayList<BroadcastListener>();
    private static final List<BroadcastRoom> listenersInRoom = new CopyOnWriteArrayList<BroadcastRoom>();
    
    public static void register(BroadcastListener listener) {
        listeners.add(listener);
    }
    
    public static void registerInRoom(BroadcastListener listener, String room) {
        for(BroadcastRoom listroom : listenersInRoom){
            if(listroom.getRoom().equals(room)){
                listroom.setListener(listener);
                return;
            }
        }
        
        BroadcastRoom newRoom = new BroadcastRoom();
        newRoom.setListener(listener);
        newRoom.setRoom(room);
        listenersInRoom.add(newRoom);
    }
    
    public static void broadcastInRoom(final String message, final String room){
        List<BroadcastListener> listeners = new ArrayList<BroadcastListener>();
        
        for(BroadcastRoom listroom : listenersInRoom){
            if(listroom.getRoom().equals(room)){
                listeners = listroom.getListener();
            }else{
                return;
            }
        }
        
        for (BroadcastListener listener : listeners){
            listener.receiveBroadcast(message);
        }
    }

    public static void unregister(BroadcastListener listener) {
        listeners.remove(listener);
    }
    
     public static void unregisterFromRoom(BroadcastListener listener, String room) {
        listeners.remove(listener);
        for(BroadcastRoom listroom : listenersInRoom){
            if(listroom.getRoom().equals(room)){
                listroom.removeListener(listener);
                return;
            }
        }
    }

    /*
    public static void broadcastLogin(final String message) {
        for (BroadcastListener listener : listeners) {
            listener.receiveBroadcast(message);
        }
    }
    */
    public static void broadcast(final String message) {
        for (BroadcastListener listener : listeners) {
            listener.receiveBroadcast(message);
        }
    }
    
     public static synchronized void broadcastRoom(final String message, String room) {
        
         for(BroadcastRoom listroom : listenersInRoom){
            if(listroom.getRoom().equals(room)){ 
                for (final BroadcastListener listener: listroom.getListener()){
                //executorService.execute(new Runnable() {
                //@Override
                //public void run() {
                
                System.out.println("wysyam: " + message);
                    listener.receiveBroadcast(message);
                }
                //}
            //});
                return;
    }
         }
     }
     
    
    public interface BroadcastListener {
        public void receiveBroadcast(String message);
        
        //public void receiveLoginBroadcast();
    }
}

    class BroadcastRoom implements Serializable{
private List<loginBroadcaster.BroadcastListener> listeners = new CopyOnWriteArrayList<loginBroadcaster.BroadcastListener>();;
private String room;

void BroadcastRoom(){}
//void BroadcastRoom(String room){this.room = room;}
/*void BroadcastRoom(String room, loginBroadcaster.BroadcastListener listener){
    this.room = room;
    this.listeners.add(listener);
    //setRoom(room);
    //setListener(listener);
}*/

public String getRoom(){
 return room;   
}

public void setRoom(String room){
 this.room = room;   
}

public List<loginBroadcaster.BroadcastListener> getListener(){
    return listeners;
}
    
public void setListener(loginBroadcaster.BroadcastListener listener){
    if(listener == null)System.out.println("null"); else System.out.println("not null"); 
    this.listeners.add(listener);
}

public void removeListener(loginBroadcaster.BroadcastListener listener){
    listeners.remove(listener);
}
}

