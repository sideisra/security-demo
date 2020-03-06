package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.exception.AccessDeniedException;
import de.sideisra.securitydemo.model.*;
import de.sideisra.securitydemo.model.meta.TodoListId;
import de.sideisra.securitydemo.model.meta.TodoListItemId;
import de.sideisra.securitydemo.security.UserRoles;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
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
  public Collection<TodoList> getTodoListsByOwner(JwtAuthenticationToken authentication) {
    final ListOwner owner = ListOwner.fromUserDetails(authentication);
    return todoListService.getTodoListsByOwner(owner);
  }

  @GetMapping("/{todoListId}")
  @RolesAllowed(UserRoles.TODO_LIST_USER)
  public TodoList getTodoList(@PathVariable final TodoListId todoListId,
      JwtAuthenticationToken authentication) {
    final TodoList todoList = todoListService.getTodoList(todoListId);
    final ListOwner listOwner = ListOwner.fromUserDetails(authentication);
    if (todoList.getOwner().equals(listOwner)) {
      return todoList;
    } else {
      throw new AccessDeniedException(
          "User " + listOwner.geteMail() + " ist not the owner of the desired list " + todoListId);
    }
  }

  @PostMapping
  @RolesAllowed(UserRoles.TODO_LIST_USER)
  public TodoListId createTodoList(@RequestBody final TodoListCreate todoListCreate,
      JwtAuthenticationToken authentication) {
    final ListOwner owner = ListOwner.fromUserDetails(authentication);
    return todoListService.createTodoList(todoListCreate.getName(), todoListCreate.getItems(), owner);
  }

  @PostMapping("/{todoListId}/addItem")
  @RolesAllowed(UserRoles.TODO_LIST_USER)
  public TodoListItemId addItem(@PathVariable final TodoListId todoListId,
      @RequestBody final TodoListItemCreate newItem, JwtAuthenticationToken authentication) {
    final TodoList todoList = todoListService.getTodoList(todoListId);
    final ListOwner listOwner = ListOwner.fromUserDetails(authentication);
    if (todoList.getOwner().equals(listOwner)) {
      return todoListService.addItem(todoListId, newItem.getValue(), newItem.isDone());
    } else {
      throw new AccessDeniedException(
          "User " + listOwner.geteMail() + " ist not the owner of the desired list " + todoListId);
    }
  }

  @PutMapping("/{todoListId}/items/{itemId}")
  @RolesAllowed(UserRoles.TODO_LIST_USER)
  public void changeItem(
      @PathVariable final TodoListId todoListId,
      @PathVariable final TodoListItemId itemId,
      @RequestBody final TodoListItem changedItem,
      JwtAuthenticationToken authentication) {
    if (!itemId.equals(changedItem.getId())) {
      throw new IllegalArgumentException(
          "Item id in path (" + itemId + ") does not match item id in body (" + changedItem.getId() + ")");
    }
    final TodoList todoList = todoListService.getTodoList(todoListId);
    final ListOwner listOwner = ListOwner.fromUserDetails(authentication);
    if (todoList.getOwner().equals(listOwner)) {
      todoListService.changeItem(todoListId, changedItem);
    } else {
      throw new AccessDeniedException(
          "User " + listOwner.geteMail() + " ist not the owner of the desired list " + todoListId);
    }
  }
}

