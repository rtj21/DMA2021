package ro.csie.en.dam09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    Button btnOpenSharedPrefs;
    EditText etUserName;
    SharedPreferences sharedPreferences;
    SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            String value = sharedPreferences.getString(key,"Default");
            Log.d(TAG,"Value for key: " + key + " was changed with value: " + value);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOpenSharedPrefs = findViewById(R.id.button2);
        btnOpenSharedPrefs.setOnClickListener(this);
        etUserName = findViewById(R.id.editTextTextPersonName);
        sharedPreferences = this.getSharedPreferences("custom_prefs", MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }

    public void writeCustomSharedPrefs(View view)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", etUserName.getText().toString());
        editor.commit();

        String value = sharedPreferences.getString("username", "Default");
        Log.d(TAG, "Value: " + value);

        SharedPreferences general = PreferenceManager.getDefaultSharedPreferences(this);
        String signature = general.getString("signature", "Default");
        Log.d(TAG, "Signature: " + signature);

        FileOutputStream fos = null;
        try {
            fos = this.openFileOutput("text.txt",MODE_PRIVATE);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            osw.write(signature);
            osw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }
}