package robert.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import robert.exception.RobertException;
import robert.task.Todo;
import robert.task.Deadline;
import robert.task.Event;
import java.time.LocalDateTime;

public class ParserTest {

    @Test
    public void parseCommand_validCommands_success() {
        assertEquals("todo", Parser.parseCommand("todo read book"));
        assertEquals("deadline", Parser.parseCommand("deadline return book /by 2019-12-01 1800"));
        assertEquals("event", Parser.parseCommand("event meeting /from 2019-12-01 1400 /to 1600"));
        assertEquals("list", Parser.parseCommand("list"));
        assertEquals("bye", Parser.parseCommand("bye"));
        assertEquals("mark", Parser.parseCommand("mark 1"));
        assertEquals("unmark", Parser.parseCommand("unmark 2"));
    }

    @Test
    public void parseCommand_commandWithExtraSpaces_success() {
        assertEquals("todo", Parser.parseCommand("  todo   read book  "));
        assertEquals("deadline", Parser.parseCommand("deadline    return book /by 2019-12-01 1800"));
    }

    @Test
    public void parseTaskIndex_validIndex_success() throws RobertException {
        assertEquals(0, Parser.parseTaskIndex("mark 1"));
        assertEquals(4, Parser.parseTaskIndex("unmark 5"));
        assertEquals(9, Parser.parseTaskIndex("delete 10"));
    }

    @Test
    public void parseTaskIndex_invalidFormat_throwsException() {
        assertThrows(RobertException.class, () -> Parser.parseTaskIndex("mark"));
        assertThrows(RobertException.class, () -> Parser.parseTaskIndex("mark 1 2"));
        assertThrows(RobertException.class, () -> Parser.parseTaskIndex("mark abc"));
    }

    @Test
    public void parseTodo_validInput_success() throws RobertException {
        Todo todo = Parser.parseTodo("todo read book");
        assertEquals("[T][ ] read book", todo.toString());
    }

    @Test
    public void parseTodo_emptyDescription_throwsException() {
        assertThrows(RobertException.class, () -> Parser.parseTodo("todo"));
        assertThrows(RobertException.class, () -> Parser.parseTodo("todo   "));
    }

    @Test
    public void parseDeadline_validInput_success() throws RobertException {
        Deadline deadline = Parser.parseDeadline("deadline return book /by 2019-12-01 1800");
        assertTrue(deadline.toString().contains("[D][ ] return book"));
        assertTrue(deadline.toString().contains("Dec 1 2019, 6:00 pm"));
    }

    @Test
    public void parseDeadline_invalidFormat_throwsException() {
        assertThrows(RobertException.class, () -> Parser.parseDeadline("deadline return book"));
        assertThrows(RobertException.class, () -> Parser.parseDeadline("deadline /by 2019-12-01 1800"));
        assertThrows(RobertException.class, () -> Parser.parseDeadline("deadline return book /by"));
        assertThrows(RobertException.class, () -> Parser.parseDeadline("deadline return book /by invalid-date"));
    }

    @Test
    public void parseEvent_validInput_success() throws RobertException {
        Event event = Parser.parseEvent("event project meeting /from 2019-12-02 1400 /to 1600");
        assertTrue(event.toString().contains("[E][ ] project meeting"));
        assertTrue(event.toString().contains("Dec 2 2019, 2:00 pm"));
        assertTrue(event.toString().contains("4:00 pm"));
    }

    @Test
    public void parseEvent_invalidFormat_throwsException() {
        assertThrows(RobertException.class, () -> Parser.parseEvent("event project meeting"));
        assertThrows(RobertException.class, () -> Parser.parseEvent("event /from 2019-12-02 1400 /to 1600"));
        assertThrows(RobertException.class, () -> Parser.parseEvent("event project meeting /from 2019-12-02 1400"));
        assertThrows(RobertException.class, () -> Parser.parseEvent("event project meeting /from invalid /to 1600"));
    }
}