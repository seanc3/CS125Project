package com.example.cs125project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TwoPersonGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwoPlayerGameLogic current = new TwoPlayerGameLogic(this);
        current.setNumColumns(3);
        current.setNumRows(3);
        setContentView(current);
    }
}
