package com.example.sensors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager lightSensorManager, accSensorManager;
    private Sensor light, accelerometer;
    TextView lightText, accText;
    float lastVal1, lastVal2, lastVal3, presentVal1, presentVal2, presentVal3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightText = (TextView) findViewById(R.id.lightText);
        accText = (TextView) findViewById(R.id.accText);

        lightSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        light = lightSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        accSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = accSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_LIGHT){
            if(event.values[0] >= 1000) {
                lightText.setText(R.string.masada);
                Intent intent = new Intent("com.example.EXAMPLE_ACTION");
                intent.putExtra("com.example.EXTRA_TEXT", "masada");
                sendBroadcast(intent);
            }
            else {
                lightText.setText(R.string.cepte);
                Intent intent = new Intent("com.example.EXAMPLE_ACTION");
                intent.putExtra("com.example.EXTRA_TEXT", "cepte");
                sendBroadcast(intent);
            }
        }

        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            presentVal1 = event.values[0];
            presentVal2 = event.values[1];
            presentVal3 = event.values[2];
            if(presentVal1 == lastVal1 &&  presentVal2 == lastVal2 && presentVal3 == lastVal3)
                accText.setText(R.string.hareketsiz);
            else
                accText.setText(R.string.hareketli);
        }
        lastVal1 = presentVal1;
        lastVal2 = presentVal2;
        lastVal3 = presentVal3;
    }

    @Override
    protected void onPause(){
        super.onPause();
        lightSensorManager.unregisterListener(this);
        accSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        lightSensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_NORMAL);
        accSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}