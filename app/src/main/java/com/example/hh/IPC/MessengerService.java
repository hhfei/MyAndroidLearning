package com.example.hh.IPC;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by hh on 2017/11/6.
 */
// Server
public class MessengerService extends Service{

    private static final String TAG = "MessengerService";
    public static final int msgFromClient = 0x100;
    public static final int msgFromServer = 0x101;

    private static class MessengerHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case msgFromClient:
                    Log.i(TAG,"receive message from client: "+msg.getData().getString("msg"));
                    Messenger messenger = msg.replyTo;
                    Message replyMsg = Message.obtain(null, msgFromServer);
                    Bundle data  = new Bundle();
                    data.putString("msg", "OK, I got your message");//服务端返回信息给客户端
                    replyMsg.setData(data);
                    try {
                        messenger.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                    break;
                default:
                    super.handleMessage(msg);
            }

        }
    }

    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
