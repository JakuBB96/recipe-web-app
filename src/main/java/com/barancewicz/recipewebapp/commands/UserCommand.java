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
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Size(min = 4, max = 16)
    private String password;
    @NotEmpty
    @Size(min = 4, max = 16)
    private String matchingPassword;
    @NotEmpty
    @Size(min = 4, max = 16)
    private String username;
    private String avatar;
}
