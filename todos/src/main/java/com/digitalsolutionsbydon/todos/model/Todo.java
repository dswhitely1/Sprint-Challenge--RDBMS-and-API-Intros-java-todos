package com.digitalsolutionsbydon.todos.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "todos")
public class Todo extends Auditable
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long todoid;

    @Column(nullable = false)
    private String description;

    private LocalDateTime datetime;
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    public Todo()
    {
    }

    public Todo(String description, LocalDateTime datetime, boolean completed, User user)
    {
        this.description = description;
        this.datetime = datetime;
        this.completed = completed;
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

    public LocalDateTime getDatetime()
    {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime)
    {
        this.datetime = datetime;
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