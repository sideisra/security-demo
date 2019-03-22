package de.sideisra.securitydemo.model.meta;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TodoListIdSerializer extends StdSerializer<TodoListId> {

  protected TodoListIdSerializer() {
    super(TodoListId.class);
  }

  @Override
  public void serialize(final TodoListId todoListId, final JsonGenerator jsonGenerator,
      final SerializerProvider serializerProvider)
      throws IOException {
    if (todoListId != null) {
      jsonGenerator.writeString(todoListId.getValue().toString());
    }
  }
}
