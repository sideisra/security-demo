package de.sideisra.securitydemo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class TodoListItemCreate {
  private final String value;
  private final boolean done;

  @JsonCreator
  public TodoListItemCreate(
    @JsonProperty("value") final String value,
    @JsonProperty("done") final boolean done) {
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TodoListItemCreate that = (TodoListItemCreate) o;
    return done == that.done &&
      Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value, done);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TodoListItemCreate{");
    sb.append("value='").append(value).append('\'');
    sb.append(", done=").append(done);
    sb.append('}');
    return sb.toString();
  }
}
