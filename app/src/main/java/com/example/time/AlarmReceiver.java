package com.example.time;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    String state = intent.getExtras().getString("extra");
    String sound = intent.getExtras().getString("sound_id");
    Intent serviceIntent = new Intent(context, AlarmAlert.class);
    serviceIntent.putExtra("extra", state);
    serviceIntent.putExtra("sound_id", sound);
    context.startService(serviceIntent);
  }
}


