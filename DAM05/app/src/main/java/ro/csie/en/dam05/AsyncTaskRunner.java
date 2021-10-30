package ro.csie.en.dam05;

import android.os.Handler;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncTaskRunner {

    private Executor executor = Executors.newCachedThreadPool();

    public <R> void executeAsyncTask(Handler mtHandler, Callable<R> asyncTask, Callback<R> bitmapCallback) {
        executor.execute(new RunnableAsyncTask<>(mtHandler, asyncTask, bitmapCallback));
    }
}
