package br.com.fa7.layoutcardview;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Armando on 26/05/2016.
 */
public class BoundService extends Service implements ServiceNotifier {

    private ListenValue obj;
    private IBinder binder;
    private boolean stop;
    private boolean isCountStarted;


    public BoundService() {
        this.stop = false;
        this.isCountStarted = false;
        this.binder = new LocalBinder();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void startCounter(){
        if(!isCountStarted){
            isCountStarted = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int count = 1500;
                    int min;
                    int seg;
                    while(!stop){
                        try {
                                    count -= 1;
                                    min = count/60;
                                    seg = count%60;


                                    notifyValue(min + ":" + seg);
                                    Thread.sleep(1000);


                            //count += 1;
                            //notifyValue(count);
                            //Log.i("App", "Valor: " + count);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    stop = false;
                    isCountStarted = false;
                }
            }).start();
        }

    }

    public void stopCounter(){
        this.stop = true;
    }

    public void closeService(){
        stopSelf();
    }

    @Override
    public void add(ListenValue obj) {
        this.obj = obj;
    }

    @Override
    public void notifyValue(String value) {
        obj.newValue(value);
    }

    public class LocalBinder extends Binder {
        public BoundService getService(){
            return BoundService.this;
        }
    }
}