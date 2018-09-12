package com.fuat.springreacttodo.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoListRepository extends JpaRepository<TodoList, Long> {
    List<TodoList> findAll();
    TodoList findOneById(Long id);
    TodoList findOneByName(String name);
    TodoList findOneByIdAndName(Long id, String name);
}