package com.example.krzysztofkubicki.buzzloud;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public boolean allowUnmuting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        allowUnmuting = false;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissions();
        final TextView volume = (TextView) findViewById(R.id.volume);
        Button raiseVol = (Button) findViewById(R.id.raise_vol);
        Button lowerVol = (Button) findViewById(R.id.lower_vol);
        Button unmute = (Button) findViewById(R.id.unmute);
        final AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        setVolumeText(volume, audio);

        raiseVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_RAISE, 0);
                setVolumeText(volume, audio);
            }
        });

        lowerVol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audio.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_LOWER, 0);
                setVolumeText(volume, audio);
            }
        });

        unmute.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                audio.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, 0);
                setVolumeText(volume, audio);
            }
        });
    }

    public void checkPermissions() {
        NotificationManager notificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                && !notificationManager.isNotificationPolicyAccessGranted()) {

            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }

        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }

    }

    private void setVolumeText(TextView volume, AudioManager audio) {
        volume.setText(String.valueOf(audio.getStreamVolume(AudioManager.STREAM_RING)));
    }


}
