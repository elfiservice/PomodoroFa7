package br.com.fa7.layoutcardview;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Armando on 25/05/2016.
 */

public class StartedService extends Service implements ServiceNotifier {

    private ListenValue obj;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //Context context = getBaseContext();
               // TextView textView = (TextView) ((Activity) context).findViewById(R.id.mainTimer);
                for (int i = 0; i < 100; i++) {
                    try {

                        notifyValue(i + 1+"");
                        //textView.setText(i + 1);

                        Log.i("App", "Valor: " + (i + 1));
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopSelf();
            }
        }).start();

        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void add(ListenValue obj) {
        this.obj = obj;
    }

    @Override
    public void notifyValue(String value) {
        obj.newValue(value);
    }
}