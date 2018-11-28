package com.icpdasusa.analogreader;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.util.Log;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

/**
 * This class handles all of the Bluetooth processes.
 */
public class BluetoothController extends Application {

    private static final String TAG = "BluetoothController";

    /**
     * These are the UUIDs specific to the HM-10 device's GATT server profile.
     */
    private static final UUID SERVICE_ID = SampleGattAttributes.getServiceId();
    private static final UUID CHARACTERISTIC_UUID = SampleGattAttributes.getCharacteristicId();

    /**
     * The MAC address of the HM-10.
     */
    private static final byte[] HM10_ADAPTER_ADDRESS = SampleGattAttributes.getMAC();

    /**
     * The <code>DataHandler</code> that saves and handles the data for the activities to use.
     */
    private static final DataHandler dataHandler = DataHandler.getInstance();

    /**
     * Interfaces with system Bluetooth properties.
     */
    private BluetoothManager mBluetoothManager;

    /**
     * Represents the Bluetooth Adapter.
     */
    private BluetoothAdapter mBluetoothAdapter;

    /**
     * Represents the HM-10 device.
     */
    private BluetoothDevice mBluetoothDevice;

    /**
     * Handles the Bluetooth LE scanning process.
     */
    private BluetoothLeScanner mBluetoothLeScanner;

    /**
     * Represents and interfaces with the GATT server
     */
    private BluetoothGatt mGatt;

    /**
     * A reference to the current activity.
     */
    private Activity mCurrentActivity = null;

    /**
     * Default constructor.
     */
    public BluetoothController(){}

    @Override
    public void onCreate(){
        super.onCreate();
        initBluetooth();
    }

    /**
     * Initializes Bluetooth LE communication with the HM-10 device.
     */
    public void initBluetooth(){
        // The BluetoothAdapter is required for any and all Bluetooth activity.
        mBluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        mBluetoothLeScanner.startScan(mScanCallback);

        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(HM10_ADAPTER_ADDRESS);

        mGatt = mBluetoothDevice.connectGatt(this, false, mGattCallback);
    }

    /**
     * Specifies Bluetooth LE scan callback actions.
     */
    private final ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            // We scan with report delay > 0. This will never be called.
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            if (!results.isEmpty()) {
                ScanResult result = results.get(0);
                BluetoothDevice device = result.getDevice();
                String deviceAddress = device.getAddress();
                System.out.println(deviceAddress);
                // Device detected, we can automatically connect to it and stop the scan
            }
            System.out.println(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            System.out.println("mScanCallback failed: " + errorCode);
        }
    };

    /**
     * Specifies Bluetooth GATT callback actions.
     */
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.i(TAG, "Connected to GATT client. Attempting to start service discovery");
                gatt.discoverServices();
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.w(TAG, "Disconnected from GATT client");
            }
        }
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status != BluetoothGatt.GATT_SUCCESS) {
                // Handle the error
                return;
            }
            // Get the service
            BluetoothGattService service = gatt.getService(UUID.fromString("0000FFe0-0000-1000-8000-00805f9b34fb"));
            // Get the characteristic
            BluetoothGattCharacteristic characteristic = service.getCharacteristic(UUID.fromString("0000FFE1-0000-1000-8000-00805f9b34fb"));
            // Get the descriptor
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString("00002902-0000-1000-8000-00805f9b34fb"));

            gatt.setCharacteristicNotification(characteristic, true);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            gatt.writeDescriptor(descriptor);
        }
        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            BluetoothGattCharacteristic characteristic = gatt.getService(SERVICE_ID).getCharacteristic(CHARACTERISTIC_UUID);
            gatt.readCharacteristic(characteristic);
            characteristic.setValue(new byte[]{1, 1});
            gatt.writeCharacteristic(characteristic);
        }
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            readCounterCharacteristic(characteristic);
        }
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status){
            readCounterCharacteristic(characteristic);
        }
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            readCounterCharacteristic(characteristic);
        }
        private void readCounterCharacteristic(BluetoothGattCharacteristic characteristic) {
            if (CHARACTERISTIC_UUID.equals(characteristic.getUuid())) {
                byte[] data = characteristic.getValue();
                // Arduino outputs ASCII
                String output = new String(data, Charset.forName("US-ASCII"));
                // Each signal value is delimited by a new line
                String values[] = output.split("\n");
                dataHandler.addAllData(Double.parseDouble(values[0]),Double.parseDouble(values[1]),Double.parseDouble(values[2]));

                /*
                    Check the current Activity to update the corresponding views.
                 */
                Activity currentActivity = ((BluetoothController)getApplicationContext()).getCurrentActivity();
                updateActivityViews(currentActivity, values);
            }
        }
    };

    /**
     * Retrieves the current activity
     * @return the current <code>Activity</code>
     */
    public Activity getCurrentActivity(){
        return mCurrentActivity;
    }

    /**
     * Sets the current activity. Used by <code>BaseActivity</code> to maintain reference to
     * current <code>Activity</code>.
     * @param mCurrentActivity
     */
    public void setCurrentActivity(Activity mCurrentActivity){
        this.mCurrentActivity = mCurrentActivity;
    }

    public void updateActivityViews(Activity mCurrentActivity, String[] values){
        if(mCurrentActivity instanceof GraphActivity) {
            Log.i(TAG, "Current Activity is GraphActivity");
            // TODO: implement live graph updating
        }
        else if(mCurrentActivity instanceof GaugeActivity) {
            Log.i(TAG, "Current Activity is GaugeActivity");
            GaugeActivity gaugeActivity = (GaugeActivity) mCurrentActivity;
            gaugeActivity.updateGaugeViews(values[0], values[1], values[2]);
        }
        else if(mCurrentActivity instanceof MainActivity) {
            Log.i(TAG, "Current Activity is MainActivity");
            MainActivity mainActivity = (MainActivity) mCurrentActivity;
            mainActivity.updateDataViews(values[0], values[1], values[2]);
        }
    }
}
