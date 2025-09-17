package edu.icesi.fitness.model;

import jakarta.persistence.*;

@Entity
@Table(name="permissions")
public class Permission {
    @Id
    private String id;
    private String name;
    public Permission(){}

    public String getId(){
        return id;}

    public void setId(String id){
        this.id=id;}
    public String getName(){
        return name;}
    public void setName(String name){

        this.name=name;}
}
