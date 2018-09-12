package com.fuat.springreacttodo.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TodoListController {
    private final Logger log = LoggerFactory.getLogger(TodoListController.class);
    private TodoListRepository todoListRepository;
    private TodoItemRepository todoItemRepository;

    public TodoListController(TodoListRepository todoListRepository, TodoItemRepository todoItemRepository) {
        this.todoListRepository = todoListRepository;
        this.todoItemRepository = todoItemRepository;
    }

    // Get all todolists
    @GetMapping("/todolists")
    List<TodoList> todolists() {
        return todoListRepository.findAll();
    }

    // Get specific todolist
    @GetMapping("/todolist/{id}")
    ResponseEntity<?> getTodoList(@PathVariable Long id) {
        Optional<TodoList> todolist = todoListRepository.findById(id);
        return todolist.map(response -> ResponseEntity.ok().body(response)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get items of the list
    @GetMapping("/todolist/{id}/todoitems")
    List<TodoItem> todoitems(@PathVariable Long id) {
        return todoItemRepository.findByList(todoListRepository.findOneById(id));
    }

    // Create todolist
    @PostMapping("/todolist")
    ResponseEntity<TodoList> createTodoList(@Valid @RequestBody TodoList todolist) throws URISyntaxException {
        log.info("Request to create todolist: {}", todolist);
        TodoList result = todoListRepository.save(todolist);
        return ResponseEntity.created(new URI("/api/todolist/" + result.getId()))
                .body(result);
    }

    // Update specific todolist
    @PutMapping("/todolist/{id}")
    ResponseEntity<TodoList> updateTodoList(@PathVariable Long id, @Valid @RequestBody TodoList todolist) {
        todolist.setId(id);
        log.info("Request to update todolist: {}", todolist);
        TodoList result = todoListRepository.save(todolist);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/todolist/{id}/todoitem/{itemId}")
    public ResponseEntity<String> updateItem(@PathVariable("id") Long id,
                                             @PathVariable("itemId") Long itemId,
                                             @RequestBody TodoItemRequest request) {
        TodoList todoList = todoListRepository.findOneById(id);
        TodoItem todoItem = todoItemRepository.findOneByIdAndList(itemId, todoList);
        todoItem.merge(request);
        todoItemRepository.save(todoItem);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/todolist/{id}")
    public ResponseEntity<?> deleteTodoList(@PathVariable Long id) {
        log.info("Request to delete todolist: {}", id);
        todoListRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/todolist/{id}/todoitem/{itemId}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id,
                                         @PathVariable("itemId") Long itemId) {
        TodoList todoList = todoListRepository.findOneById(id);
        todoItemRepository.deleteByIdAndList(itemId, todoList);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
