package edu.icesi.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private Date date;


    @Column(name = "userId", nullable = false)
    @OneToMany(mappedBy = "")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "notificationid", nullable = false, unique = true)
    private Notification notificationId;

    public Integer getId(){return id;} public void setId(Integer id){this.id=id;}
    public String getName(){return name;} public void setName(String n){this.name=n;}
    public Notification getNotificationId(){return notificationId;} public void setNotificationId(Notification n){this.notificationId=n;}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<User> getUsers() {
        return users;
    }
}
