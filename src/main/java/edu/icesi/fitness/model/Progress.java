package edu.icesi.fitness.model;

import jakarta.persistence.*;

@Entity
@Table(name="progress")
public class Progress {
    @Id
    private String id;
    private Integer repetitions;
    private String time; // store as text for simplicity
    private String difficulty;
    private String userId;
    private String historyId;
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public Integer getRepetitions(){return repetitions;} public void setRepetitions(Integer r){this.repetitions=r;}
    public String getTime(){return time;} public void setTime(String t){this.time=t;}
    public String getDifficulty(){return difficulty;} public void setDifficulty(String d){this.difficulty=d;}
    public String getUserId(){return userId;} public void setUserId(String u){this.userId=u;}
    public String getHistoryId(){return historyId;} public void setHistoryId(String h){this.historyId=h;}
}
