package com.example.tictactoe_petar_angelov;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tictactoe_petar_angelov.gameComponents.Board;
import com.example.tictactoe_petar_angelov.gameComponents.Bot;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    private TableLayout tableLayout;
    private TextView textView;
    private Board gameBoard;


    private String usernameText;

    public static int counter= 0;
    private boolean givingBotControl = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Intent intent = getIntent();
        usernameText = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        gameBoard();

        tableLayout.setBackgroundColor(Color.WHITE);

        tableLayout.getBackground().setAlpha(120);
        gameBoard=new Board();

        textView= findViewById(R.id.textView);
        textView.setTextColor(Color.WHITE);

        Button resetButton = findViewById(R.id.buttonPlay);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeAllViews();
                textView.setText("");
                gameBoard();
                gameBoard=new Board();
            }
        });

    }

        private void gameBoard(){

        tableLayout = findViewById(R.id.tableLayout);
        tableLayout.setStretchAllColumns(true);

        int counter = 1;
        for(int row=0;row<3;row++) {
            TableRow tableRow=new TableRow(this);
            for(int col=0;col<3;col++) {

                Button button=new Button(this);
                button.setTag(counter);
                button.setOnClickListener(this);

                tableRow.addView(button);
                counter++;
            }
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(-2, -2));
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        String you="X";
        String pc="O";
        String place="";

        if (!gameBoard.gameOverCHECK){

            if(gameBoard.getCurrentPlayer().equals("You")){
                place = you;
            }else if(gameBoard.getCurrentPlayer().equals("AI")){
                place = pc;
            }

            int choice = Integer.valueOf(v.getTag().toString());
            gameBoard.setOnPosition(choice); ((Button)v).setText(place);

            if(gameBoard.getGameResult() == 1){
                textView.setText("Result: You Won!");
                InsertInto("WON");
            }else if(gameBoard.getGameResult() == 2){
                textView.setText("Result: AI Won!");
                InsertInto("LOSE");
            }

            if (!givingBotControl){
                givingBotControl =true;
                int position= new Bot().findingPerfectPosition(gameBoard.copyGameBoard()).position;
                counter=0;
                if (position != 0){
                    (tableLayout.findViewWithTag(position)).callOnClick();
                }
                givingBotControl =false;
            }
        }
    }

    public void InsertInto(String score){
        try {
            ExecSQL("INSERT INTO GAME(Username, Score)" +
                            "VALUES(?, ?)",
                    new Object[]{
                            usernameText,
                            score
                    }
            );
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }






    public void ExecSQL(String SQL, Object[] args) throws SQLException {

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                getFilesDir().getPath()+"/GAME.db",
                null
        );
        if(args == null){
            db.execSQL(SQL);
        }else {
            db.execSQL(SQL, args);
        }
        db.close();
    }
}