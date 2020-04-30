package com.example.cs125project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AIGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AIGameLogic current = new AIGameLogic(this);
        current.setNumColumns(3);
        current.setNumRows(3);
        setContentView(current);
    }
}
