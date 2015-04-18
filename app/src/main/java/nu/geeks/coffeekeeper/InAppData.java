package nu.geeks.coffeekeeper;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Hannes on 2015-04-14.
 */
public class InAppData extends Application{

    ArrayList<Recipe> recipes = new ArrayList<Recipe>();

    public ArrayList<Recipe> getRecipes(){
        return recipes;
    }

    public void addRecipe(Recipe recipe){
        recipes.add(recipe);
    }

    public void setRecipes(ArrayList<Recipe> recipes){
        this.recipes = recipes;
    }
}
