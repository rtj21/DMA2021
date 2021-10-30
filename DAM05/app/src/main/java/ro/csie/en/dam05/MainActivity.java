package ro.csie.en.dam05;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    ImageView imageView;
    AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }

    public void getImage(View view)
    {
//        getImageThreadImpl(3000);
        getImageCallableImpl(3000);
    }

    private void getImageCallableImpl(int duration) {
        Handler mtHandler = new Handler();
        Callable<Bitmap> asyncTask = new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                Bitmap result = null;
                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                result = BitmapFactory.decodeResource(MainActivity.this.getResources(), R.drawable.mouse);
                return result;
            }
        };
        Callback<Bitmap> bitmapCallback= result -> {
            Log.d(TAG, "UI Thread accessed from the entire async stack");
            imageView.setImageBitmap(result);
        };

        asyncTaskRunner.executeAsyncTask(mtHandler, asyncTask, bitmapCallback);

    }

    private void getImageThreadImpl(int duration) {
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                Bitmap bitmap = new HeavyLoadImgProcessing(duration).process(MainActivity.this);
//                imageView.setImageBitmap(bitmap); doesn't work on a secondary thread
                //1. accessing the main UI thread controls with runOnUiThread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "UI Thread accessed from runOnUiThread");
                        imageView.setImageBitmap(bitmap);
                    }
                });
                //2. accessing the main UI thread controls with a Handler
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "UI Thread accessed from its Handler and posting a Runnable message");
                        imageView.setImageBitmap(bitmap);
                    }
                });

            }
        };
        thread.start();
    }
}