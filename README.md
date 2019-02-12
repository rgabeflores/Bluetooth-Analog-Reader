# Bluetooth Analog Reader

An Android application listens to an Arduino via Bluetooth LE to display live data with a graph view for historical plots. Analog sensors are connected to the Arduino's serial pins to continuously read data. The Arduino reads the data and outputs the values to an [HM-10](http://fab.cba.mit.edu/classes/863.15/doc/tutorials/programming/bluetooth.html). While the HM-10 broadcasts the values, the Android application listens to the HM-10 via its GATT service profile.


## Screenshots

<div style="display: inline-block">
    <img src="https://github.com/rgabeflores/Bluetooth-Analog-Reader/blob/master/screenshots/live-page.jpg?raw=true" width="20%">
    <img src="https://github.com/rgabeflores/Bluetooth-Analog-Reader/blob/master/screenshots/graph-page.jpg?raw=true" width="40%">
    <img src="https://github.com/rgabeflores/Bluetooth-Analog-Reader/blob/master/screenshots/gauge-page.jpg?raw=true" width="20%">
</div>

## Installation

1. Clone the repository at this [link](https://github.com/rgabeflores/Bluetooth-Analog-Reader.git).
2. Upload the Arduino script to the Arduino through the [Arduino IDE](https://www.arduino.cc/en/main/software)
3. Open the Android application with [Android Studio](https://developer.android.com/studio/)

*  `/Arduino-Sensor` contains the Arduino (C++) script
* `/Android-App` contains the Android Studio project files for the Android application

## Required Libraries

* [BluetoothGatt](https://github.com/googlesamples/android-BluetoothLeGatt)
    * Reading data from Bluetooth LE device
* [Pygmalion Gauge View](https://github.com/Pygmalion69/Gauge)
    * Displaying data on gauges
* [GraphView](https://github.com/jjoe64/GraphView)
    * Displaying data on gauges

## Contributors

* [Gabriel Flores](https://github.com/rgabeflores)
* Steve Sin
