package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.model.ListOwner;
import de.sideisra.securitydemo.model.TodoList;
import de.sideisra.securitydemo.model.TodoListCreate;
import de.sideisra.securitydemo.model.TodoListItem;
import de.sideisra.securitydemo.model.TodoListItemCreate;
import de.sideisra.securitydemo.model.meta.TodoListId;
import de.sideisra.securitydemo.model.meta.TodoListItemId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/todolists")
public class TodoListController {

  private final TodoListService todoListService;

  public TodoListController(final TodoListService todoListService) {
    this.todoListService = todoListService;
  }

  @GetMapping
  public Collection<TodoList> getTodoListsByOwner() {
    //TODO replace by Principal
    final ListOwner owner = new ListOwner("max@mustermann.local", "Max", "Mustermann");
    return todoListService.getTodoListsByOwner(owner);
  }

  @GetMapping("/{todoListId}")
  public TodoList getTodoList(@PathVariable final TodoListId todoListId) {
    return todoListService.getTodoList(todoListId);
  }

  @PostMapping
  public TodoListId createTodoList(@RequestBody final TodoListCreate todoListCreate) {
    //TODO replace by Principal
    final ListOwner owner = new ListOwner("max@mustermann.local", "Max", "Mustermann");
    return todoListService.createTodoList(todoListCreate.getName(), todoListCreate.getItems(), owner);
  }

  @PostMapping("/{todoListId}/addItem")
  public TodoListItemId addItem(@PathVariable final TodoListId todoListId, @RequestBody final TodoListItemCreate newItem) {
    return todoListService.addItem(todoListId, newItem.getValue(), newItem.isDone());
  }

  @PutMapping("/{todoListId}/items/{itemId}")
  public void addItem(@PathVariable final TodoListId todoListId, @PathVariable final TodoListItemId itemId, @RequestBody final TodoListItem changedItem) {
    //TODO error when itemId in path does not match itemId in Body
    todoListService.changeItem(todoListId, changedItem);
  }
}
