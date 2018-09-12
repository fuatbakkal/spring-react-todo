package com.fuat.springreacttodo;

import com.fuat.springreacttodo.model.TodoItem;
import com.fuat.springreacttodo.model.TodoItemRepository;
import com.fuat.springreacttodo.model.TodoList;
import com.fuat.springreacttodo.model.TodoListRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Component
class Initializer implements CommandLineRunner {

    private final TodoListRepository repository;

    public Initializer(TodoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... strings) {

        TodoList todoList1 = new TodoList("Todo List 1");
        TodoList todoList2 = new TodoList("Todo List 2");

        TodoItem todoItem1 = TodoItem.builder().name("Todo Item 1").description("Description of Item 1").
                create_date(Instant.now()).deadline(Instant.now().plusSeconds(1000)).
                completed(false).list(todoList1).build();
        TodoItem todoItem2 = TodoItem.builder().name("Todo Item 2").description("Description of Item 2").
                create_date(Instant.now()).deadline(Instant.now().plusSeconds(500)).
                completed(false).list(todoList1).build();

        todoList1.addItem(todoItem1);
        todoList1.addItem(todoItem2);

        repository.save(todoList1);
        repository.save(todoList2);

        repository.findAll().forEach(System.out::println);
    }
}