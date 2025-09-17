package edu.icesi.fitness.model;

import jakarta.persistence.*;

@Entity
@Table(name="exercises")
public class Exercise {
    @Id
    private String id;
    private String name;
    private String type;
    private String description;
    private Integer setsCount;
    private Integer repetitions;
    private String routineId;
    private String videoUrl;
    private String difficulty;
    // getters/setters
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getType(){return type;} public void setType(String t){this.type=t;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public Integer getSetsCount(){return setsCount;} public void setSetsCount(Integer s){this.setsCount=s;}
    public Integer getRepetitions(){return repetitions;} public void setRepetitions(Integer r){this.repetitions=r;}
    public String getRoutineId(){return routineId;} public void setRoutineId(String r){this.routineId=r;}
    public String getVideoUrl(){return videoUrl;} public void setVideoUrl(String v){this.videoUrl=v;}
    public String getDifficulty(){return difficulty;} public void setDifficulty(String d){this.difficulty=d;}
}
