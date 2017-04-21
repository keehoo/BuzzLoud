package com.example.krzysztofkubicki.buzzloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSmsReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("onReceive", "SMS ariived my lord!");
        Toast.makeText(context, "SMS !!!!!!!", Toast.LENGTH_SHORT).show();
        Bundle extras = intent.getExtras();
        for (String s : extras.keySet()) {
            Log.d("Key ", s);
        }
        final Object[] pdusObj = (Object[]) extras.get("pdus");
        assert pdusObj != null;
        for (int i = 0; i < pdusObj.length; i++) {

            SmsMessage currentMessage = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i], "3gpp");
            }
            if (null != currentMessage) {
                String senderNum = currentMessage.getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();
                Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                // Show Alert
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context,
                        "senderNum: " + senderNum + ", message: " + message, duration);
                toast.show();
                context.startService(new Intent(context, OutOfMuteService.class));
            }
        } // end for loop
    }
}
