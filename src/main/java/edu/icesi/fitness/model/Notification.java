package edu.icesi.fitness.model;

import jakarta.persistence.*;

@Entity
@Table(name="notifications")
public class Notification {
    @Id
    private String id;
    private String title;
    private String description;
    private String userId;
    private String trainerId;
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getTitle(){return title;} public void setTitle(String t){this.title=t;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public String getUserId(){return userId;} public void setUserId(String u){this.userId=u;}
    public String getTrainerId(){return trainerId;} public void setTrainerId(String t){this.trainerId=t;}
}
