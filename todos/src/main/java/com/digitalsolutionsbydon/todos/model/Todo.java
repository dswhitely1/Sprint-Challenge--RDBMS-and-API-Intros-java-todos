package com.digitalsolutionsbydon.todos.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "todos")
public class Todo extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long todoid;

    @Column(nullable = false)
    private String description;

    private Date datestarted;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    public Todo()
    {
    }

    public Todo(String description, Date datestarted, User user)
    {
        this.description = description;
        this.datestarted = datestarted;
        this.completed = false;
        this.user = user;
    }

    public long getTodoid()
    {
        return todoid;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Date getDatestarted()
    {
        return datestarted;
    }

    public void setDatestarted(Date datestarted)
    {
        this.datestarted = datestarted;
    }

    public boolean isCompleted()
    {
        return completed;
    }

    public void setCompleted(boolean completed)
    {
        this.completed = completed;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}