package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.model.ListOwner;
import de.sideisra.securitydemo.model.TodoList;
import de.sideisra.securitydemo.model.meta.TodoListId;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for {@link TodoListRepo}.
 */
public class TodoListRepoTest {

  private static final ListOwner LIST_OWNER_1 = new ListOwner("test1@test.local", "Test", "Local");
  private static final ListOwner LIST_OWNER_2 = new ListOwner("test2@test.local", "Test", "Local");

  private final TodoListRepo cut = new TodoListRepo();

  @Test
  public void shouldReturnTodoListWithGivenId() {
    final TodoList todoList1 = new TodoList(TodoListId.newRandom(), "my list 1", LIST_OWNER_1, List.of());
    cut.saveTodoList(todoList1);
    final TodoList todoList2 = new TodoList(TodoListId.newRandom(), "my list 2", LIST_OWNER_1, List.of());
    cut.saveTodoList(todoList2);

    assertThat(cut.getTodoList(todoList1.getId())).contains(todoList1);
    assertThat(cut.getTodoList(todoList2.getId())).contains(todoList2);
  }

  @Test
  public void shouldReturnEmptyOptionalWhenGivenIdIsUnknown() {
    cut.saveTodoList(new TodoList(TodoListId.newRandom(), "my list 1", LIST_OWNER_1, List.of()));

    assertThat(cut.getTodoList(TodoListId.newRandom())).isEmpty();
  }

  @Test
  public void shouldReturnTodoListsOfGivenOwner() {
    final TodoList todoList1 = new TodoList(TodoListId.newRandom(), "my list 1", LIST_OWNER_1, List.of());
    cut.saveTodoList(todoList1);
    final TodoList todoList2 = new TodoList(TodoListId.newRandom(), "my list 2", LIST_OWNER_2, List.of());
    cut.saveTodoList(todoList2);

    assertThat(cut.getTodoListsByOwner(LIST_OWNER_1)).containsExactly(todoList1);
    assertThat(cut.getTodoListsByOwner(LIST_OWNER_2)).containsExactly(todoList2);
  }
}
