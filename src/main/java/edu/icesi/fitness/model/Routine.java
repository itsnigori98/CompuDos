package edu.icesi.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="routines")
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;



    @ManyToMany(mappedBy = "routines")
    @JsonIgnore
    private List<User> users = new ArrayList<>();


    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "routine_exercises",
            joinColumns = @JoinColumn(name = "routine_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<Exercise> exercises = new ArrayList<>();

    public Routine(String name) {
        this.name = name;
    }

    public Routine() {

    }

    // getters/setters
    public Integer getId(){return id;} public void setId(Integer id){this.id=id;}
    public String getType(){return type;} public void setType(String t){this.type=t;}

    public List<Exercise> getExercises(){return exercises;} public void setExercises(List<Exercise> e){this.exercises=e;}

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
