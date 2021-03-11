package edu.neu.cs5520.chatime.threading;

import edu.neu.cs5520.chatime.domain.executor.MainThread;

public class TestMainThread implements MainThread {
    @Override
    public void post(Runnable runnable) {
        // tests can run on this thread, no need to invoke other threads
        runnable.run();
    }
}
