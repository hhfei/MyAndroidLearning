package com.example.hh.myandroidlearning;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hh.IPC.MessengerActivity;

public class MainActivity extends AppCompatActivity {

    private Button but_IPC_messenger = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but_IPC_messenger = (Button) findViewById(R.id.but_IPC_messenger);
        but_IPC_messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MessengerActivity.class);
                startActivity(intent);
            }
        });
    }
}
