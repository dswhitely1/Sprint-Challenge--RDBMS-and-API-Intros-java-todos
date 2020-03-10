package com.digitalsolutionsbydon.todos.repository;

import com.digitalsolutionsbydon.todos.model.Todo;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long>
{

}