package com.codes.mychess;

import static com.codes.mychess.Storage.*;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class NameActivity extends AppCompatActivity {

    ActionBar aB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_names);

        aB = getSupportActionBar();

        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }

        EditText player1 = findViewById(R.id.player_1_name);
        player1.setText(playerWhite);
        EditText player2 = findViewById(R.id.player_2_name);
        player2.setText(playerBlack);
    }

    public void done (View view)
    {
        EditText player1 = findViewById(R.id.player_1_name);
        playerWhite = player1.getText().toString().trim();
        EditText player2 = findViewById(R.id.player_2_name);
        playerBlack = player2.getText().toString().trim();

        Intent intent = new Intent(NameActivity.this, MainActivity.class);

        NameActivity.this.startActivity(intent);
    }

    public void buttonClickNameGenerator (View view)
    {
        Uri uri = Uri.parse("https://www.namegeneratorfun.com/nickname");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
}
