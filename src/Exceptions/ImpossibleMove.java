package Exceptions;

public class ImpossibleMove extends RuntimeException {
  public ImpossibleMove() {
    super("Out of grid - Please enter a valid move");
  }
}
