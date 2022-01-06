import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class TodoList {

    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<String> todoList = new ArrayList<>();
    private final Path todoListFile = FileSystems.getDefault().getPath("src", "todoList.txt");

    private void printInstruction() {
        System.out.println("0: Quit");
        System.out.println("1: Print this instruction again");
        System.out.println("2: Show all available todos");
        System.out.println("3: Add a todo");
        System.out.println("4: Remove a todo");
        System.out.println("5: Update an existing todo");
    }

    private void showAllTodos() {
        System.out.println("This is all that you need to do: ");
        if (todoList.isEmpty()) {
            System.out.println("Nothing!");
        } else {
            for(int i = 0; i < todoList.size(); i ++) {
                System.out.println((i+1)+". "+ todoList.get(i));
            }
        }
    }

    private void addTodo() {
        System.out.println("Enter what you need to do: ");
        todoList.add(scanner.nextLine());
        System.out.println("Todo added successfully");
    }

    private void removeTodo() {
        System.out.println("Enter the index of the todo you want to remove: ");
        int inputIndex = scanner.nextInt();
        scanner.nextLine();
        int index = inputIndex - 1;
        try {
            todoList.remove(index);
            System.out.println("Todo "+(index + 1)+" removed successfully");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("The todo at index: "+inputIndex+" cannot be found, please try again or enter 2 to view all todos");
        }
    }

    private void updateAnTodo() {
        System.out.println("Enter the index of the todo you want to update");
        int inputIndex = scanner.nextInt();
        scanner.nextLine();
        try {
            int index = inputIndex - 1;
            System.out.println("Enter the new content for this todo: ");
            String newTodoContent = scanner.nextLine();
            todoList.set(index, newTodoContent);
            System.out.println("Todo "+inputIndex+" updated successfully");
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("The todo at index: "+inputIndex+" cannot be found, please try again or enter 2 to view all todos");
        }
    }

    private void readTodosFromFile() {
        Charset charset = StandardCharsets.US_ASCII;
        try (BufferedReader reader = Files.newBufferedReader(todoListFile, charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                todoList.add(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void saveTodosToFile() {
        Charset charset = StandardCharsets.US_ASCII;
        try (BufferedWriter writer = Files.newBufferedWriter(todoListFile, charset)) {
            for (int i = 0; i < todoList.size(); i ++) {
                String todo = todoList.get(i)+"\n";
                writer.write(todo, 0, todo.length());
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public void todoListApp() {
        boolean quit = false;
        readTodosFromFile();
        printInstruction();

        while (!quit) {
            System.out.println("Welcome to the todo list app, what do you want to do today ?");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 0:
                    saveTodosToFile();
                    System.out.println("See you later!");
                    quit = true;
                    break;
                case 1:
                    printInstruction();
                    break;
                case 2:
                    showAllTodos();
                    break;
                case 3:
                    addTodo();
                    break;
                case 4:
                    removeTodo();
                    break;
                case 5:
                    updateAnTodo();
                    break;
                default:
                    System.out.println("Please enter a valid choice or enter 1 to show the instruction");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        TodoList todoList = new TodoList();
        todoList.todoListApp();
    }
}
