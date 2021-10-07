package com.example.accelerometersensoractivity

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometerSensor: Sensor? = null
    private var xyzTextView: TextView? = null
    private var imageViewX: ImageView? = null
    private var imageViewY: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        xyzTextView = findViewById(R.id.xyzTextView)
        imageViewX = findViewById(R.id.imageViewX)
        imageViewY = findViewById(R.id.imageViewY)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        val deviceSensors: List<Sensor> = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for (sensor in deviceSensors) {
            Log.d("sensorsList", sensor.name)
        }

        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            Log.d("isSensorFound", "Sensor found")
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        } else {
            Log.d("isSensorFound", "Sensor not found")
        }

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            xyzTextView?.setText("X: " + x + "\n" + "Y: " + y + "\n" + "Z: " + z)

            if (x > 3) {
                imageViewX?.visibility = ImageView.VISIBLE
                imageViewX?.setImageResource(R.drawable.ic_arrow_back_black_24dp)
            } else if (x < -3) {
                imageViewX?.visibility = ImageView.VISIBLE
                imageViewX?.setImageResource(R.drawable.ic_arrow_forward_black_24dp)
            } else {
                imageViewX?.visibility = ImageView.INVISIBLE
            }

            if (y > 3) {
                imageViewY?.visibility = ImageView.VISIBLE
                imageViewY?.setImageResource(R.drawable.ic_arrow_downward_black_24dp)
            } else if (y < -3) {
                imageViewY?.visibility = ImageView.VISIBLE
                imageViewY?.setImageResource(R.drawable.ic_arrow_upward_black_24dp)
            } else {
                imageViewY?.visibility = ImageView.INVISIBLE
            }

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // Do something here if sensor accuracy changes.
    }

}