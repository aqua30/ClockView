package com.aqua30.clockview.clock.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.aqua30.clockview.R;
import com.aqua30.clockview.clock.drawings.ClockView;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.circleView)ClockView clockView;

    private int seconds = 0;
    private TickerThread tickerThread;
    private TickerHandler tickerHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tickerHandler = new TickerHandler();
        tickerThread = getNewThread(tickerHandler);
        clockView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                /* removing the listener */
                clockView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                /* start the thread once the view is ready */
                clockView.initialize();
            }
        });
    }

    private TickerThread getNewThread(Handler handler){
        final TickerThread newThread = new TickerThread("TickerThread", Process.THREAD_PRIORITY_BACKGROUND);
        newThread.setRunning(true);
        newThread.setHandler(handler);
        return newThread;
    }

    public void onStartClock(View view){
        if (tickerThread != null) {
            tickerThread.setRunning(false);
            tickerThread.quit();
            tickerThread = null;
            tickerThread = getNewThread(tickerHandler);
        }
        tickerThread.start();
    }

    public void onStopClock(View view){
        tickerThread.setRunning(false);
    }

    private final class TickerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            Log.e("handler","handlerMessage");
            if (msg.arg1 == 1) {
                ++seconds;
                if (seconds > 60) {
                    seconds = 1;
                }
                clockView.tick(seconds);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (tickerThread != null)
            tickerThread.setRunning(false);
    }

    @Override
    protected int getActivityLayout() {
        return R.layout.activity_main;
    }
}