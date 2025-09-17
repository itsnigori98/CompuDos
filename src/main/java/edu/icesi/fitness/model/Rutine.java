package edu.icesi.fitness.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="routines")
public class Rutine {
    @Id
    private String id;
    private String type;
    private String userId;
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="routineId", insertable=false, updatable=false)
    private List<Exercise> exercises = new ArrayList<>();
    // getters/setters
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getType(){return type;} public void setType(String t){this.type=t;}
    public String getUserId(){return userId;} public void setUserId(String u){this.userId=u;}
    public List<Exercise> getExercises(){return exercises;} public void setExercises(List<Exercise> e){this.exercises=e;}
}
