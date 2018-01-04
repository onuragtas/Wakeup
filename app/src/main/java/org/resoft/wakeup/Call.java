package org.resoft.wakeup;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

/**
 * Created by onuragtas on 04.01.2018.
 */

public class Call {
    @SuppressLint("MissingPermission")
    public Call(Context c, String tel) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+tel));
        c.startActivity(callIntent);
    }
}
