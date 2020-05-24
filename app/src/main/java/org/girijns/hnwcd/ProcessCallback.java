package org.girijns.hnwcd;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProcessCallback {
    private Context ctx;

    public ProcessCallback(final Context ctx) {
        this.ctx = ctx;
    }

    public void run(String result) {
        final ListView listview = (ListView)((AppCompatActivity)ctx).findViewById(R.id.device_list);
        final List<String> backingList = new ArrayList<String>();
        final DeviceArrayAdapter<String> arrayAdapter = new DeviceArrayAdapter<>(ctx,
                android.R.layout.simple_list_item_1, backingList);
        populateIt(result, backingList);
        listview.setAdapter(arrayAdapter);
    }

    private void populateIt(final String result, final List<String> backingList) {
        if(null == result || !result.contains("html")) {
            backingList.add("Cannot call router service");
            return;
        }
        try {
            parseResult(result,backingList);
        } catch(Exception ex) {
            ex.printStackTrace();
            backingList.add("Bad response from router service");
        }
    }

    public void parseResult(String result, final List<String> backingList) {
        Element body = Jsoup.parse(result).select("#content-sub > form > div > table > tbody").first();
        String header = null;
        DeviceModel device = null;
        final Map<String, List<DeviceModel>> devices = new HashMap<>(3);
        //tr
        for(Element e : body.children()) {
            //td or th
            for(Element x : e.children()) {
                final String t = x.text();
                if(t.equals(Constants.MAC_HEADER)) {
                    header = t;
                    if(null != device) {
                        if(devices.get(device.con) == null) {
                            devices.put(device.con, new ArrayList<DeviceModel>(10));
                        }
                        devices.get(device.con).add(device);
                    }
                    device = new DeviceModel();
                    continue;
                }
                if(t.equals(Constants.IP_NAME_HEADER) || t.equals(Constants.CON_HEADER)) {
                    header = t;
                    continue;
                }
                if(header != null) {
                    if(header.equals(Constants.MAC_HEADER)) {
                        device.mac = t;
                    }
                    if(header.equals(Constants.IP_NAME_HEADER)) {
                        translateName(device,t);
                    }
                    if(header.equals(Constants.CON_HEADER)) {
                        device.con = t;
                    }
                    header = null;
                    continue;
                }
                header = null;
            }
        }
        reorganizeResults(devices,backingList);

    }

    private void translateName(DeviceModel device, String t) {
        String [] vals = t.split("/");
        if(null != vals && vals.length == 2) {
            device.name = vals[1].trim();
            device.ip = vals[0].trim();
            if(Constants.DEVICE_SIMPLE_NAMES.get(device.name) != null) {
                device.name = Constants.DEVICE_SIMPLE_NAMES.get(device.name);
            }
        } else {
            device.name = t;
        }
    }

    private void reorganizeResults(Map<String, List<DeviceModel>> devices, List<String> populate) {
        Set<Map.Entry<String, List<DeviceModel>>> entries = devices.entrySet();
        for(Map.Entry<String, List<DeviceModel>> entry : entries) {
            populate.add(translateCon(entry.getKey()) + ", devices(" + entry.getValue().size() + ")");
            for(DeviceModel val : entry.getValue()) {
                populate.add(val.toString());

            }
        }
    }

    /**
     * Translate your conection name
     * @param key the value form the touter something like Ethernet Lan 1
     * @return the readable value
     */
    private String translateCon(String key) {
        if(key.endsWith("1")) {
            return "Upstairs, " + key;
        }
        if(key.endsWith("2")) {
            return "Downstairs, " + key;
        }
        return key;
    }
}
