package br.com.devcoelho;

import br.com.devcoelho.model.SudokuBoard;
import java.util.Scanner;

/** Hello world! */
public class App {

  private static final Scanner scanner = new Scanner(System.in);

  private static SudokuBoard board;

  private static final int BOARD_LIMIT = 9;

  public static void main(String[] args) {
    final var positions =
        Stream.of(args).collect(toMap(k -> k.split(";")[0], v -> v.split(";")[1]));
    var option = -1;
  }
}
