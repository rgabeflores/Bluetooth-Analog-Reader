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

    /**
     * This is the size of each line on the graph.
     */
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

        // Get singleton instance of DataHandler
        DataHandler dataHandler = DataHandler.getInstance();

        // Get each collection of data
        EnergyData windData = dataHandler.getWindData();
        EnergyData waveData = dataHandler.getWaveData();
        EnergyData solarData = dataHandler.getSolarData();

        // Create series for each collection of data
        LineGraphSeries<DataPoint> windSeries = initSeries(windData, Color.RED);
        LineGraphSeries<DataPoint> waveSeries = initSeries(waveData, Color.BLUE);
        LineGraphSeries<DataPoint> solarSeries = initSeries(solarData, Color.GREEN);

        // Add each series to the graph
        graph.addSeries(windSeries);
        graph.addSeries(waveSeries);
        graph.addSeries(solarSeries);
    }

    /**
     * Initialize a series based off an <code>EnergyData</code> collection of data.
     * @param data the <code>EnergyData</code> containing the values
     * @param color the color of the line displayed on the graph
     * @return a complete series to add to the graph
     */
    public LineGraphSeries<DataPoint> initSeries(EnergyData data, int color){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

        ArrayList<Double> values = new ArrayList<>(data.getData());

        int size = values.size();

        for(int x = 0; x < size; x++)
            series.appendData(new DataPoint(x, values.get(x)), true, size);
        series.setColor(color);
        series.setThickness(LINE_SIZE);
        series.setAnimated(true);

        return series;
    }
}
