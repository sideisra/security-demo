package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.exception.NotFoundException;
import de.sideisra.securitydemo.model.ListOwner;
import de.sideisra.securitydemo.model.TodoList;
import de.sideisra.securitydemo.model.TodoListItem;
import de.sideisra.securitydemo.model.TodoListItemCreate;
import de.sideisra.securitydemo.model.meta.TodoListId;
import de.sideisra.securitydemo.model.meta.TodoListItemId;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for {@link TodoListService}.
 */
public class TodoListServiceTest {

  private static final ListOwner LIST_OWNER = new ListOwner("test@test.local", "Test", "Local");

  private final TodoListRepo todoListRepo = mock(TodoListRepo.class);

  private final TodoListService cut = new TodoListService(todoListRepo);

  @Test
  public void shouldReturnTodoListWithGivenId() {
    final TodoList todoList = new TodoList(TodoListId.newRandom(), "my list", LIST_OWNER, List.of());
    when(todoListRepo.getTodoList(todoList.getId())).thenReturn(Optional.of(todoList));

    assertThat(cut.getTodoList(todoList.getId())).isEqualTo(todoList);
  }

  @Test
  public void shouldThrowExceptionWhenGivenIdIsUnknownOnGetTodoList() {
    final TodoListId id = TodoListId.newRandom();
    when(todoListRepo.getTodoList(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> cut.getTodoList(id)).isInstanceOf(NotFoundException.class);
  }

  @Test
  public void shouldSaveNewTodoListWithNewItem() {
    final TodoListItem oldItem = new TodoListItem(TodoListItemId.newRandom(), "old item", false);
    final TodoList todoList = new TodoList(TodoListId.newRandom(), "my list", LIST_OWNER, List.of(oldItem));
    when(todoListRepo.getTodoList(todoList.getId())).thenReturn(Optional.of(todoList));

    final ArgumentCaptor<TodoList> todoListArgumentCaptor = ArgumentCaptor.forClass(TodoList.class);
    doNothing().when(todoListRepo).saveTodoList(todoListArgumentCaptor.capture());

    final String newItemValue = "new item";
    final boolean newItemDone = false;
    final TodoListItemId newItemId = cut.addItem(todoList.getId(), newItemValue, newItemDone);

    assertThat(newItemId).isNotNull();

    final TodoList capturedTodoList = todoListArgumentCaptor.getValue();
    assertThat(capturedTodoList).isNotSameAs(todoList);
    assertThat(capturedTodoList.getItems()).containsExactly(oldItem, new TodoListItem(newItemId, newItemValue, newItemDone));
  }

  @Test
  public void shouldThrowExceptionWhenGivenIdIsUnknownOnAddItem() {
    final TodoListId id = TodoListId.newRandom();
    when(todoListRepo.getTodoList(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> cut.addItem(id, null, false)).isInstanceOf(NotFoundException.class);
  }

  @Test
  public void shouldSaveNewTodoListWithChangedItem() {
    final TodoListItem oldItem = new TodoListItem(TodoListItemId.newRandom(), "old item", false);
    final TodoList todoList = new TodoList(TodoListId.newRandom(), "my list", LIST_OWNER, List.of(oldItem));
    when(todoListRepo.getTodoList(todoList.getId())).thenReturn(Optional.of(todoList));

    final ArgumentCaptor<TodoList> todoListArgumentCaptor = ArgumentCaptor.forClass(TodoList.class);
    doNothing().when(todoListRepo).saveTodoList(todoListArgumentCaptor.capture());

    final TodoListItem changedItem = new TodoListItem(oldItem.getId(), "changed item", true);
    cut.changeItem(todoList.getId(), changedItem);

    final TodoList capturedTodoList = todoListArgumentCaptor.getValue();
    assertThat(capturedTodoList).isNotSameAs(todoList);
    assertThat(capturedTodoList.getItems()).containsExactly(changedItem);
  }

  @Test
  public void shouldThrowExceptionWhenGivenIdIsUnknownOnChangeItem() {
    final TodoListId id = TodoListId.newRandom();
    when(todoListRepo.getTodoList(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> cut.changeItem(id, null)).isInstanceOf(NotFoundException.class);
  }

  @Test
  public void shouldSaveNewTodoList() {
    final TodoListItemCreate itemCreate = new TodoListItemCreate("item", false);
    final TodoListItem item = new TodoListItem(TodoListItemId.newRandom(), itemCreate.getValue(), itemCreate.isDone());

    final ArgumentCaptor<TodoList> todoListArgumentCaptor = ArgumentCaptor.forClass(TodoList.class);
    doNothing().when(todoListRepo).saveTodoList(todoListArgumentCaptor.capture());

    final TodoListId newId = cut.createTodoList("my-list", List.of(itemCreate), LIST_OWNER);

    assertThat(newId).isNotNull();
    final TodoList capturedTodoList = todoListArgumentCaptor.getValue();
    assertThat(capturedTodoList.getItems()).usingElementComparatorIgnoringFields("id").containsExactly(item);
    assertThat(capturedTodoList.getId()).isEqualTo(newId);
  }

}
