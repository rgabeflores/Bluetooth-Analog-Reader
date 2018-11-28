package com.icpdasusa.analogreader;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MAIN";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        final Button button = findViewById(R.id.viewGaugeButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showGauges();
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
     * Update the data displayed in the UI
     * @param wind the new wind value
     * @param wave the new wave value
     * @param solar the new solar value
     */
    public void updateDataViews(String wind, String wave, String solar) {
        Log.i(TAG,"Updating Data Views: " + wind + " " + wave + " " + solar);

        TextView windTextView = (TextView) findViewById(R.id.windDataView);
        TextView waveTextView = (TextView) findViewById(R.id.waveDataView);
        TextView solarTextView = (TextView) findViewById(R.id.solarDataView);

        try {
            windTextView.setText(wind);
            waveTextView.setText(wave);
            solarTextView.setText(solar);
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

    /**
     * Switch the activity to <code>GaugeActivity</code>
     */
    public void showGauges(){
        Intent myIntent = new Intent(this, GaugeActivity.class);
        this.startActivity(myIntent);
    }

}
