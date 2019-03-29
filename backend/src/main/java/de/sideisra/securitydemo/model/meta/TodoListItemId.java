package de.sideisra.securitydemo.model.meta;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Objects;
import java.util.UUID;

@JsonSerialize(using = TodoListItemIdSerializer.class)
@JsonDeserialize(using = TodoListItemIdDeserializer.class)
public class TodoListItemId {
  private final UUID value;

  private TodoListItemId(final UUID value) {
    this.value = value;
  }

  public static TodoListItemId newRandom() {
    return new TodoListItemId(UUID.randomUUID());
  }

  public static TodoListItemId fromUUID(final UUID uuid) {
    return new TodoListItemId(uuid);
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
    final TodoListItemId that = (TodoListItemId) o;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TodoListItemId{");
    sb.append("value=").append(value);
    sb.append('}');
    return sb.toString();
  }
}
