package com.example.cs125project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

//import java.util.Timer;
//import java.util.TimerTask;


public class TimerModeLogic extends View {
    private int numColumns, numRows;
    private int cellWidth, cellHeight;
    private Paint gridPaint = new Paint();
    private Paint xPaint = new Paint();
    private Paint circlePaint = new Paint();
    private int[][] board;
    private int turnCount = 0;
    private int xCapture = 1;
    private int circleCapture = 2;
    private Context context;
    private int secondsPassed = 0;

    CountDownTimer timer = new CountDownTimer(1500, 1000) {
        public void onTick(long untilFinished) {
        }
        public void onFinish() {
            String winner;
            if (turnCount % 2 == 0) {
                winner = "Circle";
            } else {
                winner = "X";
            }
            new AlertDialog.Builder(context)
                    .setTitle("Game Over")
                    .setMessage(winner + " wins!")
                    .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            Intent intent = new Intent(getContext(), TimerMode.class);
                            context.startActivity(intent);
                        }
                    })
                    .setNeutralButton("Change Mode", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), GameModeChoice.class);
                            context.startActivity(intent);
                        }
                    })
                    .setNegativeButton("Quit to Home", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                            homeIntent.addCategory( Intent.CATEGORY_HOME );
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            context.startActivity(homeIntent);
                        }
                    })
                    .show();
        }
    };


    public TimerModeLogic(Context context) {
        this(context, null);
    }

    public TimerModeLogic(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        gridPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        gridPaint.setStrokeWidth(15);
        xPaint.setStyle(Paint.Style.FILL_AND_STROKE); //can let user request a color
        xPaint.setColor(Color.BLUE);
        xPaint.setStrokeWidth(10);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.RED);
        circlePaint.setStrokeWidth(10);
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
        calculateDimensions();
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
        calculateDimensions();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        calculateDimensions();
    }

    private void calculateDimensions() {
        if (numColumns < 1 || numRows < 1) {
            return;
        }

        cellWidth = getWidth() / numColumns;
        cellHeight = getHeight() / numRows;

        board = new int[numColumns][numRows];

        invalidate();
    }

    /**
     * @param canvas the screen to draw on
     * draws the grid as well as X or O in response to onTouchEvent method
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        if (numColumns == 0 || numRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < numColumns; i++) {
            for (int j = 0; j < numRows; j++) {
                if (board[i][j] == xCapture) {
                    canvas.drawLine(i * cellWidth, j * cellHeight,
                            (i + 1) * cellWidth, (j + 1) * cellHeight,
                            xPaint);
                    canvas.drawLine((i + 1) * cellWidth, (j) * cellHeight,
                            i * cellWidth, (j + 1) * cellHeight, xPaint);

                } else if (board[i][j] == circleCapture) {
                    RectF rectF = new RectF(i * cellWidth, j * cellHeight,
                            (i + 1) * cellWidth, (j + 1) * cellHeight);
                    canvas.drawOval(rectF, circlePaint);

                }
            }
        }
        for (int i = 1; i < numColumns; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, gridPaint);
        }
        for (int i = 1; i < numRows; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, gridPaint);
        }
        if (turnCount == 0) { //preliminary alert dialog
            new AlertDialog.Builder(context)
                    .setMessage("X goes first!")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                            dialog.cancel();
                        }
                    })
                    .show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int)(event.getX() / cellWidth);
            int row = (int)(event.getY() / cellHeight);
            if (turnCount % 2 != 0 && board[column][row] == 0) {
                board[column][row] = circleCapture;
                turnCount++;
                timer.cancel();
            } else if (turnCount % 2 == 0 && board[column][row] == 0){
                board[column][row] = xCapture;
                timer.cancel();
                turnCount++;
            }
            invalidate();
            // add timer to end game, depending on turncount
            if (checkWinner()) { //there has been a winner
                String winner;
                if (turnCount % 2 == 0) {
                    winner = "Circle";
                } else {
                    winner = "X";
                }
                new AlertDialog.Builder(context)
                        .setTitle("Game Over")
                        .setMessage(winner + " wins!")
                        .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                Intent intent = new Intent(getContext(), TimerMode.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNeutralButton("Change Mode", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getContext(), GameModeChoice.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("Quit to Home", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                homeIntent.addCategory( Intent.CATEGORY_HOME );
                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(homeIntent);
                            }
                        })
                        .show();
            } else if (turnCount == 9 ) { //The game is tied
                new AlertDialog.Builder(context)
                        .setTitle("Game Over")
                        .setMessage("A tie!")
                        .setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                Intent intent = new Intent(getContext(), TimerMode.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNeutralButton("Change Mode", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(getContext(), GameModeChoice.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("Quit to Home", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                                homeIntent.addCategory( Intent.CATEGORY_HOME );
                                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(homeIntent);
                            }
                        })
                        .show();
            } else {
                timer.start();
            }
        }
        return true;
    }
    public boolean checkWinner() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1]
                    && board[i][1] == board[i][2]
                    && board[i][0] != 0) {
                return true;
            } else if (board[0][i] == board[1][i]
                    && board[1][i] == board[2][i]
                    && board[0][i] != 0){
                return true;
            } else if (board[0][0] == board[1][1]
                    && board[2][2] == board[1][1]
                    && board[0][0] != 0) {
                return true;
            } else if (board[0][2] == board[1][1]
                    && board[1][1] == board[2][0]
                    && board[0][2] != 0) {
                return true;
            }
        }
        return false;
    }
}
