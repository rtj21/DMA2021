package ro.csie.en.dam05;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

public class AsyncTask extends android.os.AsyncTask<Integer, Integer, Bitmap> {

    public interface MyAsyncTaskListener
    {
        void onPostExecuteListener(Bitmap result);
        void onPublishProgressListener(Integer integer);
    }
    private MyAsyncTaskListener asyncTaskListener;

    public void setAsyncTaskListener(MyAsyncTaskListener asyncTaskListener) {
        this.asyncTaskListener = asyncTaskListener;
    }

    private static final String TAG = AsyncTask.class.getName();
    private Context context;

    public AsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if(asyncTaskListener != null)
            asyncTaskListener.onPublishProgressListener(values[0]);
    }

    @Override
    protected Bitmap doInBackground(Integer... integers) {
        Bitmap result = null;
        try {
            int duration = integers[0]/1000;
            for(int i=0; i<duration; i++) {
                Thread.sleep(1000);
                publishProgress(i+1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        result = BitmapFactory.decodeResource(context.getResources(), R.drawable.mouse);
        return result;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        if(asyncTaskListener != null)
            asyncTaskListener.onPostExecuteListener(result);
    }
}
