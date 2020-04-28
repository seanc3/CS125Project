package com.example.cs125project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button newGame = findViewById(R.id.newGameButton);
        findViewById(R.id.newGameButton).setOnClickListener(unused -> startActivity(
                new Intent(this, GameModeChoice.class)));
        Button noPlay = findViewById(R.id.noDontPlay);
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        noPlay.setOnClickListener(unused -> startActivity(homeIntent));
    }
}
