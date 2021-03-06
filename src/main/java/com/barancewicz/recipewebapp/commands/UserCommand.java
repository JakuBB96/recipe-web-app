package com.barancewicz.recipewebapp.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class UserCommand {
    private Long id;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    @Size(min = 4, max = 16)
    private String password;
    @NotNull
    @NotEmpty
    @Size(min = 4, max = 16)
    private String matchingPassword;
    @NotNull
    @NotEmpty
    @Size(min = 4, max = 16)
    private String username;
    private String avatar;
}
