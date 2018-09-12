package com.fuat.springreacttodo.model;

import lombok.Data;

@Data
public class TodoListRequest {

    private String name;

    public TodoListRequest(String name) {
        this.name = name;
    }

}