package de.sideisra.securitydemo.model;

import de.sideisra.securitydemo.model.meta.TodoListId;

import java.util.List;
import java.util.Objects;

public class TodoList {
  private final TodoListId id;
  private final ListOwner owner;
  private final List<TodoListItem> items;

  public TodoList(final TodoListId id, final ListOwner owner,
      final List<TodoListItem> items) {
    this.id = id;
    this.owner = owner;
    this.items = items;
  }

  public TodoListId getId() {
    return id;
  }

  public ListOwner getOwner() {
    return owner;
  }

  public List<TodoListItem> getItems() {
    return items;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    final TodoList todoList = (TodoList) o;
    return Objects.equals(id, todoList.id) &&
        Objects.equals(owner, todoList.owner) &&
        Objects.equals(items, todoList.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, owner, items);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TodoList{");
    sb.append("id=").append(id);
    sb.append(", owner=").append(owner);
    sb.append(", items=").append(items);
    sb.append('}');
    return sb.toString();
  }
}
