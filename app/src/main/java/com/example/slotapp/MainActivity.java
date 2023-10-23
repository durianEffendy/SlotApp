package com.example.slotapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView roller1, roller2, roller3, ivJackpot;
    private Thread t;
    private Handler handler;
    private int click = 0;
    private boolean hasWon = false;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btStartStop).setOnClickListener(this);
        this.handler = new Handler(Looper.getMainLooper());
        this.roller1 = (ImageView) findViewById(R.id.ivRoller1);
        this.roller2 = (ImageView) findViewById(R.id.ivRoller2);
        this.roller3 = (ImageView) findViewById(R.id.ivRoller3);
        this.ivJackpot = (ImageView) findViewById(R.id.ivJackpot);

        createThread();

        }

    private void createThread() {
        int[] images  = {R.drawable.slot_1, R.drawable.slot_2, R.drawable.slot_3, R.drawable.slot_4, R.drawable.slot_5 , R.drawable.slot_6, R.drawable.slot_7, R.drawable.slot_8, R.drawable.slot_9};
        this.t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    int[] rollerImage = new int[3];

                    while (true){
                        if(click==1){
                            rollerImage[0] =(int)(Math.random() * images.length);
                            rollerImage[1] =(int)(Math.random() * images.length);
                            rollerImage[2] =(int)(Math.random() * images.length);
                        }else if (click ==2){
                            rollerImage[1] =(int)(Math.random() * images.length);
                            rollerImage[2] =(int)(Math.random() * images.length);
                        }else if (click == 3){
                            rollerImage[2] =(int)(Math.random() * images.length);


                        }

                        final int[] passImage = rollerImage;

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                roller1.setImageResource(images[passImage[0]]);
                                roller2.setImageResource(images[passImage[1]]);
                                roller3.setImageResource(images[passImage[2]]);

                                if (rollerImage[0] == rollerImage[1] && rollerImage[1] == rollerImage[2]){
                                    hasWon = true;
                                }
                            }


                        });
                        Thread.sleep(500);
                    }
                }catch (Exception e){

                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (click==3) {
            click = 0;
            t.interrupt();
        }else if(click==0){
            this.createThread();
            this.t.start();
            click++;
        }else{
            click++;
        }

        if (hasWon){
            ivJackpot.setVisibility(View.VISIBLE);
            mediaPlayer = MediaPlayer.create(this, R.raw.jackpotfx);
            mediaPlayer.start();
            hasWon = false;
        }else{
            ivJackpot.setVisibility(View.INVISIBLE);
        }
    }
}