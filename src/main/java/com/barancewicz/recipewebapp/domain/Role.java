package com.barancewicz.recipewebapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@Entity
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String role;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    // ~ defaults to @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "role_id"),
    //     inverseJoinColumns = @joinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
    public void addUser(User user){
        if(!this.users.contains(user)){
            this.users.add(user);
        }
        if(!user.getRoles().contains(this)){
            user.getRoles().add(this);
        }
    }
    public void removeUser(User user){
        this.users.remove(user);
        user.getRoles().remove(this);
    }

}