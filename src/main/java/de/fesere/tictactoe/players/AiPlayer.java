package de.fesere.tictactoe.players;

import de.fesere.tictactoe.Board;
import de.fesere.tictactoe.Mark;
import de.fesere.tictactoe.Player;

import java.util.Collections;
import java.util.List;

public class AiPlayer implements Player {

  private Mark mark;

  public AiPlayer(Mark mark) {
    this.mark = mark;
  }

  @Override
  public Board performMove(Board board) {
    int bestMove = bestMove(board);
    return board.nextBoardFor(bestMove, mark);
  }

  private int bestMove(Board board) {
    int bestMove = -1;
    int bestScore = -10;
    for(int move : shuffledMoves(board)) {
      Board newBoard = board.nextBoardFor(move, mark);
      int score = -negamax(newBoard,-10, 10, mark.opponent());

      if(score > bestScore) {
        bestScore = score;
        bestMove = move;
      }
    }
    return bestMove;
  }

  private int negamax(Board board, int alpha, int beta, Mark mark) {
    int bestMove = -1;

    if (board.isFinished()) {
      return valueOfBoard(board, mark);
    } else {
      return getRatedMove(board, alpha, beta, mark);
    }
  }

  private int getRatedMove(Board board, int alpha, int beta, Mark mark) {
    int bestMove = -1;
    int bestScore = alpha;
    for(int move : shuffledMoves(board)) {
      Board newBoard = board.nextBoardFor(move, mark);
      int score = -negamax(newBoard, -beta, -alpha, mark.opponent());

      if(score > bestScore) {
        bestScore = score;
        bestMove = move;
      }

      alpha = Math.max(score, alpha);
      if(alpha >= beta) {
        break;
      }
    }
    return bestScore;
  }

  private int valueOfBoard(Board board, Mark mark) {
    if (board.isWinner(mark)) {
      return board.getScore();
    } else if (board.isWinner(mark.opponent())) {
      return -board.getScore();
    } else {
      return 0;
    }
  }

  private List<Integer> shuffledMoves(Board board) {
    List<Integer> result = board.getPossibleMoves();
    Collections.shuffle(result);
    return result;
  }

  @Override
  public Mark getMark() {
    return mark;
  }

  private class RatedMove {
    public final int score;
    public final int location;

    public RatedMove(int score, int location) {
      this.score = score;
      this.location = location;
    }
  }
}
