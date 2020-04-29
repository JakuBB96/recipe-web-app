package com.barancewicz.recipewebapp.services;

import com.barancewicz.recipewebapp.commands.CommentCommand;

public interface CommentService {
    CommentCommand saveComment(CommentCommand command);
}
