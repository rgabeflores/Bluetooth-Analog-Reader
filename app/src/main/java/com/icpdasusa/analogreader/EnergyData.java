package com.icpdasusa.analogreader;

import java.util.LinkedList;
import java.util.Queue;

/**
 * This class is a data structure to store data relevant to energy levels.
 */
public class EnergyData {

    /**
     * The data that is stored.
     */
    private Queue<Double> data;

    /**
     * The last data that was stored.
     */
    private Double lastData;

    /**
     * The size of the data.
     */
    private int length;

    /**
     * Creates an energy data set with the default size.
     */
    public EnergyData(){
        length = 60;
        data = new LinkedList<>();
    }

    /**
     * Creates an energy data set with an optional size.
     * @param length
     */
    public EnergyData(int length){
        this.length = length;
        data = new LinkedList<>();
    }

    /**
     * Sets the data with a provided data set.
     * @param data the new data
     */
    public void setData(LinkedList<Double> data){
        this.data = data;
    }

    /**
     * Retrieves the data set.
     * @return <code>Queue<Double></code> containing the data values.
     */
    public Queue<Double> getData(){
        return data;
    }

    /**
     * Retrieves the last data value added.
     * @return a <code>Double</code> of the last data added.
     */
    public Double getLatestData(){
        return lastData;
    }

    /**
     * This function adds a new data point.
     * @param value The new data point to be added
     */
    public void addLatestData(Double value){
        if(data.size() > length) data.remove(); // Ensure that the Queue does not overgrow
        data.add(value);
        lastData = value;
    }
}
