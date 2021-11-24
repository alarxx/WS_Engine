package client_android;/*package com.rat6.ict_messenger_android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.rat6.ict_messenger_android.server.Connection;

public class MainActivity extends AppCompatActivity {

    public static final int port = 15432;
    public static final String IP = "10.200.1.194";

    private Connection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connection = new Connection(IP, port);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onPause(){
        super.onPause();
        connection.turnOff();
    }

    @Override
    public void onResume(){
        super.onResume();
        connection = new Connection(IP, port);
    }
}
*/