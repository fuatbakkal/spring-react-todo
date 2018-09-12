package com.fuat.springreacttodo.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "todolist")
public class TodoList {

    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;

    private int itemcount;

    @OneToMany(mappedBy = "list",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private List<TodoItem> items = new ArrayList<>();


    public int getItemCount() {
        return items.size();
    }

    public static TodoList from(TodoListRequest todoListRequest) {
        return new TodoList(todoListRequest.getName());
    }

    public void merge(TodoListRequest request) {
        setName(request.getName());
    }

    public void addItem(TodoItem item) {
        items.add(item);
    }
}
