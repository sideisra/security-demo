package de.sideisra.securitydemo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class TodoListCreate {
  private final String name;
  private final List<TodoListItemCreate> items;

  @JsonCreator
  public TodoListCreate(
    @JsonProperty("name") final String name,
    @JsonProperty("items") final List<TodoListItemCreate> items) {
    this.name = name;
    this.items = items == null ? List.of() : Collections.unmodifiableList(items);
  }

  public String getName() {
    return name;
  }

  public List<TodoListItemCreate> getItems() {
    return items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TodoListCreate that = (TodoListCreate) o;
    return Objects.equals(name, that.name) &&
      Objects.equals(items, that.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, items);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TodoListCreate{");
    sb.append("name='").append(name).append('\'');
    sb.append(", items=").append(items);
    sb.append('}');
    return sb.toString();
  }
}
