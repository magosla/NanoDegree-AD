package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            Sandwich sandwich = new Sandwich();
            JSONObject swObject = new JSONObject(json);
            JSONObject nameObject = swObject.getJSONObject("name");

            ArrayList<String> alsoKnowAs = new ArrayList<>(), ingredients = new ArrayList<>();

            JSONArray aksJsonArray = nameObject.getJSONArray("alsoKnownAs");
            for(int i=0; i< aksJsonArray.length(); i++){
                alsoKnowAs.add(aksJsonArray.getString(i));
            }
            JSONArray  ingJsonArray = swObject.getJSONArray("ingredients");
            for(int i=0; i< ingJsonArray.length(); i++){
                ingredients.add(ingJsonArray.getString(i));
            }

            sandwich.setMainName(nameObject.getString("mainName"));
            sandwich.setPlaceOfOrigin(swObject.getString("placeOfOrigin"));
            sandwich.setDescription(swObject.getString("description"));
            sandwich.setImage(swObject.getString("image"));
            sandwich.setIngredients(ingredients);
            sandwich.setAlsoKnownAs(alsoKnowAs);

            return sandwich;
        }catch (JSONException ignored){

        }
        return null;
    }
}
