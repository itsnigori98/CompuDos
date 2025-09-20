package edu.icesi.fitness.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "progresses")
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer repetitions;
    private Integer time;

    @Column(name = "progress_date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Integer getId(){return id;} public void setId(Integer id){this.id=id;}
    public Integer getRepetitions(){return repetitions;} public void setRepetitions(Integer r){this.repetitions=r;}
    public Integer getTime(){return time;} public void setTime(Integer t){this.time=t;}
    public User getUserId(){return user;} public void setUserId(User u){this.user=u;}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
