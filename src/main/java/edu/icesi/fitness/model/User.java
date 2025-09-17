package edu.icesi.fitness.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    private String id;
    private String name;
    private Integer age;
    private Double weight;
    private String sex;
    private String type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
       joinColumns = @JoinColumn(name="user_id"),
       inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {}
    // getters & setters
    // (omitted for brevity in this scaffold - implement as needed)
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public Integer getAge(){return age;} public void setAge(Integer age){this.age=age;}
    public Double getWeight(){return weight;} public void setWeight(Double weight){this.weight=weight;}
    public String getSex(){return sex;} public void setSex(String sex){this.sex=sex;}
    public String getType(){return type;} public void setType(String type){this.type=type;}
    public Set<Role> getRoles(){return roles;} public void setRoles(Set<Role> roles){this.roles=roles;}
}
