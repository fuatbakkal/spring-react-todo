package com.fuat.springreacttodo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
    TodoItem findOneByName(String name);
    TodoItem findOneByIdAndName(Long id, String name);
    List<TodoItem> findByList(TodoList todoList);
    TodoItem findOneByIdAndList(Long id, TodoList todoList);

    @Transactional
    void deleteByIdAndList(Long id, TodoList todoList);
}