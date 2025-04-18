package br.com.devcoelho;

import static br.com.devcoelho.util.BoardTemplate.BOARD_TEMPLATE;

import br.com.devcoelho.model.SudokuBoard;
import br.com.devcoelho.model.SudokuSpace;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Hello world! */
public class App {

  private static final Scanner scanner = new Scanner(System.in);

  private static SudokuBoard board;

  private static final int BOARD_LIMIT = 9;

  public static void main(String[] args) {
    final Map<String, String> positions =
        Stream.of(args).collect(Collectors.toMap(k -> k.split(";")[0], v -> v.split(";")[1]));
    var option = -1;

    System.out.println(positions);
    while (true) {
      System.out.println("Selecione uma opção: ");
      System.out.println("1. Iniciar um novo jogo");
      System.out.println("2. Colocar um novo número");
      System.out.println("3. Remover um número");
      System.out.println("4. Visualizar jogo");
      System.out.println("5. Verificar status do jogo");
      System.out.println("6. Limpar o jogo");
      System.out.println("7. Finalizar");
      System.out.println("8. Sair");

      option = Integer.valueOf(scanner.nextLine());
      switch (option) {
        case 1 -> startGame(positions);
        case 2 -> inputNewNumber();
        case 3 -> removeNumber();
        case 4 -> showGame();
        case 5 -> showStatus();
        case 6 -> clearGame();
        case 7 -> finishGame();
        case 8 -> System.exit(0);
        default -> System.out.println("Selecione uma opção válida");
      }
    }
  }

  private static void startGame(final Map<String, String> positions) {
    if (Objects.nonNull(board)) {
      System.out.println("Jogo já iniciado");
      return;
    }

    List<List<SudokuSpace>> spaces = new ArrayList<>();
    for (int i = 0; i < BOARD_LIMIT; i++) {
      spaces.add(new ArrayList<>());
      for (int j = 0; j < BOARD_LIMIT; j++) {
        var position = positions.get("%s, %s").formatted(i, j);
        Integer expected = Integer.parseInt(position.split(",")[0]);
        Boolean fixed = Boolean.parseBoolean(position.split(",")[1]);
        spaces.get(i).add(new SudokuSpace(expected, fixed));
      }
    }

    board = new SudokuBoard(spaces);
    System.out.println("O jogo está pronto para começar");
  }

  private static void inputNewNumber() {
    if (Objects.isNull(board)) {
      System.out.println("O jogo ainda não foi iniciado iniciado");
      return;
    }

    System.out.println("Informe a coluna que em que o número será inserido");
    var col = runUntilGetValidNumber(0, 8);
    System.out.println("Informe a linha que em que o número será inserido");
    var row = runUntilGetValidNumber(0, 8);
    System.out.printf("Informe o número que vai entrar na posição [%s,%s]\n", col, row);
    var value = runUntilGetValidNumber(1, 9);
    board.changeValue(col, row, value);
  }

  private static void removeNumber() {
    if (Objects.isNull(board)) {
      System.out.println("O jogo ainda não foi iniciado iniciado");
      return;
    }

    System.out.println("Informe a coluna que em que o número será inserido");
    var col = runUntilGetValidNumber(0, 8);
    System.out.println("Informe a linha que em que o número será inserido");
    var row = runUntilGetValidNumber(0, 8);
    board.clearContent(col, row);
  }

  private static void showGame() {
    if (Objects.isNull(board)) {
      System.out.println("O jogo ainda não foi iniciado iniciado");
      return;
    }

    var args = new Object[81];
    var argPos = 0;
    for (int i = 0; i < BOARD_LIMIT; i++) {
      for (var col : board.getSpaces()) {
        args[argPos++] =
            " " + ((Objects.isNull(col.get(i).getContent())) ? " " : col.get(i).getContent());
      }
    }
    System.out.println("Seu jogo se encontra da seguinte forma");
    System.out.printf((BOARD_TEMPLATE) + "\n", args);
  }

  private static void showStatus() {
    if (Objects.isNull(board)) {
      System.out.println("O jogo ainda não foi iniciado iniciado");
      return;
    }

    if (board.hasErrors()) {
      System.out.println("O jogo contém erros");
    } else {
      System.out.println("O jogo não contém erros");
    }
  }

  private static void clearGame() {
    if (Objects.isNull(board)) {
      System.out.println("O jogo ainda não foi iniciado iniciado");
      return;
    }

    System.out.println("Tem certeza que deseja limpar seu jogo e perder todo seu progresso?");
    var confirm = scanner.next();
    while (!confirm.equalsIgnoreCase("sim") && !confirm.equalsIgnoreCase("não")) {
      System.out.println("Informe 'sim' ou 'não'");
      confirm = scanner.next();
    }

    if (confirm.equalsIgnoreCase("sim")) {
      board.reset();
    }
  }

  private static void finishGame() {
    if (Objects.isNull(board)) {
      System.out.println("O jogo ainda não foi iniciado iniciado");
      return;
    }

    if (board.gameIsFinished()) {
      System.out.println("Parabéns você concluiu o jogo");
      showGame();
      board = null;
    } else if (board.hasErrors()) {
      System.out.println("Seu jogo conté, erros, verifique seu board e ajuste-o");
    } else {
      System.out.println("Você ainda precisa preenhcer algum espaço");
    }
  }

  private static int runUntilGetValidNumber(final int min, final int max) {
    var current = scanner.nextInt();
    while (current < min || current > max) {
      System.out.printf("Informe um número entre %s e %s\n", min, max);
      current = scanner.nextInt();
    }
    return current;
  }
}
