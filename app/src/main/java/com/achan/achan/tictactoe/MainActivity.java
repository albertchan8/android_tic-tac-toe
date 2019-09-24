package com.achan.achan.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Tic-Tac-Toe Created by Albert Chan
 */

public class MainActivity extends AppCompatActivity {
    private static final String PLAYER_1_MARKER = "X";
    private static final String PLAYER_2_MARKER = "O";
    private Button buttons[];
    private ArrayList<Integer> markedPosition;
    private boolean isPlayer1;
    private boolean winner1;
    private boolean winner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showIntroPrompt();

        isPlayer1 = true;

        buttons = new Button[9];
        buttons[0] = (Button) findViewById(R.id.button1);
        buttons[1] = (Button) findViewById(R.id.button2);
        buttons[2] = (Button) findViewById(R.id.button3);
        buttons[3] = (Button) findViewById(R.id.button4);
        buttons[4] = (Button) findViewById(R.id.button5);
        buttons[5] = (Button) findViewById(R.id.button6);
        buttons[6] = (Button) findViewById(R.id.button7);
        buttons[7] = (Button) findViewById(R.id.button8);
        buttons[8] = (Button) findViewById(R.id.button9);

        markedPosition = new ArrayList<Integer>();

        for (int i = 0; i < buttons.length; i++) {
            markedPosition.add(i);
            final int position = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonClickLogic(position);
                }
            });
        }
    }

    public void buttonClickLogic(int position) {
        Button button = buttons[position];
        if (button.getText().toString().equals("")) {
            if (isPlayer1) {
                button.setText(PLAYER_1_MARKER);

                // Check if player 1 wins.
                shouldDisplayWinner(0, 3, 6, PLAYER_1_MARKER);
                shouldDisplayWinner(1, 4, 7, PLAYER_1_MARKER);
                shouldDisplayWinner(2, 5, 8, PLAYER_1_MARKER);
                shouldDisplayWinner(0, 1, 2, PLAYER_1_MARKER);
                shouldDisplayWinner(3, 4, 5, PLAYER_1_MARKER);
                shouldDisplayWinner(6, 7, 8, PLAYER_1_MARKER);
                shouldDisplayWinner(0, 4, 8, PLAYER_1_MARKER);
                shouldDisplayWinner(2, 4, 6, PLAYER_1_MARKER);
            } else {
                button.setText(PLAYER_2_MARKER);

                // Check if player 2 wins.
                shouldDisplayWinner(0, 3, 6, PLAYER_2_MARKER);
                shouldDisplayWinner(1, 4, 7, PLAYER_2_MARKER);
                shouldDisplayWinner(2, 5, 8, PLAYER_2_MARKER);
                shouldDisplayWinner(0, 1, 2, PLAYER_2_MARKER);
                shouldDisplayWinner(3, 4, 5, PLAYER_2_MARKER);
                shouldDisplayWinner(6, 7, 8, PLAYER_2_MARKER);
                shouldDisplayWinner(0, 4, 8, PLAYER_2_MARKER);
                shouldDisplayWinner(2, 4, 6, PLAYER_2_MARKER);
            }

            markedPosition.remove(new Integer(position));

            // If there is a winner, show winning prompt.
            if (winner1 == true && winner2 == false) {
                showWinnerPrompt("Player 1");
            } else if (winner2 == true && winner1 == false) {
                showWinnerPrompt("Player 2");
            } else if (markedPosition.isEmpty()) {
                showDrawPrompt();
            } else {

                // Switch to the other player's turn.
                isPlayer1 = !isPlayer1;

                if (isPlayer1 == false) {
                    computerLogic();
                }
            }
        }
    }

    public void shouldDisplayWinner(int position1, int position2, int position3, String playerShape) {
        if (buttons[position1].getText().toString().equals(playerShape) && buttons[position2].getText().toString().equals(playerShape) && buttons[position3].getText().toString().equals(playerShape)) {
            Log.d("shouldDisplayWinner", "winningPositions: " + position1 + " " + position2 + " " + position3 + " " + playerShape);
            if (playerShape == PLAYER_1_MARKER) {
                winner1 = true;
            } else if (playerShape == PLAYER_2_MARKER) {
                winner2 = true;
            }
        }
    }

    public void resetGame() {
        markedPosition.clear();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText("");
            markedPosition.add(i);
        }
        isPlayer1 = true;
        winner1 = false;
        winner2 = false;
    }

    public void computerLogic() {
        int randomIndex = (int) (Math.random() * markedPosition.size());
        if (buttons[markedPosition.get(randomIndex)].getText().toString().equals("")) {
            buttonClickLogic(markedPosition.get(randomIndex));
        }
    }

    public void showWinnerPrompt(String playerName) {
        Log.d("showWinnerPrompt", "popup");
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("GAME OVER")
                .setMessage(playerName + " is the WINNER!")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // okay button
                        resetGame();
                    }
                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // cancel button
//                    }
//                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void showDrawPrompt() {
        Log.d("showWinnerPrompt", "popup");
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("GAME OVER")
                .setMessage("Game is a DRAW")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // okay button
                        resetGame();
                    }
                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // cancel button
//                    }
//                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void showIntroPrompt() {
        new AlertDialog.Builder(this)
                .setTitle("Tic-Tac-Toe!")
                .setMessage("Line up 3 in a Row to WIN!")

                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
//                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        // do nothing
//                    }
//                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();
    }
}
