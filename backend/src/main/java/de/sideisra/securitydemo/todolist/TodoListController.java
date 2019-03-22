package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.model.TodoList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todolists")
public class TodoListController {

  private final TodoListService todoListService;

  public TodoListController(final TodoListService todoListService) {
    this.todoListService = todoListService;
  }

  @GetMapping
  public List<TodoList> getTodoLists() {
    return todoListService.getTodoLists();
  }
}
