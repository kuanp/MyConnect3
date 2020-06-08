package ex.example.myconnect3;

import android.util.Log;

import java.util.Random;

enum GameState {
   OPEN, HAS_WINNER, NO_WINNER
}

public class Game {
   public Board board;
   public Player currentPlayer;
   public GameState gameState;
   private Random rand = new Random();
   private static final String TAG = "MY_GAME";

   public Game() {
      board = new Board();
      chooseNewPlayer();
      gameState = GameState.OPEN;
   }

   public void makeMove(int row, int col) {
      Log.v(TAG, String.format("clicked on row %s, col %s.", row, col));
      board.placePiece(currentPlayer, row, col);
      computeNewGameState();
      if (gameState == GameState.OPEN) {
         endTurnForCurrentPlayer();
      }
   }

   private void computeNewGameState() {
      if (board.hasWinner()) {
         gameState = GameState.HAS_WINNER;
      } else if (board.isFull()) {
         gameState = GameState.NO_WINNER;
      }
   }

   private void endTurnForCurrentPlayer() {
      if (currentPlayer == Player.RED) {
         currentPlayer = Player.YELLOW;
      } else {
         currentPlayer = Player.RED;
      }
   }

   private void chooseNewPlayer() {
      if (rand.nextBoolean()) {
         currentPlayer = Player.RED;
      } else {
         currentPlayer = Player.YELLOW;
      }
   }
}
