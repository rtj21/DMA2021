package en.ase.csie.dam02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends AppCompatActivity {

    int result = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int param1 = extras.getInt("param1");
        int param2 = extras.getInt("param2");
        result = param1+param2;
    }

    public void sendResultClick(View view)
    {
        Intent finishIntent = new Intent();
        finishIntent.putExtra("result", result);
        setResult(RESULT_OK, finishIntent);
        finish();
    }
}