package ro.csie.en.dam.dam03;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {


    String[] arguments = {"Paris, France", "PA, United States", "Parana, Brazil", "Pasadena, CA, USA"};


    AutoCompleteTextView autoCompleteTextView;
    Spinner spinner;
    ListView listView;
    EditText editTextDate;

    private ArrayAdapter<String> stringAdapter;
    private ArrayAdapter<String> spinerStringAdapter;

    DatePickerDialog datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        stringAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, arguments);
        spinerStringAdapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, arguments);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(stringAdapter);

        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(stringAdapter);

        listView = findViewById(R.id.listView);
        listView.setAdapter(stringAdapter);

        editTextDate = findViewById(R.id.editTextDate);
        editTextDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    editTextDate.setText(day + "/" + (month +1) +"/" + year);
                }
            }, year, month, day);
            datePicker.show();
        });
    }

    public void sendData(View view)
    {
        Toast.makeText(this, R.string.btn_send_msg, Toast.LENGTH_LONG).show();
    }
}