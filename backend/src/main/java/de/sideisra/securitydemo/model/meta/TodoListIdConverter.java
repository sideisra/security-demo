package de.sideisra.securitydemo.model.meta;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Convert a String to {@link TodoListId}. Used in path variables in controller such as
 * {@link de.sideisra.securitydemo.todolist.TodoListController}.
 */
@Component
public class TodoListIdConverter implements Converter<String, TodoListId> {

  @Override
  public TodoListId convert(final String id) {
    return TodoListId.fromUUID(UUID.fromString(id));
  }
}
