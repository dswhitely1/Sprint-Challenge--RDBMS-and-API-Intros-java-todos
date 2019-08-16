package com.digitalsolutionsbydon.todos.service;

import com.digitalsolutionsbydon.todos.model.Todo;

import java.util.List;

public interface TodoService
{
    List<Todo> findAll();

    Todo findById(long id);

    List<Todo> findByUserName(String username);

    Todo save(Todo todo);

    Todo update(Todo todo, long id);

    void delete(long id);
}