package edu.icesi.fitness.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name="roles")
public class Role {
    @Id
    private String id;
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="role_permissions",
      joinColumns=@JoinColumn(name="role_id"),
      inverseJoinColumns=@JoinColumn(name="permission_id"))
    private Set<Permission> permissions = new HashSet<>();

    public Role(){}
    public String getId(){return id;} public void setId(String id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public Set<Permission> getPermissions(){return permissions;} public void setPermissions(Set<Permission> p){this.permissions=p;}
}
