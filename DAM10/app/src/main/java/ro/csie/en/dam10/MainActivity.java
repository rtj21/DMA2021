package ro.csie.en.dam10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
        databaseManager = DatabaseManager.getInstance(this);
        movieDao = databaseManager.getMovieDao();
        lvMovies = findViewById(R.id.lvMovie);
        fabAddMovie = findViewById(R.id.floatingActionButton);
        setListViewAdapter();
        fabAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMovieActivity.class);
                startActivityForResult(intent, ADD_MOVIE_REQUEST_CODE);
            }
        });
    }

    private void setListViewAdapter() {
        List<Movie> all = movieDao.getAll();
        movieList.addAll(all);
        ArrayAdapter<String> movieAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movieList);
        lvMovies.setAdapter(movieAdapter);
        lvMovies.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.setBackgroundColor(Color.GREEN);
                writeMovie(movieList.get(position));
                return true;
            }
        });
    }

    private void writeMovie(Movie movie) {
        try {
            FileOutputStream fos = openFileOutput("movies.bin", MODE_APPEND);
            fos.write((int) movie.getMovieId());
            fos.write(movie.getMovieTitle().getBytes(StandardCharsets.UTF_8));
            fos.write(movie.getMovieGenre().getBytes(StandardCharsets.UTF_8));
            fos.write(doubleToByteArray(movie.getMovieBudget()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] doubleToByteArray(Double movieBudget) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        dataOutputStream.writeDouble(movieBudget);
        dataOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
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