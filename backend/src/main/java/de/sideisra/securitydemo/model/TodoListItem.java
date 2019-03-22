package de.sideisra.securitydemo.model;

import java.util.Objects;

public class TodoListItem {
  private final String value;
  private final boolean done;

  public TodoListItem(final String value, final boolean done) {
    this.value = value;
    this.done = done;
  }

  public String getValue() {
    return value;
  }

  public boolean isDone() {
    return done;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    final TodoListItem that = (TodoListItem) o;
    return done == that.done &&
        Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, done);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TodoListItem{");
    sb.append("value='").append(value).append('\'');
    sb.append(", done=").append(done);
    sb.append('}');
    return sb.toString();
  }
}
