package ro.csie.en.dam06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String RECIPE_JSON = "https://jsonkeeper.com/b/OCIE";
    private static final String TAG = MainActivity.class.getName();
    private Button button;
    private List<Recipe> recipes= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
    }

    public void getJson(View view)
    {
        Thread thread= new Thread()
        {
            @Override
            public void run() {
                HttpConnectionService httpConnectionService = new HttpConnectionService(RECIPE_JSON);
                String recipeJSONArray = httpConnectionService.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //1. parse the json result (recipeJSONArray) to a list of objects of type Recipe
                        List<Recipe> result = RecipeJsonParser.fromJson(recipeJSONArray);
                        //2. add them to the collection
                        recipes.addAll(result);
                        for (Recipe recipe: recipes) {
                            Log.d(TAG, "Recipe: " + recipe);
                        }
                    }
                });
            }
        };
        thread.start();
    }
}