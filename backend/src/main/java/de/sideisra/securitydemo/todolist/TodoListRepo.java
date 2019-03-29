package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.model.ListOwner;
import de.sideisra.securitydemo.model.TodoList;
import de.sideisra.securitydemo.model.meta.TodoListId;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Repository
public class TodoListRepo {

  private final Map<TodoListId, TodoList> todoLists = new HashMap<>();

  public Collection<TodoList> getTodoListsByOwner(final ListOwner owner) {
    return todoLists.values().stream().filter(todoList -> owner.equals(todoList.getOwner())).collect(toList());
  }

  public Optional<TodoList> getTodoList(final TodoListId id) {
    return Optional.ofNullable(todoLists.get(id));
  }

  public void saveTodoList(final TodoList todoList) {
    todoLists.put(todoList.getId(), todoList);
  }

}
