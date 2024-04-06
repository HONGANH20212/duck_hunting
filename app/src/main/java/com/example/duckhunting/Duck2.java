package com.example.duckhunting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Duck2 extends Duck1{
    Bitmap duck[] = new Bitmap[8];
    public Duck2(Context context) {
        super(context);
        duck[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.duck2_00);
        duck[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.duck2_01);
        duck[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.duck2_02);
        duck[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.duck2_03);
        duck[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.duck2_04);
        duck[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.duck2_05);
        duck[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.duck2_06);
        duck[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.duck2_07);
        resetPosition();
    }

    @Override
    public Bitmap getBitmap() {
        return duck[duckFame];
    }

    @Override
    public int getWidth() {
        return duck[0].getWidth();
    }

    @Override
    public int getHeight() {
        return duck[0].getHeight();
    }

    @Override
    public void resetPosition() {
        duckX = GameView.dWidth + random.nextInt(1500);
        duckY = random.nextInt(400);
        velocity = 16 + random.nextInt(19);
    }
}
