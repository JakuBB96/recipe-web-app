//package com.barancewicz.recipewebapp.converters;
//
//import com.barancewicz.recipewebapp.commands.CategoryCommand;
//import com.barancewicz.recipewebapp.commands.CommentCommand;
//import com.barancewicz.recipewebapp.domain.Category;
//import com.barancewicz.recipewebapp.domain.Comment;
//import lombok.Synchronized;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.lang.Nullable;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CommentToCommentCommand implements Converter<Comment, CommentCommand> {
//
//    @Synchronized
//    @Nullable
//    @Override
//    public CommentCommand convert(Comment source) {
//            if (source==null){
//                return null;
//            }
//            final CommentCommand command = new CommentCommand();
//            command.setId(source.getId());
//            command.setText(source.getText());
//            return command;
//        }
//}
