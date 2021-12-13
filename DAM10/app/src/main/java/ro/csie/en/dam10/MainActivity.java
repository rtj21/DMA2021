package ro.csie.en.dam10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_MOVIE_REQUEST_CODE = 100;
    private static final int UPDATE_MOVIE_REQUEST_CODE = 200;
    private static final String TAG = MainActivity.class.getName();
    private ListView lvMovies;
    private FloatingActionButton fabAddMovie;
    private List<Movie> movieList = new ArrayList<>();

    private DatabaseManager databaseManager;
    private MovieDao movieDao;
    private List<Integer> writtenMovies = new ArrayList<>();
    private Map<String, Integer> movieStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseManager = DatabaseManager.getInstance(this);
        movieDao = databaseManager.getMovieDao();
        lvMovies = findViewById(R.id.lvMovie);
        fabAddMovie = findViewById(R.id.floatingActionButton);
        List<Movie> all = movieDao.getAll();
        movieList.addAll(all);
        try {
            FileInputStream fis = openFileInput("movies.bin");
            int id;
            while((id = fis.read()) != -1)
            {
                int bytes = 0;
                for (Movie movie : movieList) {
                    if (movie.getMovieId() == id) {
                        int position = movieList.indexOf(movie);
                        writtenMovies.add(position);
                        bytes += movie.getMovieTitle().length() + movie.getMovieGenre().length() + 8;
                        Log.d(TAG, "id: " + id + ", position: "+position + ", bytes: " + bytes);
                        break;
                    }
                }
                fis.skip(bytes);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setListViewAdapter();
        fabAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = createCredentialsDialog();
                alertDialog.show();
            }
        });

        //graphics example
        movieStats = computeMovieList(movieList);
        setContentView(new MovieChart(this, movieStats));

    }

    private AlertDialog createCredentialsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_signin, null)).setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.settings:
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

   

    private Map<String, Integer> computeMovieList(List<Movie> movieList) {
        if(movieList == null || movieList.isEmpty())
            return null;
        Map<String, Integer> data = new HashMap<>();
        for(Movie movie: movieList)
        {
            if(data.containsKey(movie.getMovieGenre()))
            {
                Integer currValue = data.get(movie.getMovieGenre());
                data.put(movie.getMovieGenre(), currValue+1);
            }
            else
                data.put(movie.getMovieGenre(), 1);
        }
        return data;
    }

    private void setListViewAdapter() {

        ArrayAdapter<String> movieAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movieList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                if(writtenMovies.contains(position)) {
                    Log.d(TAG, "Item getview: " + position);
                    view.setBackgroundColor(Color.GREEN);
                }
                return view;

            }
        };
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