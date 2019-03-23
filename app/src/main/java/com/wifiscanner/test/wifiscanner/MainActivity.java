package com.wifiscanner.test.wifiscanner;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WifiManager manager;
    TextView txtSSID;
    TextView txtFreq;
    TextView txtRSSI;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        Toast.makeText(getApplicationContext(),"RSSI".length() + "",Toast.LENGTH_LONG).show();

        txtSSID = (TextView) findViewById(R.id.txtSSID);
        txtFreq = (TextView) findViewById(R.id.txtFreq);
        txtRSSI = (TextView) findViewById(R.id.txtRSSI);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    List<ScanResult> results = manager.getScanResults();

                    for (int i = 0; i < results.size(); i++) {

                        txtSSID.setText(txtSSID.getText() + "\n" + results.get(i).SSID);
                        txtFreq.setText(txtFreq.getText() + "\n" + results.get(i).frequency);
                        txtRSSI.setText(txtRSSI.getText() + "\n" + results.get(i).level);
                    }

                }
            }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        }
    }

    private String Fix(String s, int fix) {

        while (s.length() != fix)
            s = "x" + s;

        return s;

    }
}
