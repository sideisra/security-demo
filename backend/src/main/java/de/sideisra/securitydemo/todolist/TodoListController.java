package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.exception.AccessDeniedException;
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
  public TodoList getTodoList(@PathVariable final TodoListId todoListId,
      @AuthenticationPrincipal final SecurityDemoUserDetails user) {
    final TodoList todoList = todoListService.getTodoList(todoListId);
    if (todoList.getOwner().equals(ListOwner.fromUserDetails(user))) {
      return todoList;
    } else {
      throw new AccessDeniedException(
          "User " + user.getEmail() + " ist not the owner of the desired list " + todoListId);
    }
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
      @RequestBody final TodoListItemCreate newItem, @AuthenticationPrincipal final SecurityDemoUserDetails user) {
    final TodoList todoList = todoListService.getTodoList(todoListId);
    if (todoList.getOwner().equals(ListOwner.fromUserDetails(user))) {
      return todoListService.addItem(todoListId, newItem.getValue(), newItem.isDone());
    } else {
      throw new AccessDeniedException(
          "User " + user.getEmail() + " ist not the owner of the desired list " + todoListId);
    }
  }

  @PutMapping("/{todoListId}/items/{itemId}")
  @RolesAllowed(UserRoles.TODO_LIST_USER)
  public void changeItem(@PathVariable final TodoListId todoListId, @PathVariable final TodoListItemId itemId,
      @RequestBody final TodoListItem changedItem, @AuthenticationPrincipal final SecurityDemoUserDetails user) {
    if (!itemId.equals(changedItem.getId())) {
      throw new IllegalArgumentException(
          "Item id in path (" + itemId + ") does not match item id in body (" + changedItem.getId() + ")");
    }
    final TodoList todoList = todoListService.getTodoList(todoListId);
    if (todoList.getOwner().equals(ListOwner.fromUserDetails(user))) {
      todoListService.changeItem(todoListId, changedItem);
    } else {
      throw new AccessDeniedException(
          "User " + user.getEmail() + " ist not the owner of the desired list " + todoListId);
    }
  }
}

