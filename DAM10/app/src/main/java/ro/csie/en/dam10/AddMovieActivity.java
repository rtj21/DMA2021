package ro.csie.en.dam10;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class AddMovieActivity extends AppCompatActivity {

    public static final String MOVIE_KEY = "movie_key";

    private EditText etMovieTitle;
    private EditText etMovieBudget;
    private Spinner spMovieGenre;
    private Button btnSave;
    private View.OnClickListener onSaveButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String title = etMovieTitle.getText().toString();
            Double budget = Double.parseDouble(etMovieBudget.getText().toString());
            String genre = spMovieGenre.getSelectedItem().toString();
            Movie movie = new Movie(title, genre, budget);
            Intent intent = new Intent();
            intent.putExtra(MOVIE_KEY, movie);
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        etMovieTitle = findViewById(R.id.etMovieTitle);
        etMovieBudget = findViewById(R.id.etMovieBudget);
        spMovieGenre = findViewById(R.id.spMovieGenre);
        setMovieGenreValue();
        btnSave = findViewById(R.id.button);
        btnSave.setOnClickListener(onSaveButtonClick);
    }

    private void setMovieGenreValue() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.movie_genre_value, android.R.layout.simple_spinner_dropdown_item);
        spMovieGenre.setAdapter(adapter);
    }
}