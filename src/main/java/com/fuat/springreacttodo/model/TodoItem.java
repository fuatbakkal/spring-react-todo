package com.fuat.springreacttodo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@ToString(exclude = {"dependencies", "list"})
@Table(name="todo_item")
public class TodoItem {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private Instant create_date;
    private Instant deadline;
    private boolean completed;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<TodoItem> dependencies;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todolist_id")
    @JsonIgnore
    private TodoList list;

    public Optional<Set<TodoItem>> getDependencies() {
        return Optional.ofNullable(dependencies);
    }

    public void addDependency(TodoItem item) {
        dependencies.add(item);
    }

    public TodoItem(String name, TodoList list) {
        this.name = name;
        this.list = list;
    }


    public static TodoItem from(TodoItemRequest todoItemRequest, TodoList todoList) {
        return new TodoItem(todoItemRequest.getName(), todoList);
    }

    public void merge(TodoItemRequest request) {
        this.name = request.getName();
        this.completed = request.isCompleted();
    }
}