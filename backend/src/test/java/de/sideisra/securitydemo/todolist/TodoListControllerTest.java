package de.sideisra.securitydemo.todolist;

import de.sideisra.securitydemo.exception.AccessDeniedException;
import de.sideisra.securitydemo.model.*;
import de.sideisra.securitydemo.model.meta.TodoListId;
import de.sideisra.securitydemo.model.meta.TodoListItemId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TodoListControllerTest {
  private static final ListOwner LIST_OWNER_1 = new ListOwner("test@test.local", "Test", "Local");
  private static final Jwt USER_1 = mock(Jwt.class);
  private static final ListOwner LIST_OWNER_2 = new ListOwner("test-2@test.local", "Test 2", "Local 2");
  private static final Jwt USER_2 = mock(Jwt.class);

  private final TodoListService todoListService = mock(TodoListService.class);

  private final TodoListController cut = new TodoListController(todoListService);

  @BeforeEach
  public void setUp() {
    when(USER_1.getClaimAsString("email")).thenReturn(LIST_OWNER_1.geteMail());
    when(USER_1.getClaimAsString("family_name")).thenReturn(LIST_OWNER_1.getLastName());
    when(USER_1.getClaimAsString("given_name")).thenReturn(LIST_OWNER_1.getFirstName());
    when(USER_2.getClaimAsString("email")).thenReturn(LIST_OWNER_2.geteMail());
    when(USER_2.getClaimAsString("family_name")).thenReturn(LIST_OWNER_2.getLastName());
    when(USER_2.getClaimAsString("given_name")).thenReturn(LIST_OWNER_2.getFirstName());
  }

  @Test
  public void shouldTransformGivenPrincipalToListOwnerToAccessAllListsOfOwner() {
    final TodoList todoList1 = new TodoList(TodoListId.newRandom(), "my list 1", LIST_OWNER_1, List.of());
    final TodoList todoList2 = new TodoList(TodoListId.newRandom(), "my list 2", LIST_OWNER_2, List.of());
    when(todoListService.getTodoListsByOwner(LIST_OWNER_1)).thenReturn(List.of(todoList1));
    when(todoListService.getTodoListsByOwner(LIST_OWNER_2)).thenReturn(List.of(todoList2));

    assertThat(cut.getTodoListsByOwner(USER_1)).containsExactly(todoList1);
  }

  @Test
  public void shouldCheckOwnerWhenAccessingASpecificList() {
    final TodoList todoList = new TodoList(TodoListId.newRandom(), "my list", LIST_OWNER_1, List.of());
    when(todoListService.getTodoList(todoList.getId())).thenReturn(todoList);

    assertThat(cut.getTodoList(todoList.getId(), USER_1)).isEqualTo(todoList);
    assertThatThrownBy(() -> cut.getTodoList(todoList.getId(), USER_2)).isInstanceOf(AccessDeniedException.class);
  }

  @Test
  public void shouldSetTheOwnerOfANewList() {
    final TodoListCreate todoListCreate = new TodoListCreate("my list", List.of());
    final TodoListId newListId = TodoListId.newRandom();
    when(todoListService.createTodoList(todoListCreate.getName(), todoListCreate.getItems(), LIST_OWNER_1))
        .thenReturn(newListId);

    assertThat(cut.createTodoList(todoListCreate, USER_1)).isEqualTo(newListId);
  }

  @Test
  public void shouldCheckTheOwnerOfTheListWhenAddingANewItem() {
    final TodoListId todoListId = TodoListId.newRandom();
    final TodoList todoList = new TodoList(todoListId, "my list", LIST_OWNER_1, List.of());
    when(todoListService.getTodoList(todoList.getId())).thenReturn(todoList);
    final TodoListItemCreate newTodoListItem = new TodoListItemCreate("item value", true);
    final TodoListItemId newItemId = TodoListItemId.newRandom();

    when(todoListService.addItem(todoListId, newTodoListItem.getValue(), newTodoListItem.isDone()))
        .thenReturn(newItemId);

    assertThat(cut.addItem(todoListId, newTodoListItem, USER_1)).isEqualTo(newItemId);
    assertThatThrownBy(() -> cut.addItem(todoListId, newTodoListItem, USER_2))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Test
  public void shouldCheckTheOwnerOfTheListWhenChangingAnItem() {
    final TodoListId todoListId = TodoListId.newRandom();
    final TodoList todoList = new TodoList(todoListId, "my list", LIST_OWNER_1, List.of());
    when(todoListService.getTodoList(todoList.getId())).thenReturn(todoList);
    final TodoListItemId changedItemId = TodoListItemId.newRandom();
    final TodoListItem changedTodoListItem = new TodoListItem(changedItemId, "item value", true);

    cut.changeItem(todoListId, changedItemId, changedTodoListItem, USER_1);
    assertThatThrownBy(() -> cut.changeItem(todoListId, changedItemId, changedTodoListItem, USER_2))
        .isInstanceOf(AccessDeniedException.class);
  }

  @Test
  public void shouldCheckTheIdOfTheChangedItem() {
    final TodoListId todoListId = TodoListId.newRandom();
    final TodoList todoList = new TodoList(todoListId, "my list", LIST_OWNER_1, List.of());
    when(todoListService.getTodoList(todoList.getId())).thenReturn(todoList);
    final TodoListItemId changedItemId = TodoListItemId.newRandom();
    final TodoListItem changedTodoListItem = new TodoListItem(changedItemId, "item value", true);
    final TodoListItemId otherItemId = TodoListItemId.newRandom();

    assertThatThrownBy(() -> cut.changeItem(todoListId, otherItemId, changedTodoListItem, USER_1))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
