package com.mycompany.mavenchat.servis;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ReflectionDBObject;
import com.mongodb.util.JSONCallback;
import com.mycompany.mavenchat.room.RoomData;
import com.mycompany.mavenchat.user.UserData;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bson.BasicBSONDecoder;
import org.bson.BasicBSONEncoder;

public class MongoDB extends ReflectionDBObject{
    
    public void MongoDB(){}
    
    public DB connectDB(){
       
        try {
                                    MongoClient mongoClient = new MongoClient( "localhost" );
                                    return mongoClient.getDB( "mydb" );
                                    
                                   } catch (UnknownHostException e) {
                                      //new Notification("blad polaczenia:"+e, "b").show(Page.getCurrent());
           return null;
                                }
        
    }
    
    public void closeConnectDB(DB db){
       db.getMongo().close();
    }  
    
    public boolean findLogin(String login){
        
        DB db = connectDB();
        DBCollection dbCollection = db.getCollection("user");
        BasicDBObject searchQuery = new BasicDBObject("Login", login);
	DBCursor cursor = dbCollection.find(searchQuery);
        
        
        if (cursor.hasNext()) {
                closeConnectDB(db);
                return true;
	}
        closeConnectDB(db);
        return false;
    }
    
    public void addUser(UserData user){
        DB db = connectDB();
                
        if(db!=null){
        DBCollection dbCollection = db.getCollection("user");
        dbCollection.save(user);
        
        }else{
            //throw new Exception ("błąd połączenia z bazą");
        }
        
        closeConnectDB(db);
    }
    
    public List<UserData> getUsersFromRoom(String room){
        List<UserData> users = new CopyOnWriteArrayList<UserData>();
        
        DB db = connectDB();
        DBCollection dbCollection = db.getCollection("user");
        BasicDBObject searchQuery = new BasicDBObject("Room", room);
	DBCursor cursor = dbCollection.find(searchQuery);
        
        while (cursor.hasNext()) {
            DBObject user = cursor.next();
                users.add(new UserData(user.get("Login").toString(),user.get("Room").toString()));
	}
        return users;
    }
    
    //dodaje room do odpowiedniego usera
    public void updateDBUserRoom(String login, String room){
        DB db = connectDB();
        DBCollection dbCollection = db.getCollection("user");                       
        BasicDBObject searchQuery = new BasicDBObject().append("Login", login);
        BasicDBObject newDocument = new BasicDBObject();
	newDocument.append("$set", new BasicDBObject().append("Room", room));
        dbCollection.update(searchQuery, newDocument);
        closeConnectDB(db);
    }
    
        public void removeUser(String login){
        DB db = connectDB();
        DBCollection dbCollection = db.getCollection("user");       	
BasicDBObject removeQuery = new BasicDBObject();
removeQuery.append("Login", login);
dbCollection.remove(removeQuery);



        closeConnectDB(db);
    }
    
    public void addRoom(String room){
        DB db = connectDB();
                
        if(db!=null && findRoom(room)!=true){
        DBCollection dbCollection = db.getCollection("room");
        dbCollection.save(new BasicDBObject("Room", room));
        closeConnectDB(db);
        }else{
            //throw new Exception ("błąd połączenia z bazą");
       }
    }
    
    public boolean findRoom(String room){
        
        DB db = connectDB();
        DBCollection dbCollection = db.getCollection("room");
        BasicDBObject searchQuery = new BasicDBObject("Room", room);
	DBCursor cursor = dbCollection.find(searchQuery);
        
        
        if (cursor.hasNext()) {
                closeConnectDB(db);
                return true;
	}
        closeConnectDB(db);
        return false;
    }
    
    public List<RoomData> findRooms(){
        List<RoomData> rooms = new CopyOnWriteArrayList<RoomData>();
        
        DB db = connectDB();
        DBCollection dbCollection = db.getCollection("room");       
	DBCursor cursor = dbCollection.find();
       
        while (cursor.hasNext()) {
                rooms.add(new RoomData(cursor.next().get("Room").toString()));
	}
        
        closeConnectDB(db);
        return rooms;
    }
    
    public void removeRoom(String room){
        DB db = connectDB();
        DBCollection dbCollection = db.getCollection("room");       	
BasicDBObject removeQuery = new BasicDBObject();
removeQuery.append("Room", room);
dbCollection.remove(removeQuery);
        closeConnectDB(db);
    }
    
}
    
    /*
public void clearUsers(){
         DB db = connectDB();
        DBCollection dbCollection = db.getCollection("user");       	
BasicDBObject removeQuery = new BasicDBObject();
//removeQuery.append("Login", login);
dbCollection.drop();
        closeConnectDB(db);
    }
    //pobierz rozmowe
     public boolean findChatInRoom(){
        DB db = connectDB();
        DBCollection dbCollection = db.getCollection("chat");
        
	DBCursor cursor = dbCollection.find();
        
        //System.out.println("findLogin " + login);
        if (cursor.hasNext()) {
		//System.out.println("cursor"+cursor.next());
                //return cursor.next().get("Login").toString();
                return true;
	}
        return false;
    }
    
     //dodaj mess go bazy
    public void addMessage(){
         
    };
    
    
    }
*/
        //BasicDBObject basicDBObject = new BasicDBObject(user.getLogin(),user);
        //basicDBObject.put(user.getLogin(),user);
        //dbCollection.insert(basicDBObject);

          /*
        BasicDBObject basicDBObject = new BasicDBObject(user.getLogin(),user);
        DBCollection dbCollection = db.getCollection("user");
        dbCollection.save(basicDBObject);
        */        
            
 /*
    public ArrayList<UserData> getRoomUsers(String Room){
        ArrayList<UserData> users = new ArrayList<UserData>();
        
        DB db = connectDB();
        DBCollection dbCollection = db.getCollection("user");
        //BasicDBObject searchQuery = new BasicDBObject("login", login);
	//searchQuery.put("login", login);
//basicDBObject
	DBCursor cursor = dbCollection.find();
       // BasicDBCursor cursor = dbCollection.find();
        
        System.out.println("allInCollection");
        while (cursor.hasNext()) {
		System.out.println("cursorColl"+cursor.next());
                //UserData u = 
                        //users.add((UserData) cursor.next());
                        UserData u;// = new UserData();
                        u = (UserData) cursor.next().get("Login");
                        System.out.println(" "+u.toString());
	}
        return users;
    }
*/
 /*
    public byte[] DBObjectToBSON(DBObject dbObject) {
    BasicBSONEncoder encoder = new BasicBSONEncoder();
    byte bson[] = encoder.encode(dbObject);
    return bson;
}

public DBObject BSONToDBObject(byte[] bson) {
    BasicBSONDecoder decoder = new BasicBSONDecoder();
    JSONCallback callback = new JSONCallback();
    decoder.decode(bson, callback);
    Object obj = callback.get();
    DBObject dbObject = (DBObject) obj;
    return dbObject;
}
*/
/*
public ArrayList<UserData> findUsersInRoom(String room){
        DB db = connectDB();
        DBCollection dbCollection = db.getCollection("user");
        BasicDBObject searchQuery = new BasicDBObject("Room", room);

	DBCursor cursor = dbCollection.find(searchQuery);
        
        ArrayList<UserData> users = new ArrayList<UserData>();
        
        if (cursor.hasNext()){
                users.add(new UserData(cursor.next().get("Login").toString(),cursor.next().get("Login").toString()));
        }
        
        return users;
    };
*/
