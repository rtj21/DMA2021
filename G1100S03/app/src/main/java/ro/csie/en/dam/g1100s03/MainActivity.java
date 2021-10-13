package ro.csie.en.dam.g1100s03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AlertDialog.Builder alertDialog;
    private static final String TAG = MainActivity.class.getName();
    private EditText etName;
    private EditText etEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etName = findViewById(R.id.editTextTextPersonName);
        etEmail = findViewById(R.id.editTextTextEmailAddress);
        Log.d(TAG, "onCreate");
    }

    public void sendData(View view)
    {
        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure you want to save the object?")
                .setTitle("Save Student")
                .setCancelable(false)
                .setPositiveButton("YES", (dialog, which) -> {
                    String name = etName.getText().toString();
                    String email = etEmail.getText().toString();
                    Student student = new Student(name, email);
                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                    Bundle data = new Bundle();
                    data.putParcelable("param1", student);
                    intent.putExtras(data);
                    startActivity(intent);
                })
                .setNegativeButton("NO", (dialog, which) -> {
                    dialog.cancel();
                    Toast.makeText(getApplicationContext(), "Save Student action was canceled", Toast.LENGTH_LONG).show();
                });
        AlertDialog alert = alertDialog.create();
        alert.show();

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }
}