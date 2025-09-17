package edu.icesi.fitness.model;

import jakarta.persistence.*;

@Entity
@Table(name="trainers")
public class Trainer {
    @Id
    private String id;
    private String name;
    private String specialty;
    private String cellphone;
    // getters/setters
    public String getId(){

        return id;} public void setId(String id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getSpecialty(){return specialty;} public void setSpecialty(String s){this.specialty=s;}
    public String getCellphone(){return cellphone;} public void setCellphone(String c){this.cellphone=c;}
}
