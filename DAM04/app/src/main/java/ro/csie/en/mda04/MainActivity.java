package ro.csie.en.mda04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    ListView listView;
    String[] stringValues = new String[]{"Paris", "London", "Bucharest", "Washington", "Rome", "Berlin", "Moscow", "Dublin", "Prague", "Amsterdam", "Brussels", "Edinburgh"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lvCities);

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, stringValues);
        listView.setAdapter(stringArrayAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String value = stringValues[position];
            TextView viewById = view.findViewById(android.R.id.text1);
            Log.d(TAG, "Clicked Item: " + value + ", from view: " + viewById.getText().toString());
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View layout, int position, long id) {
                String value = stringValues[position];
                TextView viewById = layout.findViewById(android.R.id.text1);
                Log.d(TAG, "Long Clicked Item: " + value + ", from view: " + viewById.getText().toString());
                return true;
            }
        });

    }
}