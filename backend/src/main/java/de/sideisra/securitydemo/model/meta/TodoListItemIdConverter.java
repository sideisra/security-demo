package de.sideisra.securitydemo.model.meta;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Convert a String to {@link TodoListItemId}. Used in path variables in controller such as
 * {@link de.sideisra.securitydemo.todolist.TodoListController}.
 */
@Component
public class TodoListItemIdConverter implements Converter<String, TodoListItemId> {

  @Override
  public TodoListItemId convert(final String id) {
    return TodoListItemId.fromUUID(UUID.fromString(id));
  }
}
