package ro.csie.en.dam05;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
    }

    public void getImage(View view)
    {
        getImageThreadImpl(3000);
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