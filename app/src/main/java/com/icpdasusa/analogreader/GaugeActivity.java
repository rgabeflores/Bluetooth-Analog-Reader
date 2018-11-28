package com.icpdasusa.analogreader;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import de.nitri.gauge.Gauge;

public class GaugeActivity extends BaseActivity {

    private static final String TAG = "GAUGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gauge);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Button button = findViewById(R.id.viewLiveButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
        final Button graphButton = findViewById(R.id.viewGraphButton);
        graphButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showGraph();
            }
        });
    }

    /**
     * Update the values displayed on the gauges.
     * @param wind the value for the wind gauge
     * @param wave the value for the wave gauge
     * @param solar the value for the solar gauge
     */
    public void updateGaugeViews(String wind, String wave, String solar){
        Log.i(TAG,"Updating Gauge Views: " + wind + " " + wave + " " + solar);
        Gauge windGauge = (Gauge) findViewById(R.id.windGauge);
        Gauge waveGauge = (Gauge) findViewById(R.id.waveGauge);
        Gauge solarGauge = (Gauge) findViewById(R.id.solarGauge);

        try {
            windGauge.setValue(Float.parseFloat(wind));
            waveGauge.setValue(Float.parseFloat(wave));
            solarGauge.setValue(Float.parseFloat(solar));
        } catch(Exception e){
            Log.w(TAG, e.getMessage());
        }
    }

    /**
     * Switch the activity to <code>GraphActivity</code>
     */
    public void showGraph(){
        Intent myIntent = new Intent(this, GraphActivity.class);
        this.startActivity(myIntent);
    }
}
