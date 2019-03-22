package de.sideisra.securitydemo.model.meta;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.util.StringUtils.isEmpty;

public class TodoListIdDeserializer extends StdDeserializer<TodoListId> {

  protected TodoListIdDeserializer() {
    super(TodoListId.class);
  }

  @Override
  public TodoListId deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
      throws IOException {
    final String value = jsonParser.readValueAs(String.class);
    return isEmpty(value) ? null : TodoListId.fromUUID(UUID.fromString(value));
  }
}
