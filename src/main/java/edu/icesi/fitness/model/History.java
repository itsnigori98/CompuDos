package edu.icesi.fitness.model;

import jakarta.persistence.*;

@Entity
@Table(name="history")
public class History {
    @Id
    private String id;
    private String description;
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getDescription(){return description;} public void setDescription(String d){this.description=d;}
}
