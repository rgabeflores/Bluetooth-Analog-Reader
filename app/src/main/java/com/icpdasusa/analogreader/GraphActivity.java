package com.icpdasusa.analogreader;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * This is the graph activity for the application. It displays a 1 minute history of the data on a line graph.
 *
 */
public class GraphActivity extends BaseActivity {

    /**
     * For debugging purposes.
     */
    private final String TAG = this.getClass().getSimpleName();

    private static final int LINE_SIZE = 10;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        final Button button = findViewById(R.id.viewLiveButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        GraphView graph = (GraphView) findViewById(R.id.mainGraph);
        graph.setTitle("1 Minute History");
        graph.setTitleTextSize(72);

        // Used to access all data
        DataHandler dataHandler = DataHandler.getInstance();

//        EnergyData allData[] = dataHandler.getAllData();

        EnergyData windData = dataHandler.getWindData();
        EnergyData waveData = dataHandler.getWaveData();
        EnergyData solarData = dataHandler.getSolarData();

        LineGraphSeries<DataPoint> windSeries = new LineGraphSeries<>();
        ArrayList<Double> windDataValues = new ArrayList<>(windData.getData());
        for(int x = 0; x < windDataValues.size(); x++)
            windSeries.appendData(new DataPoint(x,windDataValues.get(x)), true, dataHandler.getLength());
        windSeries.setColor(Color.RED);
        windSeries.setThickness(LINE_SIZE);
        windSeries.setAnimated(true);

        LineGraphSeries<DataPoint> waveSeries = new LineGraphSeries<>();
        ArrayList<Double> waveDataValues = new ArrayList<>(waveData.getData());
        for(int x = 0; x < waveDataValues.size(); x++)
            waveSeries.appendData(new DataPoint(x,waveDataValues.get(x)), true, dataHandler.getLength());
        waveSeries.setColor(Color.BLUE);
        waveSeries.setThickness(LINE_SIZE);
        waveSeries.setAnimated(true);

        LineGraphSeries<DataPoint> solarSeries = new LineGraphSeries<>();
        ArrayList<Double> solarDataValues = new ArrayList<>(solarData.getData());
        for(int x = 0; x < solarDataValues.size(); x++)
            solarSeries.appendData(new DataPoint(x,solarDataValues.get(x)), true, dataHandler.getLength());
        solarSeries.setColor(Color.GREEN);
        solarSeries.setThickness(LINE_SIZE);
        solarSeries.setAnimated(true);

        graph.addSeries(windSeries);
        graph.addSeries(waveSeries);
        graph.addSeries(solarSeries);
    }


    /**
     * This function is a wrapper for adding a new <code>DataPoint</code>. It adds the new <code>DataPoint</code>
     * to the end of the series.
     * @param value the y value of the new <code>DataPoint</code>
     */
    private void addDataPoint(double value){
//        series.appendData(
////                new DataPoint(MAX_DATA_POINTS, value),
////                true,
////                MAX_DATA_POINTS
////        );
    }
}
