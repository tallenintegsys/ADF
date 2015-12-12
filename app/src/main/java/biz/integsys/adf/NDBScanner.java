package biz.integsys.adf;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.util.Log;

import java.util.TreeSet;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jshoop on 12/11/15.
 */
public class NDBScanner {
    private final BluetoothLeScanner btScanner;
    private boolean scanning = false;
    private final NDBTreeSet scanNDBTreeSet = new NDBTreeSet(); //the beacons to be displayed next
    private ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(1);
    private final Runnable rescan = new Runnable() {
        @Override
        public void run() {
            btScanner.stopScan(scanCallback);
            //btScanner.flushPendingScanResults(scanCallback);
            long now = System.nanoTime();
            Log.d("ni", "                                now: " + now);
            for (NDB beacon : scanNDBTreeSet) {
                Log.d("ni", "name: "+beacon.address+"   timestamp: "+beacon.timestamp);
                if (beacon.timestamp > (now + 10000))
                    scanNDBTreeSet.remove(beacon);
            }
            btScanner.startScan(scanCallback);
        }
    };

    public NDBScanner(Context context) {
        BluetoothManager bluetoothManager= (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        btScanner = bluetoothAdapter.getBluetoothLeScanner();
    }

    public void startScan() {
        btScanner.startScan(scanCallback);
        timer.scheduleAtFixedRate(rescan, 0, 10000, TimeUnit.MILLISECONDS);
        scanning = true;
    }

    public void stopScan() {
        btScanner.stopScan(scanCallback);
        timer.remove(rescan);
        scanning = false;
    }

    public void clear() {
        scanNDBTreeSet.clear();
    }

    public boolean isScanning() {
        return scanning;
    }

    public TreeSet<NDB> getScanNDBTreeSet() {
        return scanNDBTreeSet;
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            Log.d("ni", "onScanResult");
            NDB beacon = new NDB(result.getRssi(), result.getDevice().getAddress(), result.getTimestampNanos());
            scanNDBTreeSet.add(beacon);
        }
    };
}
