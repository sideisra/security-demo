package de.sideisra.securitydemo.model;

import de.sideisra.securitydemo.model.meta.TodoListItemId;

import java.util.Objects;

public class TodoListItem {
  private final TodoListItemId id;
  private final String value;
  private final boolean done;

  public TodoListItem(TodoListItemId id, final String value, final boolean done) {
    this.id = id;
    this.value = value;
    this.done = done;
  }

  public TodoListItemId getId() {
    return id;
  }

  public String getValue() {
    return value;
  }

  public boolean isDone() {
    return done;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TodoListItem that = (TodoListItem) o;
    return done == that.done &&
      Objects.equals(id, that.id) &&
      Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, value, done);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TodoListItem{");
    sb.append("id=").append(id);
    sb.append(", value='").append(value).append('\'');
    sb.append(", done=").append(done);
    sb.append('}');
    return sb.toString();
  }
}
