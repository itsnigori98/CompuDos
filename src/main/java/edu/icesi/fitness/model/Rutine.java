package edu.icesi.fitness.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="rutines")
public class Rutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;



    @ManyToMany(mappedBy = "rutines")
    private List<User> users = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "rutine_exercises",
            joinColumns = @JoinColumn(name = "rutine_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    private List<Exercise> exercises = new ArrayList<>();

    public Rutine(String name) {
        this.name = name;
    }

    public Rutine() {

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
