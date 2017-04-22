package com.example.krzysztofkubicki.buzzloud;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {


    private EditText editText;
    private Button button;
    private SharedPrefsHelper sharedPrefsHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPrefsHelper = new SharedPrefsHelper(getApplicationContext());
        editText = (EditText) findViewById(R.id.editTextSetUnmuteCommand);
        editText.setText(sharedPrefsHelper.getUnmuteKeyword());
        button = (Button) findViewById(R.id.addUnmuteCommandButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = editText.getText().toString();
                sharedPrefsHelper.addUnmuteKeyword(keyWord);
                finish();
            }
        });

    }
}