//package com.barancewicz.recipewebapp.services;
//
//import com.barancewicz.recipewebapp.commands.CommentCommand;
//import com.barancewicz.recipewebapp.converters.CommentCommandToComment;
//import com.barancewicz.recipewebapp.converters.CommentToCommentCommand;
//import com.barancewicz.recipewebapp.converters.RecipeCommandToRecipe;
//import com.barancewicz.recipewebapp.converters.UserCommandToUser;
//import com.barancewicz.recipewebapp.domain.Comment;
//import com.barancewicz.recipewebapp.domain.Recipe;
//import com.barancewicz.recipewebapp.domain.User;
//import com.barancewicz.recipewebapp.repositories.RecipeRepository;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CommentServiceImpl implements CommentService {
//
//    private final RecipeRepository recipeRepository;
//    private final RecipeService recipeService;
//    private final UserService userService;
//    private final CommentCommandToComment commentCommandToComment;
//    private final CommentToCommentCommand commentToCommentCommand;
//    private final RecipeCommandToRecipe recipeCommandToRecipe;
//    private final UserCommandToUser userCommandToUser;
//
//    public CommentServiceImpl(RecipeRepository recipeRepository, RecipeService recipeService, UserService userService, CommentCommandToComment commentCommandToComment, CommentToCommentCommand commentToCommentCommand, RecipeCommandToRecipe recipeCommandToRecipe, UserCommandToUser userCommandToUser) {
//        this.recipeRepository = recipeRepository;
//        this.recipeService = recipeService;
//        this.userService = userService;
//        this.commentCommandToComment = commentCommandToComment;
//        this.commentToCommentCommand = commentToCommentCommand;
//        this.recipeCommandToRecipe = recipeCommandToRecipe;
//        this.userCommandToUser = userCommandToUser;
//    }
//
//    @Override
//    public CommentCommand saveComment(CommentCommand command) {
//        Recipe recipe = recipeCommandToRecipe.convert(command.getRecipe());
//        User user = userCommandToUser.convert(command.getUser());
//
//
//        Comment comment = commentCommandToComment.convert(command);
//        comment.setRecipe(recipe);
//        recipe.addComment(comment);
//        comment.setUser(user);
//        user.getComments().add(comment);
//
//        userService.saveOrUpdate(user);
//        Recipe savedRecipe = recipeRepository.save(recipe);
//
//
//        savedRecipe.getComments().forEach(comment1 -> System.out.println(comment1.getText()));
//
//        CommentCommand savedComment = commentToCommentCommand.convert(comment);
//        savedComment.setRecipe(command.getRecipe());
//        savedComment.setUser(command.getUser());
//        return savedComment;
//    }
//}
