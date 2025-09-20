package edu.icesi.fitness.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "sex", nullable = false)
    private String sex;

    @Column(name = "type", nullable = false)
    private String type;


    @OneToMany(mappedBy = "user")
    private List<Progress> progresses = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "user_notifications", // tabla intermedia
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "notification_id")
    )
    private List<Notification> notifications = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "user_rutines", // tabla intermedia
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "rutine_id")
    )
    private List<Rutine> rutines = new ArrayList<>();


    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();



    public User() {}
    // getters & setters

    public Integer getId(){return id;} public void setId(Integer id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public Integer getAge(){return age;} public void setAge(Integer age){this.age=age;}
    public Double getWeight(){return weight;} public void setWeight(Double weight){this.weight=weight;}
    public String getSex(){return sex;} public void setSex(String sex){this.sex=sex;}
    public String getType(){return type;} public void setType(String type){this.type=type;}

    public List<Rutine> getRutines() {
        return rutines;
    }

    public void setRutines(List<Rutine> rutines) {
        this.rutines = rutines;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public List<Progress> getProgresses() {
        return progresses;
    }

    public void setProgresses(List<Progress> progresses) {
        this.progresses = progresses;
    }
}
