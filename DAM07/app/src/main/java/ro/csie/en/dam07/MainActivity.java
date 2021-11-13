package ro.csie.en.dam07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getData(View view)
    {
        DataService dataService = new RestApiAdapter().getData();
        dataService.getDataObjects(new Callback<ObjectCollection>() {
            @Override
            public void success(ObjectCollection objectCollection, Response response) {
                Log.d(TAG, "Response code: " + response.getStatus());
                for (DataObject dataObject : objectCollection.valori) {
                    String value = dataObject.getValue();
                    Log.d(TAG, "Value: " + value);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
            }
        });
    }
}