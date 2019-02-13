package mbenoukaiss.tetris.game;

import android.os.Handler;

import java.util.function.Supplier;

public class Clock {

    private Handler handler;

    private Supplier<Boolean> runnable;

    private boolean running;

    private int delay;

    public Clock(Supplier<Boolean> runnable, int delay) {
        this.handler = new Handler();
        this.runnable = runnable;
        this.running = false;
        this.delay = delay;
    }

    public synchronized void run() {
        running = true;

        handler.postDelayed(() -> {
            if(running)
                running = runnable.get();

            if(running)
                handler.postDelayed(this::run, delay);
        }, delay);
    }

    public synchronized void pause() {
        running = false;
    }

    public synchronized int getDelay() {
        return delay;
    }

    public synchronized void setDelay(int delay) {
        this.delay = delay;
    }

}
