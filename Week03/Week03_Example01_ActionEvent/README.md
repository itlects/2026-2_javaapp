# Week03 Example01 - ActionEvent and EDT

## Event handling
- Event Source: JButton
- Event: ActionEvent
- Listener: ActionListener
- Handler: lambda expression / method body

## EventQueue.invokeLater()
The main method uses:

```java
EventQueue.invokeLater(() ->
    new ActionEventExample().frame.setVisible(true)
);
```

This schedules Swing GUI creation and display on the Event Dispatch Thread (EDT).

- `main()` begins on the main thread.
- Swing components are generally not thread-safe.
- GUI creation and updates should run on the EDT.
- `invokeLater()` accepts a Runnable and places it in the Event Queue.
- `invokeLater()` does not create a background worker thread.
- Long-running work should use a worker mechanism such as `SwingWorker` rather than blocking the EDT.

Execution flow:

```text
main()
 -> invokeLater()
 -> Event Queue
 -> EDT
 -> ActionEventExample()
 -> frame.setVisible(true)
```
