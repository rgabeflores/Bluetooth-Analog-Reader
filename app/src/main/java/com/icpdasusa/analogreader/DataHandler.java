package com.icpdasusa.analogreader;

import android.util.Log;

/**
 * This is a singleton class that handles the data for multiple activities to use. It saves the data captured
 * through the <code>MainActivity</code> and manages the historical data which can be used for <code>GraphActivity</code>.
 */
public class DataHandler{

    /**
     * For debugging purposes.
     */
    private final String TAG = "DATAHANDLER";

    /**
     * <code>Source</code> enum is used to represent the different types of data
     */
    public enum Source{
        WIND, WAVE, SOLAR;
    }

    /**
     * A <code>Queue</code> containing the historical wind data.
     */
    private EnergyData windData;
    /**
     * A <code>Queue</code> containing the historical wave data.
     */
    private EnergyData waveData;
    /**
     * A <code>Queue</code> containing the historical solar data.
     */
    private EnergyData solarData;

    /**
     * The array of all data
     */
    private EnergyData allData[] = {windData, waveData, solarData};

    /**
     * The number of seconds to save for the historical data.
     */
    private final int LENGTH = 60;

    /**
     * Get the length of the energy data.
     * @return int the length of the data.
     */
    public int getLength(){
        return LENGTH;
    }
    /**
     * This function retrieves the wind data from the past 60 seconds.
     * @return
     */
    public EnergyData getWindData(){
        return windData;
    }

    /**
     * This function retrieves the wave data from the past 60 seconds.
     * @return
     */
    public EnergyData getWaveData(){
        return waveData;
    }

    /**
     * This function retrieves the solar data from the past 60 seconds.
     * @return
     */
    public EnergyData getSolarData(){
        return solarData;
    }

    /**
     * Get each type of data.
     * @return an array containing each type of data
     */
    public EnergyData[] getAllData(){
        return allData;
    }

    /**
     * Set the wind data to existing data.
     * @param data the data to set
     */
    public void setWindData(EnergyData data){
        windData = data;
    }
    /**
     * Set the wave data to existing data.
     * @param data the data to set
     */
    public void setWaveData(EnergyData data){
        waveData = data;
    }
    /**
     * Set the solar data to existing data.
     * @param data the data to set
     */
    public void setSolarData(EnergyData data){
        solarData = data;
    }

    /**
     * Add data of a specific type.
     * @param src the type of data
     * @param value the data value
     */
    public void addData(Source src, Double value){
        if(src == Source.WIND) windData.addLatestData(value);
        if(src == Source.WAVE) waveData.addLatestData(value);
        if(src == Source.SOLAR) solarData.addLatestData(value);
    }

    /**
     * Saves data from each source (wind, wave, and solar).
     * @param wind
     * @param wave
     * @param solar
     */
    public void addAllData(Double wind, Double wave, Double solar){
        Log.i(TAG, "Saving Data: " + wind + " " + wave + " " + solar + " ");
        addData(Source.WIND, wind);
        addData(Source.WAVE, wave);
        addData(Source.SOLAR, solar);
    }

    /**
     * Using the singleton constructor methodology to have only one instance of <code>DataHandler</code>.
     */
    private DataHandler(){
        windData = new EnergyData(LENGTH);
        waveData = new EnergyData(LENGTH);
        solarData = new EnergyData(LENGTH);
    }

    /**
     * The single instance of <code>DataHandler</code>
     */
    private static final DataHandler dataHandler = new DataHandler();

    /**
     * Retrieve the singleton instance
     * @return the DataHandler instance
     */
    public static DataHandler getInstance(){ return dataHandler; }
}
