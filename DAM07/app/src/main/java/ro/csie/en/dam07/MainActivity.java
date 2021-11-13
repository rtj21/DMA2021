package ro.csie.en.dam07;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import retrofit.RetrofitError;
//import retrofit.client.Response;

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
        Call<ObjectCollection> dataObjects = dataService.getDataObjects();
        dataObjects.enqueue(new Callback<ObjectCollection>() {
            @Override
            public void onResponse(Call<ObjectCollection> call, Response<ObjectCollection> response) {
                Log.d(TAG, "Response code: " + response.code());
                if(response.code() == 200) {
                    ObjectCollection objectCollection = response.body();
                    for (DataObject dataObject : objectCollection.valori) {
                        String value = dataObject.getValue();
                        Log.d(TAG, "Value: " + value);
                    }
                }
            }

            @Override
            public void onFailure(Call<ObjectCollection> call, Throwable t) {
                Log.d(TAG, "Error: " + t.getMessage());
            }
        });

//        dataService.getDataObjects(new Callback<ObjectCollection>() {
//            @Override
//            public void success(ObjectCollection objectCollection, Response response) {
//                Log.d(TAG, "Response code: " + response.getStatus());
//                for (DataObject dataObject : objectCollection.valori) {
//                    String value = dataObject.getValue();
//                    Log.d(TAG, "Value: " + value);
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                    Log.d(TAG, "Error: " + error.getMessage());
//            }
//        });
    }
}