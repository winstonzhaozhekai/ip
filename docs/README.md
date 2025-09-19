# Robert User Guide

Robert is a friendly chatbot that helps you manage your tasks from the command line or GUI. You can add todos, deadlines, and events, mark them as done, search, and delete them. Your tasks are saved automatically.

## Features

- Add tasks: Todo, Deadline, Event
- Mark/unmark tasks as done
- Delete tasks
- List all tasks
- Find tasks by keyword
- Prevent duplicate tasks
- Persistent storage

---

## Getting Started

1. **Run Robert**  
   - **CLI:** Run `java -classpath bin robert.Robert`
   - **GUI:** Run `gradlew run` or launch `Launcher.java` in your IDE

2. **Basic Commands**
   - `todo <description>`: Adds a todo task
   - `deadline <description> /by <yyyy-MM-dd HHmm>`: Adds a deadline
   - `event <description> /from <yyyy-MM-dd HHmm> /to <HHmm or yyyy-MM-dd HHmm>`: Adds an event
   - `list`: Shows all tasks
   - `mark <task number>`: Marks a task as done
   - `unmark <task number>`: Marks a task as not done
   - `delete <task number>`: Deletes a task
   - `find <keyword>`: Finds tasks containing the keyword
   - `bye`: Exits the program

---

## Example Usage

### Adding a Todo

**Input:**
```
todo read book
```

**Output:**
```
Got it. I've added this task:
  [T][ ] read book
Now you have 1 task(s) in the list.
```

### Adding a Deadline

**Input:**
```
deadline return book /by 2019-12-01 1800
```

**Output:**
```
Got it. I've added this task:
  [D][ ] return book (by: Dec 1 2019, 6:00 pm)
Now you have 2 task(s) in the list.
```

### Adding an Event

**Input:**
```
event project meeting /from 2019-12-02 1400 /to 1600
```

**Output:**
```
Got it. I've added this task:
  [E][ ] project meeting (from: Dec 2 2019, 2:00 pm to: 4:00 pm)
Now you have 3 task(s) in the list.
```

### Listing Tasks

**Input:**
```
list
```

**Output:**
```
Here are the tasks in your list:
1. [T][ ] read book
2. [D][ ] return book (by: Dec 1 2019, 6:00 pm)
3. [E][ ] project meeting (from: Dec 2 2019, 2:00 pm to: 4:00 pm)
```

### Marking/Unmarking Tasks

**Input:**
```
mark 1
```
**Output:**
```
Nice! I've marked this task as done:
  [T][X] read book
```

**Input:**
```
unmark 1
```
**Output:**
```
OK, I've marked this task as not done yet:
  [T][ ] read book
```

### Deleting a Task

**Input:**
```
delete 2
```
**Output:**
```
Noted. I've removed this task:
  [D][ ] return book (by: Dec 1 2019, 6:00 pm)
Now you have 2 task(s) in the list.
```

### Finding Tasks

**Input:**
```
find book
```
**Output:**
```
Here are the matching tasks in your list:
1.[T][ ] read book
```

### Exiting

**Input:**
```
bye
```
**Output:**
```
Bye. Hope to see you again soon!
```

---

## Notes

- Dates must be in `yyyy-MM-dd HHmm` format (e.g., `2019-12-01 1800`)
- Duplicate tasks are not allowed
- All tasks are saved in `data/duke.txt`
- GUI version available with JavaFX

---

## Troubleshooting

- If you see "Error loading tasks", your data file may be missing or corrupted. Robert will start with an empty list.
- For GUI issues, ensure JavaFX is installed and configured.

---

## Contributors

See [CONTRIBUTORS.md](../CONTRIBUTORS.md) for details.