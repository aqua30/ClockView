package com.aqua30.clockview.clock.controller;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import static com.aqua30.clockview.clock.drawings.ClockConstants.ClockTickAutoInterval;

/**
 * Created by Saurabh 2017.
 */

public class TickerThread extends HandlerThread {

    private boolean isRunning = false;
    private long lastTime = 0;
    private Handler handler;

    public TickerThread(String name, int priority) {
        super(name, priority);
    }

    public void setHandler(Handler handler){
        this.handler = handler;
    }

    @Override
    public void run() {
        lastTime = System.currentTimeMillis();
        while (isRunning) {
            final long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime >= ClockTickAutoInterval) {
                Message msg = handler.obtainMessage();
                msg.arg1 = 1;
                handler.sendMessage(msg);
                lastTime = currentTime;
            }
        }
    }

    public void setRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
