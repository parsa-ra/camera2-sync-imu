/* All rights reserved to parsa rahimi noshanagh */

package com.example.android.camera2video;

import android.hardware.Sensor ;
import android.hardware.SensorManager ;
import android.hardware.SensorEventListener ;
import android.hardware.SensorEvent ;
import android.content.Context ;
import android.os.SystemClock ;
import android.content.Context ;
import android.util.Log ;
import java.util.Vector ;

public class IMUHandling implements SensorEventListener {
    private static final String TAG = "ParaCameraSensorManager" ;
    private double GyroMinDelay;
    private double AccMinDelay;
    private double MaxGyroRate;
    private double MaxAccRate;
    // Current TimeStamp in Millis
    public Long currTime = SystemClock.elapsedRealtimeNanos();

    // Gyro TimeStamp and Data
    public Vector<Long> timeStampGyro = new Vector<Long>() ;
    public Vector<float[]> gyroData = new Vector<float[]>() ;

    // Accelerometer TimeStamp and Data
    public Vector<Long> timeStampAcc = new Vector<Long>() ;
    public Vector<float[]> accData = new Vector<float[]>() ;

    // Get an instance of sensor manager
    private SensorManager sensorManager;

    // Our sensor to be used
    private Sensor sensorGyro;
    private Sensor sensorAcc ;

    // initializing the sensors
    // Default Constructor
    public IMUHandling(Context mContext) {
        // initializing the sensor manager
        this.sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        this.sensorGyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        this.sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.GyroMinDelay = sensorGyro.getMinDelay();
        this.AccMinDelay = sensorAcc.getMinDelay();
        this.MaxAccRate = (1 / (this.AccMinDelay * Math.pow(10, -6))) ;
        this.MaxGyroRate = (1 / (this.GyroMinDelay * Math.pow(10, -6))) ;

        // Show some information about Gyroscope and Accelerometer
        Log.d(TAG, String.format("Gyro Min Delay: %.3f in MicroSecond === %.3f Hz\nAcc Min Delay %.3f MicroSeconds === %.3f Hz",
                this.GyroMinDelay, MaxGyroRate ,
                this.AccMinDelay, MaxAccRate));
    }

    public void regSensors(){
        Log.i(TAG, "Registering IMU Listeners");
        // Maximum SamplingRate Possible
        sensorManager.registerListener(this, sensorGyro, (int)GyroMinDelay) ;
        sensorManager.registerListener(this, sensorAcc, (int)AccMinDelay) ;

    }

    public void unRegSensors(){
        Log.i(TAG, "UnRegistering IMU Listeners") ;
        // Maximum sampling rate possible
        sensorManager.unregisterListener(this, sensorGyro);
        sensorManager.unregisterListener(this, sensorAcc);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            timeStampGyro.add(event.timestamp - this.currTime) ;
            float[] data = {event.values[0], event.values[1], event.values[2]} ;
            gyroData.add(data) ;

        }else if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            timeStampAcc.add(event.timestamp - this.currTime) ;
            float[] data = {event.values[0], event.values[1], event.values[2]} ;
            accData.add(data) ;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

        Log.i(TAG, String.format("Accuracy changed to %d", accuracy)) ;

    }

}
