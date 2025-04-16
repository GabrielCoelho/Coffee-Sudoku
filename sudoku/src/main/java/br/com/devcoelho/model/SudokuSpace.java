package br.com.devcoelho.model;

public class SudokuSpace {
  private Integer content;
  private final int expected;

  public SudokuSpace(final int expected) {
    this.expected = expected;
  }

  public Integer getContent() {
    return this.content;
  }

  public void setContent(Integer number) {
    this.content = number;
  }

  public void clearCurrentSpace() {
    setContent(null);
  }

  public int getExpected() {
    return this.expected;
  }
}
