package robert.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class TaskListTest {
    private TaskList taskList;
    private Todo todo;
    private Deadline deadline;
    private Event event;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        todo = new Todo("read book");
        deadline = new Deadline("return book", LocalDateTime.of(2019, 12, 1, 18, 0));
        event = new Event("project meeting", 
                         LocalDateTime.of(2019, 12, 2, 14, 0),
                         LocalDateTime.of(2019, 12, 2, 16, 0));
    }

    @Test
    public void constructor_emptyConstructor_createsEmptyList() {
        assertEquals(0, taskList.size());
    }

    @Test
    public void constructor_withArrayList_createsListWithTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(todo);
        tasks.add(deadline);
        
        TaskList newTaskList = new TaskList(tasks);
        assertEquals(2, newTaskList.size());
        assertEquals(todo, newTaskList.get(0));
        assertEquals(deadline, newTaskList.get(1));
    }

    @Test
    public void add_singleTask_increasesSize() {
        taskList.add(todo);
        assertEquals(1, taskList.size());
        assertEquals(todo, taskList.get(0));
    }

    @Test
    public void add_multipleTasks_maintainsOrder() {
        taskList.add(todo);
        taskList.add(deadline);
        taskList.add(event);
        
        assertEquals(3, taskList.size());
        assertEquals(todo, taskList.get(0));
        assertEquals(deadline, taskList.get(1));
        assertEquals(event, taskList.get(2));
    }

    @Test
    public void remove_validIndex_removesAndReturnsTask() {
        taskList.add(todo);
        taskList.add(deadline);
        
        Task removed = taskList.remove(0);
        assertEquals(todo, removed);
        assertEquals(1, taskList.size());
        assertEquals(deadline, taskList.get(0));
    }

    @Test
    public void get_validIndex_returnsCorrectTask() {
        taskList.add(todo);
        taskList.add(deadline);
        
        assertEquals(todo, taskList.get(0));
        assertEquals(deadline, taskList.get(1));
    }

    @Test
    public void markTask_validIndex_marksTaskAsDone() {
        taskList.add(todo);
        assertFalse(todo.getStatusIcon().equals("X"));
        
        taskList.markTask(0);
        assertTrue(todo.getStatusIcon().equals("X"));
    }

    @Test
    public void unmarkTask_validIndex_marksTaskAsNotDone() {
        taskList.add(todo);
        todo.markAsDone();
        assertTrue(todo.getStatusIcon().equals("X"));
        
        taskList.unmarkTask(0);
        assertFalse(todo.getStatusIcon().equals("X"));
    }

    @Test
    public void size_afterOperations_returnsCorrectSize() {
        assertEquals(0, taskList.size());
        
        taskList.add(todo);
        assertEquals(1, taskList.size());
        
        taskList.add(deadline);
        assertEquals(2, taskList.size());
        
        taskList.remove(0);
        assertEquals(1, taskList.size());
    }

    @Test
    public void getTasks_returnsCorrectArrayList() {
        taskList.add(todo);
        taskList.add(deadline);
        
        ArrayList<Task> tasks = taskList.getTasks();
        assertEquals(2, tasks.size());
        assertEquals(todo, tasks.get(0));
        assertEquals(deadline, tasks.get(1));
    }
}