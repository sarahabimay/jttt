package de.fesere.tictactoe;

import org.junit.Test;

import static de.fesere.tictactoe.Mark.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MarkTest {

  @Test
  public void opponentOfXisO() {
     assertThat(X.opponent(), is(O));
  }

  @Test
  public void opponentOfOisX() {
    assertThat(O.opponent(), is(X));
  }
}