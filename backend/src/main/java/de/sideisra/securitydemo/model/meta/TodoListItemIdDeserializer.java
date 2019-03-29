package de.sideisra.securitydemo.model.meta;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.UUID;

import static org.springframework.util.StringUtils.isEmpty;

public class TodoListItemIdDeserializer extends StdDeserializer<TodoListItemId> {

  protected TodoListItemIdDeserializer() {
    super(TodoListItemId.class);
  }

  @Override
  public TodoListItemId deserialize(final JsonParser jsonParser, final DeserializationContext deserializationContext)
    throws IOException {
    final String value = jsonParser.readValueAs(String.class);
    return isEmpty(value) ? null : TodoListItemId.fromUUID(UUID.fromString(value));
  }
}
