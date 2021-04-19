package com.example.havastest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.havastest.model.DateDataClass;
import com.example.havastest.service.DateAndTimeService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private  DateAndTimeService dateAndTimeService;
    private  TextView showDate;
    private  FrameLayout frameLayout;
        private int xAxis;
         private int yAxis;
    private  ViewGroup  viewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showDate=(TextView) findViewById(R.id.displaydate);
        frameLayout = (FrameLayout) findViewById(R.id.Square_frame);
        viewGroup = (RelativeLayout) findViewById(R.id.main);
        dateAndTimeService= Util.getService();
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotation);

        frameLayout.startAnimation(animation);

        final long timeInterval = 1000;
        Runnable runnable = new Runnable() {
            public void run() {
                while (true) {
                    getLiveDate();
                    try {
                        Thread.sleep(timeInterval);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        frameLayout.setOnTouchListener(onDragFrame());
    }


    private void getLiveDate() {
        dateAndTimeService.getDate().enqueue(new Callback<DateDataClass>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<DateDataClass> call, Response<DateDataClass> response) {
                    if(response!=null) {
                        String liveDate = "";
                        try {
                            String s = response.body().getDate();
                            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(s);
                            liveDate = new SimpleDateFormat("HH:mm:ss").format(date);
                            Log.d("TAG", "onResponse: " + liveDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        showDate.setText(liveDate);
                    }
                    else{
                        showDate.setText("Something wrong");
                        Log.d("Date NOt Received","");
                    }
            }

            @Override
            public void onFailure(Call<DateDataClass> call, Throwable t) {
                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }


    private View.OnTouchListener onDragFrame() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int x = (int) event.getRawX();
                final int y = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                               v.getLayoutParams();
                            xAxis = x - params.leftMargin;
                            yAxis = y - params.topMargin;
                            break;
                    case MotionEvent.ACTION_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams)
                           v.getLayoutParams();
                        params1.leftMargin = x-xAxis;
                        params1.topMargin = y-yAxis;
                        params1.rightMargin = 0;
                        params1.bottomMargin = 0;
                        v.setLayoutParams(params1);
                        break;
                }

                viewGroup.invalidate();
                return true;
            };
        };

    }

}