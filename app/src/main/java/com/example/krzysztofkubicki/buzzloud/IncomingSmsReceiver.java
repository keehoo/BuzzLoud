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

    private Context c;

    @Override
    public void onReceive(Context context, Intent intent) {


        c = context;

        SharedPrefsHelper helper = new SharedPrefsHelper(context);
        boolean shouldUnmuteWhileSmsArrives = helper.shouldUnmuteWhileSmsArrives();
        Log.d("DEBUG", String.valueOf(shouldUnmuteWhileSmsArrives));

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

                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                    if (messageContainsWakeUpCommand(message))

                        context.startService(new Intent(context, OutOfMuteService.class));
                }
            }
        } // end for loop
    }

    private boolean messageContainsWakeUpCommand(String message) {
        return (message.contains("wtfru") || messageContainsCustomUnmuteCommands(message));
    }

    private boolean messageContainsCustomUnmuteCommands(String message) {

        SharedPrefsHelper sharedPrefsHelper = new SharedPrefsHelper(c);
        String unmuteKeyword = sharedPrefsHelper.getUnmuteKeyword();
        return (message.contains(unmuteKeyword));
    }

    @NonNull
    private Object[] getPdusObjectArray(Intent intent) {
        Bundle extras = intent.getExtras();
        final Object[] pdusObj = (Object[]) extras.get("pdus");
        assert pdusObj != null;
        return pdusObj;
    }
}
