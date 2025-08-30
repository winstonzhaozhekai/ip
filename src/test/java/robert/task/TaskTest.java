package robert.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    
    @Test
    public void constructor_newTask_isNotDone() {
        Todo todo = new Todo("test task");
        assertEquals(" ", todo.getStatusIcon());
        assertEquals(TaskType.TODO, todo.getType());
    }

    @Test
    public void markAsDone_task_changesStatus() {
        Todo todo = new Todo("test task");
        todo.markAsDone();
        assertEquals("X", todo.getStatusIcon());
    }

    @Test
    public void markAsNotDone_doneTask_changesStatus() {
        Todo todo = new Todo("test task");
        todo.markAsDone();
        todo.markAsNotDone();
        assertEquals(" ", todo.getStatusIcon());
    }

    @Test
    public void toString_todo_correctFormat() {
        Todo todo = new Todo("read book");
        assertEquals("[T][ ] read book", todo.toString());
        
        todo.markAsDone();
        assertEquals("[T][X] read book", todo.toString());
    }
}