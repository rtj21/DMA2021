package ro.csie.en.dam05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HeavyLoadImgProcessing {
    int duration;

    public HeavyLoadImgProcessing(int duration) {
        this.duration = duration;
    }

    public Bitmap process(Context context)
    {
        Bitmap result = null;
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = BitmapFactory.decodeResource(context.getResources(), R.drawable.mouse);
        return result;
    }
}
