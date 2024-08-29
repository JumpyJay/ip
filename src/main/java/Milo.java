import java.util.Scanner;
import java.util.ArrayList;
import TaskObj.Deadlines;
import TaskObj.Events;
import TaskObj.Task;
import TaskObj.Todos;
import TaskObj.TaskType;

public class Milo {


    public static void main(String[] args) {
        String cat0 = """
                  ╱|、
                (˚ˎ 。7 \s
                 |、˜〵         \s
                 じしˍ,)ノ
                """;
        String cat1 = """
                 ∧,,,∧
                ( ̳• · •̳)
                /    づ♡
                """;
        String greeting = "Hello! I'm Milo\nWhat can I do for you?\n" + cat0;
        String bye = "Bye. Hope to see you again soon!\n" + cat1;
        Scanner myScanner = new Scanner(System.in);
        String greetingMessage = Printer.hLine + greeting + Printer.hLine;
        String byeMessage = Printer.hLine + bye + Printer.hLine;
        // Database to store text
        ArrayList<Task> todoList = new ArrayList<>();

        // Greets user
        System.out.println(greetingMessage);
        // Await user input
        String userInput = myScanner.nextLine();
        // Loop for user input
        while (!userInput.toLowerCase().strip().equals("bye")) {
            // Split input on space
            String[] arrOfInput = userInput.split(" ", 2);
            String action = arrOfInput[0];
            switch (action) {
                // Show list
                case "list":
                    Printer.printList(todoList);
                    userInput = myScanner.nextLine();
                    break;
                // Mark as complete
                case "mark":
                    Task curTask = todoList.get(Integer.parseInt(arrOfInput[1]) - 1);
                    curTask.mark();
                    Printer.printMark(curTask);
                    userInput = myScanner.nextLine();
                    break;
                // Mark as incomplete
                case "unmark":
                    Task currTask = todoList.get(Integer.parseInt(arrOfInput[1]) - 1);
                    currTask.unmark();
                    Printer.printUnmark(currTask);
                    userInput = myScanner.nextLine();
                    break;
                // Deleting tasks
                case "delete":
                    int delIndex = Integer.parseInt(arrOfInput[1]) - 1;
                    Task currrTask = todoList.get(delIndex);
                    currrTask.delete();
                    todoList.remove(delIndex);
                    Printer.printDelete(currrTask);
                    userInput = myScanner.nextLine();
                    break;
                // Adding tasks
                // Todos
                case "todo":
                    // Check case where todos empty
                    if (arrOfInput.length == 1) {
                        Printer.printError(TaskType.taskType.TODO, "The description of a todo cannot be empty");
                    } else {
                        String desc = arrOfInput[1];
                        Task curTodo = new Todos(desc);
                        todoList.add(curTodo);
                        Printer.printTask(curTodo);
                    }
                    userInput = myScanner.nextLine();
                    break;
                // Deadline
                case "deadline":
                    // Check case where deadline empty
                    if (arrOfInput.length == 1) {
                        Printer.printError(TaskType.taskType.DEADLINE, "The description of a deadline cannot be empty");
                    } else {
                        // Check case where deadline command is not properly formatted
                        String[] deadlineDesc = arrOfInput[1].split("/by", 2);
                        if (deadlineDesc.length != 2) {
                            Printer.printError(TaskType.taskType.DEADLINE, "Invalid deadline command\n Proper formatting: deadline <task description> + /by + <date description>");
                        } else {
                            Task curDeadline = new Deadlines(deadlineDesc[0], deadlineDesc[1]);
                            todoList.add(curDeadline);
                            Printer.printTask(curDeadline);
                        }
                    }
                    userInput = myScanner.nextLine();
                    break;
                // Event
                case "event":
                    // Check case where event empty
                    if (arrOfInput.length == 1) {
                        Printer.printError(TaskType.taskType.EVENT, "The description of an event cannot be empty");
                    } else {
                        // Check case where event command is not properly formatted
                        String[] eventDesc = arrOfInput[1].split("/from | /to", 3);
                        if (eventDesc.length != 3) {
                            Printer.printError(TaskType.taskType.DEADLINE, "Invalid event command\n Proper formatting: deadline <task description> + /from + <starting date description> + /to + <ending date description");
                        } else {
                            Task curEvent = new Events(eventDesc[0], eventDesc[1], eventDesc[2]);
                            todoList.add(curEvent);
                            Printer.printTask(curEvent);
                        }
                    }
                    userInput = myScanner.nextLine();
                    break;
                default:
                    Printer.printError(TaskType.taskType.INVALID, "");
                    userInput = myScanner.nextLine();
            }
        }

        // User input == "bye"
        System.out.print(byeMessage);
    }


}
