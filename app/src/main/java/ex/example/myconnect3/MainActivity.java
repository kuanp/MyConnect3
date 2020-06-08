package ex.example.myconnect3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Game currentGame;
    private static final String TAG = "MY_MAIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startNewGame(null);
    }

    public void cellClickedOn(View view) {
        Cell cell = (Cell) view.getTag();
        if (!cell.hasPiece && currentGame.gameState == GameState.OPEN) {
            // only perform actions if cell doesn't have any piece currently
            cell.hasPiece = true;
            displayPiece(view);
            currentGame.makeMove(cell.row, cell.col);
            Log.v(TAG, currentGame.gameState.toString());
            if (currentGame.gameState == GameState.OPEN) {
                displayCurrentTurn();
            } else {
                displayEndGamePrompt();
            }
        }
    }

    public void startNewGame(View view) {
        currentGame = new Game();
        resetLayoutState();
        displayCurrentTurn();
    }

    private void displayPiece(View view) {
        final ImageView imageView = (ImageView) view;
        if (currentGame.currentPlayer == Player.RED) {
            imageView.setImageResource(R.drawable.red);
        } else {
            imageView.setImageResource(R.drawable.yellow);
        }
        imageView.setTranslationY(-2000);
        imageView.animate().translationYBy(2000).rotationBy(720).alpha(1).setDuration(2000);
    }

    private void resetLayoutState() {
        hideWinnerLayout();
        hideBoardPieces();
    }

    private void displayCurrentTurn() {
        TextView turnTextView = findViewById(R.id.turnTextView);
        turnTextView.setText(String.format("%s's turn", currentGame.currentPlayer.toString()));
    }

    private void hideWinnerLayout() {
        findViewById(R.id.winnerLayout).setTranslationY(-3000);
    }

    private void displayEndGamePrompt() {
        LinearLayout winnerLayout = findViewById(R.id.winnerLayout);
        TextView winnerText = findViewById(R.id.winnerTextView);
        if (currentGame.gameState == GameState.HAS_WINNER) {
            winnerText.setText(currentGame.currentPlayer.toString() + " WINS!");
            winnerLayout.animate().translationYBy(3000).rotationBy(720).setDuration(2000);
        } else {
            winnerText.setText("It's a tie...");
            winnerLayout.animate().translationYBy(3000).setDuration(2000);
        }
    }

    private void hideBoardPieces() {
        TableLayout boardLayout = findViewById(R.id.boardLayout);
        for (int i = 0; i < boardLayout.getChildCount(); i++) {
            TableRow row = (TableRow) boardLayout.getChildAt(i);
            for (int j = 0; j < row.getChildCount(); j++) {
                ImageView cellImageView = (ImageView) row.getChildAt(j);
                cellImageView.setAlpha(0f);
                cellImageView.setTag(new Cell(i, j));
            }
        }
    }
}
