package com.barancewicz.recipewebapp.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Transient
    private String password;
    private String firstName;
    private String lastName;
    private String encryptedPassword;
    private String avatar;
    private Boolean enabled = true;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable
    // ~ defaults to @JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"),
    //     inverseJoinColumns = @joinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();
    private Integer failedLoginAttempts = 0;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "user")
    private List<Recipe> recipes = new ArrayList<>();
    public void addRole(Role role){
        if(!this.roles.contains(role)){
            this.roles.add(role);
        }
        if(!role.getUsers().contains(this)){
            role.getUsers().add(this);
        }
    }
    public void removeRole(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
    public Recipe addRecipe(Recipe recipe){
        this.recipes.add(recipe);
        recipe.setUser(this);
        return recipe;
    }
}
