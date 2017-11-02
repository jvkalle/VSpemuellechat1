package com.example.vspemuellechat.ch.ethz.inf.vs.a3.udpclient;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.vspemuellechat.R;

import static com.example.vspemuellechat.R.string.ip;
import static com.example.vspemuellechat.R.string.port;



public class SettingsActivity extends PreferenceActivity {

    public static String IP = "ip";
    public static String PORT = "port";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        NetworkConsts.SERVER_ADDRESS = Integer.toString(ip);
        NetworkConsts.UDP_PORT = port;
    }

}
