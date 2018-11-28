
package com.icpdasusa.analogreader;

import java.math.BigInteger;
import java.util.UUID;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {

    private static final UUID SERVICE_ID = UUID.fromString("0000ffe0-0000-1000-8000-00805f9b34fb");
    private static final UUID CHARACTERISTIC_UUID = UUID.fromString("0000ffe1-0000-1000-8000-00805f9b34fb");
    private static final byte[] HM10_ADAPTER_ADDRESS = parseMAC("7C:01:0A:77:10:72");

    public static byte[] getMAC(){
        return HM10_ADAPTER_ADDRESS;
    }
    public static UUID getServiceId(){
        return SERVICE_ID;
    }
    public static UUID getCharacteristicId(){
        return CHARACTERISTIC_UUID;
    }

    /**
     * This function converts from an <code>int</code> to <code>UUID</code>
     * @param i the <code>int</code> value to convert from
     * @return a <code>UUID</code> representation of the specified <code>int</code>
     */
    public UUID convertFromInteger(int i) {
        final long MSB = 0x0000000000001000L;
        final long LSB = 0x800000805f9b34fbL;
        long value = i & 0xFFFFFFFF;
        return new UUID(MSB | (value << 32), LSB);
    }

    /**
     * Converts a <code>String</code> of a MAC address into a <code>byte[]</code> representation
     * @param macAddress
     * @return
     */
    public static byte[] parseMAC(String macAddress) {
        String[] bytes = macAddress.split(":");
        byte[] parsed = new byte[bytes.length];

        for (int x = 0; x < bytes.length; x++) {
            BigInteger temp = new BigInteger(bytes[x], 16);
            byte[] raw = temp.toByteArray();
            parsed[x] = raw[raw.length - 1];
        }
        return parsed;
    }
}
