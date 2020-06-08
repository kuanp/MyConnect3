package ex.example.myconnect3;

import android.util.Log;

enum Player {
    RED, YELLOW
}

public class Board {
    private static final int NUM_ROWS = 3;
    private static final int NUM_COLS = 3;
    private static final String TAG = "MY_BOARD";
    private Player[][] board;
    private int numPieces;

    public Board() {
        initBoard();
    }

    public void clearBoard() {
        initBoard();
    }

    public void placePiece(Player player, int row, int col) {
        if (canPlacePiece(row, col)) {
            board[col][row] = player;
            numPieces++;
        } else {
            throw new RuntimeException(String.format(
                    "Bad placement for player %s, at row %s, col%s",
                    player,
                    row,
                    col));
        }
    }

    private boolean canPlacePiece(int row, int col) {
        return board[col][row] == null;
    }

    public boolean isFull() {
        return numPieces == NUM_COLS * NUM_ROWS;
    }

    public boolean hasWinner() {
        return hasHorizontalWinner() || hasVerticalWinner() || hasCrossingWinner();
    }

    private boolean hasHorizontalWinner() {
        for (int row = 0; row < NUM_ROWS; row ++) {
            if (board[0][row] != null && board[0][row] == board[1][row] && board[0][row] == board[2][row]) {
                return true;
            }
        }

        return false;
    }

    private boolean hasVerticalWinner() {
        for (int col = 0; col < NUM_COLS; col ++) {
            if (board[col][0] != null && board[col][0] == board[col][1] && board[col][0] == board[col][2]) {
                return true;
            }
        }

        return false;
    }

    private boolean hasCrossingWinner() {
        return board[1][1] != null && (board[0][0] == board[1][1] && board[0][0] == board[2][2]
                || board[0][2] == board[1][1] && board[0][2] == board[2][0]);
    }

    private void initBoard() {
        board = new Player[NUM_COLS][NUM_ROWS];
        numPieces = 0;
    }
}
