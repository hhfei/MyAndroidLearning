package com.example.hh.IPC;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.hh.myandroidlearning.R;

/**
 * Created by hh on 2017/11/6.
 *
 */

// Client
public class MessengerActivity  extends AppCompatActivity{


    public static final String TAG  = "MessengerActivity";
    // 客户端发送的 Messenger
    private Messenger mService = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            Message msg = Message.obtain(null, MessengerService.msgFromClient);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello this is a client");
            msg.setData(bundle);
            msg.replyTo = replyMessenger;//用于服务器返回信息的处理
            Log.i(TAG, "[onServiceConnected] before sendMsg");
            try {
                mService.send(msg);//其实是内部的handler发送消息
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.i(TAG, "[onServiceConnected] after sendMsg");
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    //用于接受服务端的返回信息的 Messenger
    private Messenger replyMessenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case MessengerService.msgFromServer:
                    Log.i(TAG,"receive message from Server: "+msg.getData().getString("msg"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_messengeracvitity);

        Log.i(TAG, "[onCreate] before bindService");
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        Log.i(TAG, "[onCreate] after bindService");
    }

    @Override
    protected void onDestroy() {
        unbindService(serviceConnection);
        super.onDestroy();
    }
}
