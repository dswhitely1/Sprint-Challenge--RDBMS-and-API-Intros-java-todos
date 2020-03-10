package com.digitalsolutionsbydon.todos.service;

import com.digitalsolutionsbydon.todos.model.Todo;
import com.digitalsolutionsbydon.todos.model.User;
import com.digitalsolutionsbydon.todos.model.UserRoles;
import com.digitalsolutionsbydon.todos.repository.RoleRepository;
import com.digitalsolutionsbydon.todos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService
{
    @Autowired
    private UserRepository userrepos;

    @Autowired
    private RoleRepository rolerepos;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userrepos.findByUsername(username);
        if (user == null)
        {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthority());
    }

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        userrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public User findUserByName(String name) throws EntityNotFoundException
    {
        return userrepos.findByUsername(name);
    }

    @Transactional
    @Override
    public User findUserById(long id)
    {
        return userrepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (userrepos.findById(id).isPresent())
        {
            userrepos.deleteById(id);
        } else
        {
            throw new EntityNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public User save(User user)
    {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPasswordNoEncrypt(user.getPassword());

        ArrayList<UserRoles> newRoles = new ArrayList<>();
        for (UserRoles ur : user.getUserRoles())
        {
            newRoles.add(new UserRoles(newUser, ur.getRole()));
        }
        newUser.setUserRoles(newRoles);
        for (Todo t : user.getTodos())
        {
            newUser.getTodos().add(new Todo(t.getDescription(), new Date(), newUser));
        }

        return userrepos.save(newUser);
    }

    @Transactional
    @Override
    public User update(User user, long id)
    {
        User currentUser = userrepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));

        if (user.getUsername() != null)
        {
            currentUser.setUsername(user.getUsername());
        }

        if (user.getPassword() != null)
        {
            currentUser.setPasswordNoEncrypt(user.getPassword());
        }

        if (user.getUserRoles().size() > 0)
        {
            rolerepos.deleteUserRolesByUserId(currentUser.getUserid());

            for (UserRoles ur : user.getUserRoles())
            {
                rolerepos.insertUserRoles(id, ur.getRole().getRoleid());
            }
        }

        if (user.getTodos().size() > 0)
        {
            for (Todo t : user.getTodos())
            {
                currentUser.getTodos().add(new Todo(t.getDescription(), new Date(), currentUser));
            }
        }
        return userrepos.save(currentUser);
    }
}