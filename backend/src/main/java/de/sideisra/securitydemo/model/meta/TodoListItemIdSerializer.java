package de.sideisra.securitydemo.model.meta;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class TodoListItemIdSerializer extends StdSerializer<TodoListItemId> {

  protected TodoListItemIdSerializer() {
    super(TodoListItemId.class);
  }

  @Override
  public void serialize(final TodoListItemId todoListItemId, final JsonGenerator jsonGenerator,
                        final SerializerProvider serializerProvider)
    throws IOException {
    if (todoListItemId != null) {
      jsonGenerator.writeString(todoListItemId.getValue().toString());
    }
  }
}
