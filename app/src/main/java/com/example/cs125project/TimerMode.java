package com.example.cs125project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TimerMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimerModeLogic current = new TimerModeLogic(this);
        current.setNumColumns(3);
        current.setNumRows(3);
        setContentView(current);
    }
}
