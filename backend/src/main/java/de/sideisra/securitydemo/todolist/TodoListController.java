package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.model.ListOwner;
import de.sideisra.securitydemo.model.TodoList;
import de.sideisra.securitydemo.model.TodoListItem;
import de.sideisra.securitydemo.model.meta.TodoListId;
import de.sideisra.securitydemo.model.meta.TodoListItemId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/todolists")
public class TodoListController {

  private final TodoListService todoListService;

  public TodoListController(final TodoListService todoListService) {
    this.todoListService = todoListService;
  }

  @GetMapping
  public Collection<TodoList> getTodoListsByOwner(final ListOwner owner) {
    return todoListService.getTodoListsByOwner(owner);
  }

  @GetMapping("/{todoListId}")
  public TodoList getTodoList(@PathVariable final TodoListId todoListId) {
    return todoListService.getTodoList(todoListId);
  }

  @PostMapping
  public TodoListId createTodoList(final List<TodoListItem> items, final ListOwner owner) {
    return todoListService.createTodoList(items, owner);
  }

  @PostMapping("/{todoListId}/addItem")
  public TodoListItemId addItem(@PathVariable final TodoListId todoListId, final String value, final boolean done) {
    return todoListService.addItem(todoListId, value, done);
  }

  @PutMapping("/{todoListId}/items/{itemId}")
  public void addItem(@PathVariable final TodoListId todoListId, @PathVariable final TodoListItemId itemId, final TodoListItem changedItem) {
    todoListService.changeItem(todoListId, changedItem);
  }
}
