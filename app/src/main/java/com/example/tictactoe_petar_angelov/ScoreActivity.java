package com.example.tictactoe_petar_angelov;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ScoreActivity extends AppCompatActivity implements View.OnClickListener{



    protected interface OnSelectElement{
        public void OnElementIterate(String Name, String Score, String ID);
    }

    private ListView list;
    private TextView textView;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        textView = findViewById(R.id.textScore);
        textView.setMovementMethod(new ScrollingMovementMethod());
        deleteButton = findViewById(R.id.buttonDelete);

        populateList();

        this.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    TruncateQuery();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                populateList();
            }
        });

    }

    private void populateList() {
        List<String> listResults = null;
        String textS = "";
        try {
         listResults = SelectQuery();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (String s:
             listResults) {
            textS += s;
        }
        textView.setText(textS);
        list=findViewById(R.id.simpleList);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonPlay:
                try {
                    TruncateQuery();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }



    public List<String> SelectQuery() throws Exception {
        final List<String> listRes = new ArrayList<>();
        listRes.add("ID              Username              Score"+"\n");
        listRes.add("______________________________________"+"\n");
        SelectSQL(
                "SELECT * FROM GAME;",
                null,
                new OnSelectElement() {
                    @Override
                    public void OnElementIterate(String Username, String Score, String ID) {
                        listRes.add(ID+".\t     |    "+Username+"\t     |    "+Score+"\n");
                    }
                }
        );

        return  listRes;
    }

    public List<String> TruncateQuery() throws Exception {
        final List<String> listRes = new ArrayList<>();
        ExecSQL(
                "DELETE FROM GAME;",
                null
        );

        return  listRes;
    }

    public void SelectSQL(String SQL, String[] args, OnSelectElement onSelectElement)
            throws Exception
    {
        SQLiteDatabase db=
                SQLiteDatabase.openOrCreateDatabase(
                        getFilesDir().getPath()+"/GAME.db",
                        null
                );

        Cursor cursor=db.rawQuery(SQL, args);
        while (cursor.moveToNext()){
            String ID=cursor.getString(cursor.getColumnIndex("ID"));
            String Username=cursor.getString(cursor.getColumnIndex("Username"));
            String Score=cursor.getString(cursor.getColumnIndex("Score"));
            onSelectElement.OnElementIterate(Username, Score, ID);
        }
        db.close();

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