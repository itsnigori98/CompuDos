package edu.icesi.fitness.model;

import jakarta.persistence.*;

@Entity
@Table(name="trainers")
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "specialty", nullable = false)
    private String specialty;

    @Column(name = "cellphone", nullable = false)
    private String cellphone;


    // getters/setters
    public Integer getId(){
        return id;} public void setId(Integer id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getSpecialty(){return specialty;} public void setSpecialty(String s){this.specialty=s;}
    public String getCellphone(){return cellphone;} public void setCellphone(String c){this.cellphone=c;}
}
