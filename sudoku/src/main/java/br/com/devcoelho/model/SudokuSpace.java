package br.com.devcoelho.model;

public class SudokuSpace {
  private Integer content;
  private final int expected;
  private final boolean fixed;

  public SudokuSpace(final int expected, final boolean fixed) {
    this.expected = expected;
    this.fixed = fixed;
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

  public boolean isFixed() {
    return this.fixed;
  }
}
