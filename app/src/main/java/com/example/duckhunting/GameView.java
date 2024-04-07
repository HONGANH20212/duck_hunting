package com.example.duckhunting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class GameView extends View {
    Bitmap background;
    Rect rect;
    static int dWidth, dHeight;
    Handler handler;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            invalidate();
        }
    };

    final long UPDATE_MILLIS = 30;

    List<WhiteDuck> whiteDucks;
    List<BrownDuck> brownDucks;

    //ball
    Bitmap ball, target;
    float ballX, ballY;
    float sX, sY;
    float fX, fY;
    float dX, dY;
    float tempX, tempY;
    Paint borderPaint;
    int score = 0;
    int life = 10;
    Context context;
    MediaPlayer duck_sound, ball_shoot;
    boolean gameState = true;

    public GameView(Context context) {
        super(context);
        System.out.println("call on game view function");
        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        Display display = ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        dWidth = size.x;
        dHeight = size.y;
        rect = new Rect(0, 0, dWidth, dHeight);
        handler = new Handler();

        whiteDucks = Arrays.asList(new WhiteDuck(context), new WhiteDuck(context));
        brownDucks = Arrays.asList(new BrownDuck(context), new BrownDuck(context));

        //icon ball
        ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        target = BitmapFactory.decodeResource(getResources(), R.drawable.target);
        ballX = ballY = 0;
        sX=sY=fX=fY=0;
        dX=dY=0;
        tempX=tempY=0;
        borderPaint = new Paint();
        borderPaint.setColor(Color.RED);
        borderPaint.setStrokeWidth(5);
        this.context = context;
        duck_sound = MediaPlayer.create(context, R.raw.duck_1_sound);
        ball_shoot = MediaPlayer.create(context, R.raw.ball_shoot);
    }

    @SuppressLint("SuspiciousIndentation")
    protected void onDraw(@NonNull Canvas canvas) {
        System.out.println("call on draw action");
        super.onDraw(canvas);
        //activity life
        if (life < 1){
            gameState = false;
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("score", score);
            context.startActivity(intent);
            ((Activity)context).finish();
        }

        canvas.drawBitmap(background, null, rect, null);

        for (int i = 0; i < whiteDucks.size(); i++) {

            final WhiteDuck whiteDuck = whiteDucks.get(i);
            canvas.drawBitmap(whiteDuck.getBitmap(), whiteDuck.duckX, whiteDuck.duckY, null);
            whiteDuck.computeDuckFrame();
            whiteDuck.duckX -= whiteDuck.velocity;

            if (whiteDuck.duckX < -whiteDuck.getWidth()) {
                whiteDuck.resetPosition();
                life--;
                if(duck_sound != null) duck_sound.start();
            }

            //duck2
            final BrownDuck brownDuck = brownDucks.get(i);
            canvas.drawBitmap(brownDuck.getBitmap(), brownDuck.duckX, brownDuck.duckY, null);
            brownDuck.computeDuckFrame();
            brownDuck.duckX -= brownDuck.velocity;

            if (brownDuck.duckX < -brownDuck.getWidth()) {
                brownDuck.resetPosition();
                life--;
                if(duck_sound != null) duck_sound.start();
            }

            // bắn trúng mục tiêu duck1
            final int whiteDuckHE = whiteDuck.duckX + whiteDuck.getWidth();
            final int whiteDuckVE = whiteDuck.duckY + whiteDuck.getHeight();
            final float ballXEnd = ballX + ball.getWidth();
            final float ballYEnd = ballY + ball.getHeight();

            if (ballX <= whiteDuckHE && ballXEnd >= whiteDuck.duckX && ballY <= whiteDuckVE && ballYEnd >= whiteDuck.duckY){
                whiteDuck.resetPosition();
                score++;
                if (ball_shoot != null) ball_shoot.start();
            }

            // bắn trúng mục tiêu duck2
            final int brownDuckHE = brownDuck.duckX + brownDuck.getWidth();
            final int brownDuckVE = brownDuck.duckY + brownDuck.getHeight();
            if (ballX <= brownDuckHE && ballXEnd >= brownDuck.duckX && ballY <= brownDuckVE && ballYEnd >= brownDuck.duckY){
                brownDuck.resetPosition();
                score++;
                if (ball_shoot != null) ball_shoot.start();
            }

        }

        //ball
        if(sX > 0 && sY > dHeight * .75f){
            canvas.drawBitmap(target, sX - (float) target.getWidth() /2, sY - (float) target.getHeight() /2, null);
        }
        if((Math.abs(fX - sX) > 0 || Math.abs(fY - sY) > 0) && fY >0 && fY > dHeight * .75f){
            canvas.drawBitmap(target, fX - (float) target.getWidth() /2, fY - (float) target.getHeight() /2, null);
        }
        if ((Math.abs(dX) > 10 || Math.abs(dY) > 10) && sY > dHeight * .75f && fY > dHeight * .75f){
            ballX = fX - (float) ball.getWidth() /2 - tempX;
            ballY = fY - (float) ball.getHeight() /2 - tempY;
            canvas.drawBitmap(ball, ballX, ballY, null);
            tempX += dX;
            tempY += dY;
        }
        //ball
        canvas.drawLine(0, dHeight * .75f, dWidth, dHeight * .75f, borderPaint);
        if(gameState)
            handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    //action ball
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("call on touch event");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                dX = dY = fX = fY = tempX = tempY = 0;
                sX = event.getX();
                sY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                fX = event.getX();
                fY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                fX = event.getX();
                fY = event.getY();
                ballX = event.getX();
                ballY = event.getY();
                dX = fX - sX;
                dY = fY - sY;
                break;
        }
        return true;
    }
}