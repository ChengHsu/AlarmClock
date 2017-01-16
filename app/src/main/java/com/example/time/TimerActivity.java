package com.example.time;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends AppCompatActivity {
  Button start, stop, restart,back;
  TextView timer;
  Handler handler;
  int  sec, min, ms;
  boolean isStart;
  boolean isStop;
  Thread thread;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.timer);
    initView();
    initListener();
    initHnadler();
  }

  private void initHnadler() {
//    用Handler动态更新UI
    handler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        super.handleMessage(msg);
        //开始
        if (msg.what == 0) {
            addTimer();
          //暂停
        }else if(msg.what==1){
         timer.setText("00:00:00");
        }
      }
    };
//    用线程实现计时
    thread =  new Thread(){
      @Override
      synchronized public void run() {
        while(true){
          while(isStart){
           handler.sendEmptyMessage(0);
            try {
              sleep(10);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
          try {
            sleep(100);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    };
    thread .start();
  }

  private synchronized  void addTimer(){
//    如果毫秒已经到达99，则进1
    if (ms == 99) {
//      如果秒已经到达59，则进1
      if (sec == 59) {
              min++;
              sec = 0;
              ms = 0;
      } else {
        sec++;
        ms = 0;
      }
    } else {
      ms++;
    }
    timer.setText(formatTime(min,sec,ms));
  }
//  将时间转为00：00：00格式
private String formatTime(int min,int sec,int ms){
  String s = "";
  if(min<10){
    s = "0"+min;
  }else{
    s+=min;
  }
  s+=":";
  if(sec<10){
    s += "0"+sec;
  }else{
    s+=sec;
  }
  s+=":";
  if(ms<10){
    s += "0"+ms;
  }else{
    s+=ms;
  }
  Log.i("123",s);
  return s;
}
//  时间显示框清0
  private  void clear(){
    min = 0;
    sec = 0;
    ms = 0;
    timer.setText("00:00:00");
  }
//  初始化
  private void initView() {
    start = (Button) findViewById(R.id.bt_start);
    stop = (Button) findViewById(R.id.bt_stop);
    restart = (Button) findViewById(R.id.bt_restart);
    back = (Button) findViewById(R.id.bt_back);
    timer = (TextView) findViewById(R.id.timer);
    clear();
  }

  private void initListener() {
//    开始计时功能
    start.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        restart.setEnabled(false);
        if(isStart!=true) {
          clear();
        }
        isStart = true;
      }
    });
//    停止计时功能
    stop.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        start.setEnabled(false);
        restart.setEnabled(true);
        if (stop.getTag() == null) {
//          non-timing
           isStart = false;
          stop.setText("恢复计时");
          stop.setTag(stop);
        } else
        {
//          timing
          isStart = true;
          isStop = false;
          stop.setText("暂停计时");
          stop.setTag(null);
        }
      }
    });
//    重新计时功能
    restart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        start.setEnabled(true);
        isStart = false;
        stop.setText("暂停计时");
        stop.setTag(null);
        clear();
      }
    });
    back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        TimerActivity.this.finish();
      }
    });
  }
}
