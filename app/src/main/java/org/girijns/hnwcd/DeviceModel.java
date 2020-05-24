package org.girijns.hnwcd;

import androidx.annotation.NonNull;

public class DeviceModel {
    public String mac;
    public String ip;
    public String name;
    public String con;

    @NonNull
    @Override
    public String toString() {
        return  (name +
                "\nIp Address: " + ip +
        "\nMac Address: " + mac);
    }
}
