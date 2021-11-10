package ro.csie.en.dam06;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeJsonParser {

    public static List<Recipe> fromJson(String recipeJSONArray) {
        List<Recipe> result = null;
        try {
            JSONArray array = new JSONArray(recipeJSONArray);
            result = new ArrayList<>();
            for (int index = 0; index < array.length(); index++) {
                Recipe recipe = readRecipe(array.getJSONObject(index));
                result.add(recipe);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static Recipe readRecipe(JSONObject jsonObject) throws JSONException {
        String denumire = jsonObject.getString("denumire");
        String descriere = jsonObject.getString("descriere");
        String dataAdaugarii = jsonObject.getString("dataAdaugarii");
        int calorii = jsonObject.getInt("calorii");
        String categorie = jsonObject.getString("categorie");
        Recipe recipe = new Recipe(denumire,descriere,dataAdaugarii, calorii, categorie);
        return recipe;
    }

    public static String toJson(List<Recipe> recipes) {
        JSONArray array = new JSONArray();
        try {
            for (Recipe recipe:recipes) {
                JSONObject jsonObject = writeRecipe(recipe);
                array.put(jsonObject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array.toString();
    }
    private static JSONObject writeRecipe(Recipe recipe) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("denumire", recipe.getDenumire());
        jsonObject.put("descriere", recipe.getDescriere());
        jsonObject.put("dataAdaugarii", recipe.getDataAdaugarii());
        jsonObject.put("calorii", recipe.getCalorii());
        jsonObject.put("categorie", recipe.getCategorie());
        return jsonObject;
    }
}
