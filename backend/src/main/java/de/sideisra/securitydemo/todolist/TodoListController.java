package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.model.*;
import de.sideisra.securitydemo.model.meta.TodoListId;
import de.sideisra.securitydemo.model.meta.TodoListItemId;
import de.sideisra.securitydemo.security.SecurityDemoUserDetails;
import de.sideisra.securitydemo.security.UserRoles;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

@RestController
@RequestMapping("/todolists")
public class TodoListController {

  private final TodoListService todoListService;

  public TodoListController(final TodoListService todoListService) {
    this.todoListService = todoListService;
  }

  @GetMapping
  @RolesAllowed(UserRoles.TODO_LIST_USER)
  public Collection<TodoList> getTodoListsByOwner(@AuthenticationPrincipal final SecurityDemoUserDetails user) {
    final ListOwner owner = ListOwner.fromUserDetails(user);
    return todoListService.getTodoListsByOwner(owner);
  }

  @GetMapping("/{todoListId}")
  @RolesAllowed(UserRoles.TODO_LIST_USER)
  public TodoList getTodoList(@PathVariable final TodoListId todoListId) {
    //TODO check owner
    return todoListService.getTodoList(todoListId);
  }

  @PostMapping
  @RolesAllowed(UserRoles.TODO_LIST_USER)
  public TodoListId createTodoList(@RequestBody final TodoListCreate todoListCreate,
      @AuthenticationPrincipal final SecurityDemoUserDetails user) {
    final ListOwner owner = ListOwner.fromUserDetails(user);
    return todoListService.createTodoList(todoListCreate.getName(), todoListCreate.getItems(), owner);
  }

  @PostMapping("/{todoListId}/addItem")
  @RolesAllowed(UserRoles.TODO_LIST_USER)
  public TodoListItemId addItem(@PathVariable final TodoListId todoListId,
      @RequestBody final TodoListItemCreate newItem) {
    //TODO check owner
    return todoListService.addItem(todoListId, newItem.getValue(), newItem.isDone());
  }

  @PutMapping("/{todoListId}/items/{itemId}")
  @RolesAllowed(UserRoles.TODO_LIST_USER)
  public void addItem(@PathVariable final TodoListId todoListId, @PathVariable final TodoListItemId itemId,
      @RequestBody final TodoListItem changedItem) {
    //TODO check owner
    //TODO error when itemId in path does not match itemId in Body
    todoListService.changeItem(todoListId, changedItem);
  }
}

