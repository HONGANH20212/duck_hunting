package com.example.duckhunting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Duck1 {
    Bitmap duck[] = new Bitmap[8];
    int duckX, duckY, velocity, duckFame;

    Random random;
    public Duck1(Context context){
        duck[0] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck0);
        duck[1] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck1);
        duck[2] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck2);
        duck[3] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck3);
        duck[4] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck4);
        duck[5] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck5);
        duck[6] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck6);
        duck[7] = BitmapFactory.decodeResource(context.getResources(),R.drawable.duck7);
        random = new Random();
        duckFame = 0;
        resetPosition();
    }
    public Bitmap getBitmap(){
        return duck[duckFame];
    }
    public int getWidth(){
        return duck[0].getWidth();
    }
    public int getHeight(){
        return duck[0].getHeight();
    }
    public void resetPosition(){
        duckX = GameView.dWidth + random.nextInt(1200);
        duckY = random.nextInt(300);
        velocity = 14 + random.nextInt(17);

    }
}