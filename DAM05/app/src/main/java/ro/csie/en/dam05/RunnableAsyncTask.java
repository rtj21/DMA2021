package ro.csie.en.dam05;

import android.os.Handler;

import java.util.concurrent.Callable;

public class RunnableAsyncTask<R> implements Runnable {
    private Handler mtHandler;
    private Callable<R> asyncTask;
    private Callback<R> callableResponse;
    public <T> RunnableAsyncTask(Handler mtHandler, Callable<R> asyncTask, Callback<R> bitmapCallback) {
        this.mtHandler = mtHandler;
        this.asyncTask = asyncTask;
        this.callableResponse = bitmapCallback;
    }

    @Override
    public void run() {
        try {
            R result = asyncTask.call();
            mtHandler.post(new HandlerMessage<>(callableResponse, result));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
