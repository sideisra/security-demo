package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.exception.NotFoundException;
import de.sideisra.securitydemo.model.ListOwner;
import de.sideisra.securitydemo.model.TodoList;
import de.sideisra.securitydemo.model.TodoListItem;
import de.sideisra.securitydemo.model.meta.TodoListId;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TodoListService {

  private final TodoListRepo todoListRepo;

  public TodoListService(final TodoListRepo todoListRepo) {
    this.todoListRepo = todoListRepo;
  }

  public Collection<TodoList> getTodoListsByOwner(final ListOwner owner) {
    return todoListRepo.getTodoListsByOwner(owner);
  }

  public TodoList getTodoList(final TodoListId id) {
    return todoListRepo.getTodoList(id).orElseThrow(() -> todoListNotFound(id));
  }

  public TodoListId createTodoList(final List<TodoListItem> items, final ListOwner owner) {
    final TodoListId id = TodoListId.newRandom();
    todoListRepo.saveTodoList(new TodoList(id, owner, items));
    return id;
  }

  public void addItem(final TodoListId id, final TodoListItem newItem) {
    final Optional<TodoList> todoListOpt = todoListRepo.getTodoList(id);
    todoListOpt.ifPresentOrElse(
      todoList -> todoListRepo.saveTodoList(todoList.addItem(newItem)),
      () -> {
        throw todoListNotFound(id);
      }
    );
  }

  private NotFoundException todoListNotFound(final TodoListId id) {
    return new NotFoundException("Could not find Todo List with id " + id);
  }
}
