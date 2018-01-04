package org.resoft.wakeup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;

public class ReceiverCall extends BroadcastReceiver {

    // The receiver will be recreated whenever android feels like it. We need a
    // static variable to remember data between instantiations

    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static Date callStartTime;
    private static boolean isIncoming;
    private static String savedNumber; // because the passed incoming is only
    // valid in ringing

    @Override
    public void onReceive(Context context, Intent intent) {

        // We listen to two intents. The new outgoing call only tells us of an
        // outgoing call. We use it to get the number.
        if (intent.getAction()
                .equals("android.intent.action.NEW_OUTGOING_CALL")) {
            savedNumber = intent.getExtras().getString(
                    "android.intent.extra.PHONE_NUMBER");
        } else {
            String stateStr = intent.getExtras().getString(
                    TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(
                    TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                state = TelephonyManager.CALL_STATE_IDLE;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            } else if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                state = TelephonyManager.CALL_STATE_RINGING;
            }
            if(DataManager.call) {
                new Call(context, savedNumber);
            }
            onCallStateChanged(context, state, number);
        }
        System.out.println("action:"+intent.getAction());
    }

    // Derived classes should override these to respond to specific events of
    // interest

    protected void onIncomingCallStarted(Context ctx, String number, Date start) {
        System.out.println("Incoming " + number);
    }

    protected void onOutgoingCallStarted(Context ctx, String number, Date start) {
        System.out.println("Outgoing " + number);
    }

    protected void onIncomingCallEnded(Context ctx, String number, Date start,
                                       Date end) {
        System.out.println("InEnd " + number);
    }

    protected void onOutgoingCallEnded(Context ctx, String number, Date start,
                                       Date end) {
        System.out.println("OutEnd " + number);
    }

    protected void onMissedCall(Context ctx, String number, Date start) {
        System.out.println("Miss " + number);
    }

    // Deals with actual events

    // Incoming call- goes from IDLE to RINGING when it rings, to OFFHOOK when
    // it's answered, to IDLE when its hung up
    // Outgoing call- goes from IDLE to OFFHOOK when it dials out, to IDLE when
    // hung up
    public void onCallStateChanged(Context context, int state, String number) {
        if (lastState == state) {
            // No change, debounce extras
            return;
        }
    }
}