package org.girijns.hnwcd;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog progressDialog;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Constants.initialize(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshButton = findViewById(R.id.refresh_btn);
        refreshButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        getDevices();
    }

    private void getDevices() {
        HttpGetTask asyncTask = new HttpGetTask(progressDialog,new ProcessCallback(this));
        asyncTask.execute(getString(R.string.router_service_url));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refresh_btn:
                getDevices();
        }
    }
}
