package br.com.devcoelho.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class SudokuBoard {

  private final List<List<SudokuSpace>> table;

  public SudokuBoard(final List<List<SudokuSpace>> newTable) {
    this.table = newTable;
  }

  public List<List<SudokuSpace>> getSpaces() {
    return this.table;
  }

  public void changeValue(final int col, final int row, final int value) {
    var space = table.get(col).get(row);
    space.setContent(value);
  }

  public void clearContent(final int col, final int row) {
    var space = table.get(col).get(row);
    space.clearCurrentSpace();
  }

  public void reset() {
    table.forEach(row -> row.forEach(SudokuSpace::clearCurrentSpace));
  }

  public boolean hasErrors() {
    return table.stream()
        .flatMap(Collection::stream)
        .anyMatch(s -> Objects.nonNull(s.getContent()) && !s.getContent().equals(s.getExpected()));
  }

  public boolean gameIsFinished() {
    return !hasErrors();
  }
}
