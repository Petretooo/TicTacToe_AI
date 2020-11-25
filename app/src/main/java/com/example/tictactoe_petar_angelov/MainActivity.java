package com.example.tictactoe_petar_angelov;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    EditText usernameText;
    Button buttonPlay;
    Button buttonScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.buttonPlay = findViewById(R.id.buttonPlay);
        this.buttonScore = findViewById(R.id.buttonScore);
        this.usernameText = findViewById(R.id.usernameText);
        this.buttonPlay.setOnClickListener(this);
        this.buttonScore.setOnClickListener(this);

        try {
            initDb();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonPlay:
                if(!usernameText.getText().toString().trim().isEmpty()){
                    playGame(view);
                }
                break;
            case R.id.buttonScore:
                viewScore(view);
                break;
            default:
                break;
        }
    }


    public void playGame(View view){
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        intent.putExtra(EXTRA_MESSAGE, usernameText.getText().toString());
        startActivity(intent);
    }

    public void viewScore(View view){
        Intent intent = new Intent(MainActivity.this, ScoreActivity.class);
        startActivity(intent);
    }


    public void initDb() throws SQLException {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(
                getFilesDir().getPath()+"/GAME.db",null
        );
        String createQuery = "CREATE TABLE if not exists GAME( " +
                "ID integer PRIMARY KEY AUTOINCREMENT, " +
                "Username text not null, " +
                "Score text" +
                " );";
        db.execSQL(createQuery);
        db.close();
    }

}