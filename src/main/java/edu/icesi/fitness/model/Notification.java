package edu.icesi.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;


    @ManyToMany(mappedBy = "notifications")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    public Integer getId(){return id;} public void setId(Integer id){this.id=id;}
    public String getTitle(){return title;} public void setTitle(String t){this.title=t;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
