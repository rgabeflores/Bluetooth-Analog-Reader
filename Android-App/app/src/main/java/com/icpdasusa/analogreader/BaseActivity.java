package com.icpdasusa.analogreader;

import android.app.Activity;
import android.os.Bundle;

/**
 * This class enables Android classes to maintain a reference of the currently running activity at run time.
 */
public class BaseActivity extends Activity {

    /**
     * The class that keeps a reference of the current activity.
     */
    protected BluetoothController mBluetoothController;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBluetoothController = (BluetoothController) this.getApplicationContext();
    }
    protected void onResume() {
        super.onResume();
        mBluetoothController.setCurrentActivity(this);
    }
    protected void onPause() {
        clearReferences();
        super.onPause();
    }
    protected void onDestroy() {
        clearReferences();
        super.onDestroy();
    }

    private void clearReferences(){
        Activity currActivity = mBluetoothController.getCurrentActivity();
        if (this.equals(currActivity))
            mBluetoothController.setCurrentActivity(null);
    }
}