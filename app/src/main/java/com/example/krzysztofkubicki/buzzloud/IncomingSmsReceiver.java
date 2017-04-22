package com.example.krzysztofkubicki.buzzloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingSmsReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPrefsHelper helper = new SharedPrefsHelper(context);
        boolean shouldUnmuteWhileSmsArrives = helper.shouldUnmuteWhileSmsArrives();
        Log.d("onReceive", String.valueOf(shouldUnmuteWhileSmsArrives));

        if (shouldUnmuteWhileSmsArrives) {
            final Object[] pdusObj = getPdusObjectArray(intent);
            for (int i = 0; i < pdusObj.length; i++) {

                SmsMessage currentMessage = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i], "3gpp");
                }
                if (null != currentMessage) {
                    String senderNum = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody().trim();
                    Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    // Show Alert

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                    if (messageContainsWakeUpCommand(message))
                   
                    context.startService(new Intent(context, OutOfMuteService.class));
                }
            }
        } // end for loop
    }

    private boolean messageContainsWakeUpCommand(String message) {
        return (message.contains("wtfru"));
    }

    @NonNull
    private Object[] getPdusObjectArray(Intent intent) {
        Bundle extras = intent.getExtras();
        final Object[] pdusObj = (Object[]) extras.get("pdus");
        assert pdusObj != null;
        return pdusObj;
    }
}
