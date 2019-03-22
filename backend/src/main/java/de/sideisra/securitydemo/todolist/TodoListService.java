package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.model.ListOwner;
import de.sideisra.securitydemo.model.TodoList;
import de.sideisra.securitydemo.model.meta.TodoListId;
import de.sideisra.securitydemo.model.TodoListItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService {
  public List<TodoList> getTodoLists() {
    return List.of(
        new TodoList(TodoListId.newRandom(), new ListOwner("list@owner.local", "List", "Owner"),
            List.of(new TodoListItem("check security", false)))
    );
  }
}
