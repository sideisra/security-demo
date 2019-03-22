package de.sideisra.securitydemo.model.meta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;
import java.util.UUID;

@JsonSerialize(using = TodoListIdSerializer.class)
@JsonDeserialize(using = TodoListIdDeserializer.class)
public class TodoListId {
  private final UUID value;

  private TodoListId(final UUID value) {
    this.value = value;
  }

  public static TodoListId newRandom() {
    return new TodoListId(UUID.randomUUID());
  }

  public static TodoListId fromUUID(final UUID uuid) {
    return new TodoListId(uuid);
  }

  UUID getValue() {
    return value;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    final TodoListId that = (TodoListId) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TodoListId{");
    sb.append("value=").append(value);
    sb.append('}');
    return sb.toString();
  }
}
