package biz.integsys.adf;

import java.util.Comparator;

/**
 * Created by jshoop on 12/11/15.
 */
public class NDB implements Comparable<NDB>, Comparator<NDB> {
    public int rssi;
    public String address;
    public long timestamp;

    public NDB(int rssi, String address, long timestamp) {
        this.rssi = rssi;
        this.address = address;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "address=" + address +
                "   rssi=" + rssi +
                "   timestamp=" + timestamp;
    }

    @Override
    public int compareTo(NDB another) {
        if (this.address.contains(another.address))
            return 0;
        return another.rssi - this.rssi;
    }

    @Override
    public int compare(NDB lhs, NDB rhs) {
        return lhs.address.compareTo(rhs.address);
    }
}