package com.digitalsolutionsbydon.todos.controller;

import com.digitalsolutionsbydon.todos.model.Todo;
import com.digitalsolutionsbydon.todos.model.User;
import com.digitalsolutionsbydon.todos.service.TodoService;
import com.digitalsolutionsbydon.todos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController
{
    @Autowired
    private UserService userService;

    @Autowired
    private TodoService todoService;

    @GetMapping(value="/mine", produces = {"application/json"})
    public ResponseEntity<?> getUserTodos(Authentication authentication)
    {
        List<Todo> list = todoService.findByUserName(authentication.getName());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping(value="/", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewUser(@RequestBody
                                                User newuser)
    {
        User newUser = userService.save(newuser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN' or hasAnyRole('ROLE_USER'))")
    @PostMapping(value="/todo/{userid}", consumes = {"application/json"}, produces = {"applcation/json"})
    public ResponseEntity<?> addTodo(@RequestBody Todo newTodo, @PathVariable long userid)
    {
        Todo returnTodo = todoService.update(newTodo,userid);
        return new ResponseEntity<>(returnTodo,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping(value="/userid/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable long userid)
    {
        userService.delete(userid);
        return new ResponseEntity<>(userid,HttpStatus.OK);
    }

}