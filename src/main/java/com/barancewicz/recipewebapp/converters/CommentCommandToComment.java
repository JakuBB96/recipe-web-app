//package com.barancewicz.recipewebapp.converters;
//
//import com.barancewicz.recipewebapp.commands.CommentCommand;
//import com.barancewicz.recipewebapp.commands.RecipeCommand;
//import com.barancewicz.recipewebapp.domain.Comment;
//import lombok.Synchronized;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CommentCommandToComment implements Converter<CommentCommand, Comment> {
//
//    @Synchronized
//    @Nullable
//    @Override
//    public Comment convert(CommentCommand source) {
//        if (source==null){
//            return null;
//        }
//        final Comment comment = new Comment();
//        comment.setId(source.getId());
//        comment.setText(source.getText());
//        return comment;
//    }
//
//
//}
