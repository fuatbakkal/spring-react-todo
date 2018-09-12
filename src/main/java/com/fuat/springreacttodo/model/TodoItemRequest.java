package com.fuat.springreacttodo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TodoItemRequest {

    private String name;
    private boolean completed;

    public TodoItemRequest(String name) {
        this.name = name;
    }
}