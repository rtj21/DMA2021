package ro.csie.en.dam10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_MOVIE_REQUEST_CODE = 100;
    private static final int UPDATE_MOVIE_REQUEST_CODE = 200;
    private ListView lvMovies;
    private FloatingActionButton fabAddMovie;
    private List<Movie> movieList = new ArrayList<>();

    private DatabaseManager databaseManager;
    private MovieDao movieDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvMovies = findViewById(R.id.lvMovie);
        fabAddMovie = findViewById(R.id.floatingActionButton);
        ArrayAdapter<String> movieAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movieList);
        lvMovies.setAdapter(movieAdapter);
        fabAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMovieActivity.class);
                startActivityForResult(intent, ADD_MOVIE_REQUEST_CODE);
            }
        });
        databaseManager = DatabaseManager.getInstance(this);
        movieDao = databaseManager.getMovieDao();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null)
        {
            Movie movie = (Movie) data.getSerializableExtra(AddMovieActivity.MOVIE_KEY);
            if(requestCode == ADD_MOVIE_REQUEST_CODE)
            {
                //where we insert movie in the db
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        long id = movieDao.insert(movie);
                        movie.setMovieId(id);
                        movieList.add(movie);
                        synchronized (lvMovies.getAdapter())
                        {
                            lvMovies.getAdapter().notify();
                        }

                    }
                });

            }
        }

    }
}