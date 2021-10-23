package ro.csie.en.mda04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    ListView listView;
    String[] stringValues = new String[]{"Paris", "London", "Bucharest", "Washington", "Rome", "Berlin", "Moscow", "Dublin", "Prague", "Amsterdam", "Brussels", "Edinburgh"};
    List<City> cities = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lvCities);

        cities.add(new City(100, "Paris", 233455550));
        cities.add(new City(101, "London", 53455550));
        cities.add(new City(102, "Bucharest", 785550));
        cities.add(new City(103, "Washington", 22155550));
        cities.add(new City(104, "Rome", 412455550));
        cities.add(new City(105, "Berlin", 764355550));
        cities.add(new City(106, "Moscow", 54355550));
        cities.add(new City(107, "Dublin", 5655550));
        cities.add(new City(108, "Prague", 1235550));
        cities.add(new City(109, "Amsterdam", 76555550));
        cities.add(new City(110, "Brussels", 12455550));
        cities.add(new City(111, "Edinburgh", 3455550));

        CityAdapter cityAdapter = new CityAdapter(this, cities);

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

        listView.setAdapter(cityAdapter);

    }
}