package com.digitalsolutionsbydon.todos.repository;

import com.digitalsolutionsbydon.todos.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>
{
    User findByUsername(String username);
}