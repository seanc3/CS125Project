package com.example.cs125project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TwoPersonGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GridDrawer pixelGrid = new GridDrawer(this);
        pixelGrid.setNumColumns(3);
        pixelGrid.setNumRows(3);
        setContentView(pixelGrid);
        //use MotionEvent // onTouchEvent()
        //https://stackoverflow.com/questions/24842550/2d-array-grid-on-drawing-canvas
    }
}
