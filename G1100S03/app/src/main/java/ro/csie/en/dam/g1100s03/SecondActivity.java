package ro.csie.en.dam.g1100s03;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        Student student = intent.getParcelableExtra("param1");
        Toast.makeText(this,student.toString(),Toast.LENGTH_LONG).show();
    }
}