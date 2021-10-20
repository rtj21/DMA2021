package en.ase.csie.dam02;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String MAIN_ACTIVITY_TAG = MainActivity.class.getCanonicalName();
    private EditText fullName;
    private Button sendData;

    private ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK)
                    {
                        Intent data = result.getData();
                        int result1 = data.getIntExtra("result", 0);
                        Log.d(MAIN_ACTIVITY_TAG,"Value is: " + result1);
                        Toast.makeText(getApplicationContext(), "Value is: " + result1, Toast.LENGTH_LONG).show();
                    }
                }
    });

    String[] arr = { "Paries,France", "PA,United States","Parana,Brazil",
            "Padua,Italy", "Pasadena,CA,United States"};
    DatePickerDialog picker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fullName = findViewById(R.id.etFullName);
        sendData = findViewById(R.id.btnSend);
        fullName.setOnClickListener(this);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        Spinner spinner = findViewById(R.id.spinner);
        ListView listView =findViewById(R.id.listView);
        EditText eText = findViewById(R.id.eText);
        eText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(MainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = fullName.getText().toString();
                Log.d("MainActivity", "Click from button view: " + value);
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("param1", 78);
                intent.putExtra("param2", 12);
                //startActivity(intent);
                activityResultLauncher.launch(intent);
            }
        });

        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arr);
        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(adapter);
        spinner.setAdapter(adapter);
        listView.setAdapter(adapter);
    }

    public void btnClick(View view)
    {
        String value = fullName.getText().toString();
        Log.d("MainActivity", "Input values is: " + value);
    }

    @Override
    public void onClick(View view) {
        String value = fullName.getText().toString();
        Log.d("MainActivity", "Click from general view (EditText): " + value);
        ViewStub importPanel = findViewById(R.id.viewStub);
        if(importPanel!= null)
            importPanel.inflate();
    }
}