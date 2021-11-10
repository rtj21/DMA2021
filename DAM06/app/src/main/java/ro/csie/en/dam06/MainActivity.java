package ro.csie.en.dam06;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String RECIPE_GET_JSON = "https://jsonkeeper.com/b/OCIE";
    private static final String RECIPE_POST_JSON = "https://ptsv2.com/t/MDA2021/post";
    private static final String TAG = MainActivity.class.getName();
    private Button btnGet;
    private Button btnPost;
    private TextView tvPostResult;
    private List<Recipe> recipes= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGet = findViewById(R.id.btnGet);
        btnPost = findViewById(R.id.btnPost);
        tvPostResult = findViewById(R.id.textView);
        recipes.add(new Recipe("Test1", "Test2", "Test3", 1, "Test4"));
    }

    public void getJson(View view)
    {
        Thread thread= new Thread()
        {
            @Override
            public void run() {
                HttpConnectionService httpConnectionService = new HttpConnectionService(RECIPE_GET_JSON);
                String recipeJSONArray = httpConnectionService.getData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //1. parse the json result (recipeJSONArray) to a list of objects of type Recipe
                        List<Recipe> result = RecipeJsonParser.fromJson(recipeJSONArray);
                        //2. add them to the collection
                        if(!recipes.containsAll(result))
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
    public void postJson(View view)
    {
        Thread thread= new Thread()
        {
            @Override
            public void run() {
                HttpConnectionService httpConnectionService = new HttpConnectionService(RECIPE_POST_JSON);
                String jsonArray = RecipeJsonParser.toJson(recipes);
                String value = httpConnectionService.postData(jsonArray);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvPostResult.setText(value);
                    }
                });
            }
        };
        thread.start();
    }
}