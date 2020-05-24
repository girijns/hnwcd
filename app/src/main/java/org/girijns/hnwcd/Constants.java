package org.girijns.hnwcd;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    private Context ctx;
    public static final Map<String,String> DEVICE_SIMPLE_NAMES = new HashMap<>(40);
    public static String IP_NAME_HEADER;
    public static String CON_HEADER;
    public static String MAC_HEADER;
    /* Add all device mappings */
    static {
        DEVICE_SIMPLE_NAMES.put("Device123123","Nest Camera");
        DEVICE_SIMPLE_NAMES.put("Device456789","Smart tv");
        DEVICE_SIMPLE_NAMES.put("Device143235","Home Laptop");
        DEVICE_SIMPLE_NAMES.put("Device8867","XBox");

    }
    public static void initialize(Context ctx) {
        IP_NAME_HEADER = ctx.getString(R.string.ip_name_header);
        CON_HEADER = ctx.getString(R.string.con_header);
        MAC_HEADER = ctx.getString(R.string.mac_header);

    }
}
