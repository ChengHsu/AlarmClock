package com.example.time;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import java.util.Random;

public class AlarmAlert extends Service {
    private boolean isRunning;
    private Context context;
    MediaPlayer mMediaPlayer;
    private int startId;

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.e("MyActivity", "In the Richard service");
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
//        通知功能
        final NotificationManager mNM = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(this.getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent1, 0);
        Notification mNotify  = new Notification.Builder(this)
                .setContentTitle("Your alarm is beeping" + "!")
                .setContentText("Click here!")
                .setSmallIcon(R.drawable.ic_action_call)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .build();
        String state = intent.getExtras().getString("extra");
//       将传进来的参数转换为startID
        assert state != null;
        switch (state) {
            case "NO":
                startId = 0;
                break;
            case "YES":
                startId = 1;
                break;
            default:
                startId = 0;
                break;
        }
//      响铃功能
        String sound_id = intent.getExtras().getString("sound_id");
        Log.e("Service: sound id is " , sound_id);
        if(!this.isRunning && startId == 1) {
            Log.e("if there was not sound ", " and you want start");

            assert sound_id != null;
//            随机播放铃声
            if (sound_id.equals("0")) {
                int min = 1;
                int max = 9;
                Random r = new Random();
                int random_number = r.nextInt(max - min + 1) + min;
                Log.e("random number is ", String.valueOf(random_number));

                if (random_number == 1) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.sound1);
                }
                else if (random_number == 2) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.sound2);
                }
                else if (random_number == 3) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.sound3);
                }
                else if (random_number == 4) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.sound4);
                }
                else if (random_number == 5) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.sound5);
                }
                else if (random_number == 6) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.sound6);
                }
                else if (random_number == 7) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.sound7);
                }
                else if (random_number == 8) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.sound8);
                }
                else if (random_number == 9) {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.sound9);
                }
                else {
                    mMediaPlayer = MediaPlayer.create(this, R.raw.sound1);
                }
            }
            else if (sound_id.equals("1")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound1);
            }
            else if (sound_id.equals("2")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound2);
            }
            else if (sound_id.equals("3")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound3);
            }
            else if (sound_id.equals("4")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound4);
            }
            else if (sound_id.equals("5")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound5);
            }
            else if (sound_id.equals("6")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound6);
            }
            else if (sound_id.equals("7")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound7);
            }
            else if (sound_id.equals("8")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound8);
            }
            else if (sound_id.equals("9")) {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound9);
            }
            else {
                mMediaPlayer = MediaPlayer.create(this, R.raw.sound1);
            }
            mMediaPlayer.start();
            mNM.notify(0, mNotify);
            this.isRunning = true;
            this.startId = 0;
        }
        else if (!this.isRunning && startId == 0){
            this.isRunning = false;
            this.startId = 0;
        }
        else if (this.isRunning && startId == 1){
            this.isRunning = true;
            this.startId = 0;
        }
        else {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            this.isRunning = false;
            this.startId = 0;
        }
        Log.e("MyActivity", "In the service");
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        Log.e("JSLog", "on destroy called");
        super.onDestroy();

        this.isRunning = false;
    }
}

