package edu.icesi.fitness.model;

import jakarta.persistence.*;

@Entity
@Table(name="events")
public class Event {
    @Id
    private String id;
    private String name;
    private String userId;
    private String notificationId;
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getName(){return name;} public void setName(String n){this.name=n;}
    public String getUserId(){return userId;} public void setUserId(String u){this.userId=u;}
    public String getNotificationId(){return notificationId;} public void setNotificationId(String n){this.notificationId=n;}
}
