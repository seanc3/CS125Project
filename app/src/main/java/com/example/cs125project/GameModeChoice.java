package com.example.cs125project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class GameModeChoice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode_choice2);
        Button singlePlayer = findViewById(R.id.singlePlayer);
        Button doublePlayer = findViewById(R.id.doublePlayer);
//        singlePlayer.setOnClickListener();
    }
}
