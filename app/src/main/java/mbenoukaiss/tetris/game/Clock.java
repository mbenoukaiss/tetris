package mbenoukaiss.tetris.game;

import android.os.Handler;

import java.util.function.Supplier;

public class Clock {

    private Handler handler;

    private Supplier<Boolean> runnable;

    private boolean running;

    private long delay;

    private float factor;

    public Clock(Supplier<Boolean> runnable, long delay) {
        this.handler = new Handler();
        this.runnable = runnable;
        this.running = false;
        this.delay = delay;
        this.factor = 1.0f;
    }

    public synchronized void start() {
        running = true;

        handler.removeCallbacksAndMessages(null);
        run();
    }

    public synchronized void run() {
        handler.postDelayed(() -> {
            if(running)
                running = runnable.get();

            if(running)
                run();
        }, getAcceleratedDelay());
    }

    public synchronized void pause() {
        running = false;
        handler.removeCallbacksAndMessages(null);
    }

    public synchronized long getAcceleratedDelay() {
        return (long) (delay * factor);
    }

    public synchronized long getDelay() {
        return delay;
    }

    public synchronized void setDelay(long delay) {
        this.delay = delay;
    }

    public synchronized void accelerate(float factor) {
        this.factor /= factor;
    }

    public synchronized void decelerate() {
        factor = 1.0f;
    }

    public synchronized boolean isAccelerated() {
        return factor != 1.0f;
    }

}
