package com.example.lukas.shellgame;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends Activity
{

    private EditText txtPlayerName;
    private CheckBox chckLightBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if(((ExtendMyApplication) getApplication()).getLightBackground())
            setTheme(R.style.LightTheme);
        else
            setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_settings);
        chckLightBackground = findViewById(R.id.chkLightBackground);
        txtPlayerName = findViewById(R.id.txtPlayerName);
        txtPlayerName.setText(((ExtendMyApplication) getApplication()).getPlayerName());
        txtPlayerName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable s)
            {
                ((ExtendMyApplication) getApplication()).setPlayerName(txtPlayerName.getText().toString());
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {


            }
        });
        chckLightBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            if (isChecked)
            {
                ((ExtendMyApplication) getApplication()).setLightBackground(true);
            }
            else
            {
                Log.d("lightFalse","false");
                ((ExtendMyApplication) getApplication()).setLightBackground(false);
            }

        }
    });
    }
}
