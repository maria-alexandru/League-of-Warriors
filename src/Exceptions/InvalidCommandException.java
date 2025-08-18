package Exceptions;

public class InvalidCommandException extends RuntimeException {
  public InvalidCommandException() {
    super("Invalid command - Please enter a valid command");
  }
}

