package de.sideisra.securitydemo.model;

import de.sideisra.securitydemo.exception.NotFoundException;
import de.sideisra.securitydemo.model.meta.TodoListId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

public class TodoList {
  private final TodoListId id;
  private final String name;
  private final ListOwner owner;
  private final List<TodoListItem> items;

  public TodoList(final TodoListId id, String name, final ListOwner owner,
                  final List<TodoListItem> items) {
    this.id = id;
    this.name = name;
    this.owner = owner;
    this.items = items == null ? List.of() : Collections.unmodifiableList(items);
  }

  public TodoList addItem(final TodoListItem item) {
    final ArrayList<TodoListItem> newItems = new ArrayList<>(items);
    newItems.add(item);
    return new TodoList(id, name, owner, newItems);
  }

  public TodoList changeItem(TodoListItem changedItem) {
    final List<TodoListItem> newItems = items.stream()
      .map(i -> i.getId().equals(changedItem.getId()) ? changedItem : i)
      .collect(toList());
    if (newItems.contains(changedItem)) {
      return new TodoList(id, name, owner, newItems);
    } else {
      throw new NotFoundException("Could not find item " + changedItem.getId() + " in tod list " + id);
    }
  }

  public TodoListId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public ListOwner getOwner() {
    return owner;
  }

  public List<TodoListItem> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TodoList todoList = (TodoList) o;
    return Objects.equals(id, todoList.id) &&
      Objects.equals(name, todoList.name) &&
      Objects.equals(owner, todoList.owner) &&
      Objects.equals(items, todoList.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, owner, items);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TodoList{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", owner=").append(owner);
    sb.append(", items=").append(items);
    sb.append('}');
    return sb.toString();
  }
}
