package org.resoft.wakeup;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.PROCESS_OUTGOING_CALLS, Manifest.permission.WRITE_CALL_LOG, Manifest.permission.READ_CALL_LOG},
                    1
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DataManager.call = true;
        verifyStoragePermissions(MainActivity.this);

        final EditText number = (EditText) findViewById(R.id.number);
        Button ara = (Button) findViewById(R.id.ara);
        Button stop = (Button) findViewById(R.id.stop);

        ara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Call(getApplicationContext(), number.getText().toString());
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataManager.call = false;
            }
        });
    }
}
