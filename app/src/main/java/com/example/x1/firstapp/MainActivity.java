package com.example.x1.firstapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements View.OnClickListener{

    int hourz=0;
    int munitez=0;
    int secondz=0;
    int restTime=0;
    int allTime=0;
    Button btn_start;
    Timer timer = new Timer();


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start=(Button)findViewById(R.id.button);
        Button t0=(Button)findViewById(R.id.t0);
        t0.setOnClickListener(this);
        Button t15=(Button)findViewById(R.id.t15);
        t15.setOnClickListener(this);
        Button t30=(Button)findViewById(R.id.t30);
        t30.setOnClickListener(this);
        Button t45=(Button)findViewById(R.id.t45);
        t45.setOnClickListener(this);

    }


    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {

                    if(restTime/3600>0){
                        hourz=restTime/3600;
                        restTime=restTime-hourz*3600;
                    }else{
                        hourz=0;
                    }
                    if(restTime/60>0){
                        munitez=restTime/60;
                        restTime=restTime-munitez*60;
                    }else{
                        munitez=0;
                    }
                    if(restTime>0){
                        secondz=restTime;
                    }else{
                        secondz=0;
                    }
                    String strhour=""+hourz;
                    String strmunite=""+munitez;
                    String strsecond=""+secondz;
                    if(hourz>=0&&hourz<=9){
                        strhour="0"+hourz;
                    }
                    if(munitez>=0&&munitez<=9){
                        strmunite="0"+munitez;
                    }
                    if(secondz>=0&&secondz<=9){
                        strsecond="0"+secondz;
                    }
                    String timestr=strhour+":"+strmunite+":"+strsecond;
                    btn_start.setText(""+timestr);
                    if(allTime< 0){
                        timer.cancel();
                    }
                    allTime--;
                    restTime=allTime;
                }
            });
        }
    };
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View view = View.inflate(this, R.layout.timepicker, null);

        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);

        builder.setView(view);
        int hour=0;
        int minute=0;
        int second=0;
        int setminute=00;
        int sethour=00;
        Calendar calendar=Calendar.getInstance();
        hour=calendar.get(Calendar.HOUR);
        minute=calendar.get(Calendar.MINUTE);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(sethour);
        if(v.getId()==R.id.t0){
            timePicker.setCurrentMinute(00);
        }
        else if(v.getId()==R.id.t15){
            timePicker.setCurrentMinute(15);
        }else if(v.getId()==R.id.t30){
            timePicker.setCurrentMinute(30);
        }else if(v.getId()==R.id.t45){
            timePicker.setCurrentMinute(45);
        }



        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {




            @Override
            public void onClick(DialogInterface dialog, int which) {
                if((timePicker.getCurrentHour() == 0) && timePicker.getCurrentMinute() == 0){
                    Toast.makeText(MainActivity.this, "请输入正确的时间", Toast.LENGTH_SHORT).show();
                }else{
                    hourz=timePicker.getCurrentHour();
                    munitez=timePicker.getCurrentMinute();
                    restTime=hourz*3600+munitez*60+secondz;
                    allTime=restTime;
                    timer.schedule(task, 1000, 1000);       // timeTask
                }
            }

        });



        Dialog dialog = builder.create();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

        lp.alpha = 1f;

        dialog.getWindow().setAttributes(lp);

        dialog.show();
    }


}
