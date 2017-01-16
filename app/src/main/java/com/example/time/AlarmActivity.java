package com.example.time;
import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class AlarmActivity extends AppCompatActivity {
  private Button bt_set,bt_canel,bt_back;
  private TextView tv_alarm;
  Calendar mCalendar = Calendar.getInstance();
  int sound = 0;
  Context context;
  AlarmManager mAlarmManager;
  PendingIntent pi;
  private static AlarmActivity inst;
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.alarm);
    this.context = this;
    mAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    bt_set = (Button) findViewById(R.id.bt_set);
    bt_canel = (Button) findViewById(R.id.bt_canel);
    bt_back = (Button) findViewById(R.id.bt_back);
    tv_alarm = (TextView) findViewById(R.id.show_alarm);
    setListener();
  }

  public void setListener() {
    final Intent myIntent = new Intent(this.context, AlarmReceiver.class);
//    设计闹钟能
    bt_set.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = mCalendar.get(Calendar.MINUTE);
        new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                  public void onTimeSet(TimePicker view,
                                        int hourOfDay, int minute) {
//                   设置时间
                    mCalendar.setTimeInMillis(System
                            .currentTimeMillis());
                    mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    mCalendar.set(Calendar.MINUTE, minute);
                    mCalendar.set(Calendar.SECOND, 0);
                    mCalendar.set(Calendar.MILLISECOND, 0);
//                    当前时间
                    Calendar currentTime = Calendar.getInstance();
//                     如果设置的闹钟时间比当前时间还小，往前加一天
                    if(mCalendar.getTimeInMillis() <= currentTime.getTimeInMillis()){
                      mCalendar.setTimeInMillis(mCalendar.getTimeInMillis() + 24*60*60*1000);
                    }
//                    大于等于10的直接显示，小于10的前面加“0”显示
                    if(hourOfDay> 9){
                      tv_alarm.setText(hourOfDay+"");
                    }else{
                      tv_alarm.setText("0"+hourOfDay);

                    }
                    tv_alarm.append(":");
                    if(minute> 9){
                      tv_alarm.append(minute+"");
                    }else{
                      tv_alarm.append("0"+minute);

                    }
//                    传参并发送广播
                    myIntent.putExtra("extra", "YES");
                    myIntent.putExtra("sound_id", String.valueOf(sound));
                    pi = PendingIntent.getBroadcast(AlarmActivity.this, 0, myIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    mAlarmManager.set(AlarmManager.RTC_WAKEUP,mCalendar.getTimeInMillis(), pi);
                  }
                }, mHour, mMinute, true).show();
      }
    });
//    取消闹钟功能
    bt_canel.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        myIntent.putExtra("extra", "NO");
        myIntent.putExtra("sound_id", String.valueOf(sound));

        sendBroadcast(myIntent);
        mAlarmManager.cancel(pi);
        Toast.makeText(AlarmActivity.this,"已经取消闹钟",Toast.LENGTH_LONG).show();
        tv_alarm.setText("00:00");
      }
    });
//    返回功能
    bt_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        AlarmActivity.this.finish();
      }
    });
  }

  @Override
  public void onStart() {
    super.onStart();
    inst = this;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.e("MyActivity", "on Destroy");
  }
}
