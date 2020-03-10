package com.digitalsolutionsbydon.todos.service;

import com.digitalsolutionsbydon.todos.model.Todo;
import com.digitalsolutionsbydon.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value="todoService")
public class TodoServiceImpl implements TodoService
{
    @Autowired
    TodoRepository todorepos;

    @Override
    public List<Todo> findAll()
    {
        List<Todo> list = new ArrayList<>();
        todorepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Todo findById(long id)
    {
        return todorepos.findById(id).orElseThrow(()->new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public List<Todo> findByUserName(String username)
    {
        List<Todo> list = new ArrayList<>();
        todorepos.findAll().iterator().forEachRemaining(list::add);
        list.removeIf(t -> !t.getUser().getUsername().equalsIgnoreCase(username));
        return list;
    }

    @Transactional
    @Override
    public Todo save(Todo todo)
    {
        return todorepos.save(todo);
    }

    @Override
    public Todo update(Todo todo, long id)
    {
        Todo newTodo = todorepos.findById(id).orElseThrow(()->new EntityNotFoundException(Long.toString(id)));

        if (todo.getDescription() != null)
        {
            newTodo.setDescription(todo.getDescription());
        }

        if (todo.getDatestarted()!=null)
        {
            newTodo.setDatestarted(todo.getDatestarted());
        }
        if (todo.getUser()!=null)
        {
            newTodo.setUser(todo.getUser());
        }
        newTodo.setCompleted(todo.isCompleted());

        return todorepos.save(newTodo);
    }

    @Override
    public void delete(long id)
    {
        if (todorepos.findById(id).isPresent())
        {
            todorepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }
}