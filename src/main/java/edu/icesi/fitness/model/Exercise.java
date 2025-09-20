package edu.icesi.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "description", nullable = false)
    private String description;


    @Column(name = "sets_count")
    private Integer setsCount;


    @Column(name = "repetitions")
    private Integer repetitions;

    @Column(name = "video_Url")
    private String videoUrl;


    // Relación ManyToMany con Routines
    @ManyToMany(mappedBy = "exercises")
    @JsonIgnore
    private List<Routine> routines = new ArrayList<>();


    // getters/setters
    public Integer getId(){return id;} public void setId(Integer id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getType(){return type;} public void setType(String t){this.type=t;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
    public Integer getSetsCount(){return setsCount;} public void setSetsCount(Integer s){this.setsCount=s;}
    public Integer getRepetitions(){return repetitions;} public void setRepetitions(Integer r){this.repetitions=r;}
    public String getVideoUrl(){return videoUrl;} public void setVideoUrl(String v){this.videoUrl=v;}

    public List<Routine> getRoutines() {
        return routines;
    }

    public void setRoutines(List<Routine> routines) {
        this.routines = routines;
    }
}
