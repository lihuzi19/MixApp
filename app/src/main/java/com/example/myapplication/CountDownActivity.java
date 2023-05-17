package com.example.myapplication;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lihuzi.mixapp.R;

public class CountDownActivity extends AppCompatActivity {


    private static final String TAG = "SensorTest";

    private TextView _timeTv;
    private TextView _statusTv;

    private volatile int _status; //0 暂停计时 1 计时中
    private SensorManager _sensorManager;
    private Thread _workThread;
    private long _startTimeMillis;
    private long _endTimeMillis;
    private Handler _handler;
    private StringBuilder _sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_count_down);
        initData();
        initView();
    }

    private void initData() {
        _sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        _handler = new Handler();
        _sb = new StringBuilder();
    }

    private void initView() {
        _timeTv = findViewById(R.id.act_count_down_time_tv);
        _statusTv = findViewById(R.id.act_count_down_status_tv);

        _statusTv.setOnClickListener(_statusClickListener);
    }

    private void startCount() {
        if (_workThread == null) {
            _workThread = new Thread(_workRunnable);
            _workThread.start();
        }
    }

    private Runnable _workRunnable = new Runnable() {
        @Override
        public void run() {
            _startTimeMillis = System.currentTimeMillis();
            while (_status == 1) {
                try {
                    _endTimeMillis = System.currentTimeMillis();
                    _handler.post(_refershTime);
                    Thread.sleep(10);
                    _workThread = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private Runnable _refershTime = new Runnable() {
        @Override
        public void run() {
            System.out.println("refer");
            _sb.replace(0, _sb.length(), "00:00:00:00");
            long time = _endTimeMillis - _startTimeMillis;
            if ((time = time / 10) > 0) {
                long milli = time % 100;
                _sb.replace(9, 11, milli < 10 ? "0" + milli : milli + "");
                if ((time = time / 100) > 0) {
                    long second = time % 60;
                    _sb.replace(6, 8, second < 10 ? "0" + second : second + "");
                    if ((time = time / 60) > 0) {
                        long minute = time % 60;
                        _sb.replace(3, 5, minute < 10 ? "0" + minute : minute + "");
                        if ((time = time / 60) > 0) {
                            long hour = time;
                            _sb.replace(0, 2, hour < 10 ? "0" + hour : hour + "");
                        }
                    }
                }
            }
            _timeTv.setText(_sb.toString());
        }
    };

    private View.OnClickListener _statusClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (_status == 0) {
                _statusTv.setText("等待开始");
                _status = 1;
            } else {
                _statusTv.setText("计时结束");
                _status = 0;
            }
        }
    };
    private SensorEventListener _sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (_status == 1) {
                float[] values = event.values;
                switch (event.sensor.getType()) {
                    case Sensor.TYPE_PROXIMITY: {
                        if (values[0] == 0.0) {// 贴近手机
                            System.out.println("hands cover");
                            _statusTv.performClick();
                        } else {// 远离手机
                            System.out.println("hands moved");
                            startCount();
                        }
                    }
                    break;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        _sensorManager.registerListener(_sensorEventListener,
                _sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        _sensorManager.unregisterListener(_sensorEventListener);
    }
}
