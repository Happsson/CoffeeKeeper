package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MainRecipeScreen extends Activity {

    private Button bAddRecipe;
    private ListView listRecipes;
    private Spinner sSort;
    private ArrayList<String> sortOptions;
    private ArrayList<Dataholder> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_screen);

        initializeView();

    }

    private void initializeView(){
        bAddRecipe = (Button) findViewById(R.id.bNewRecipe);
        listRecipes = (ListView) findViewById(R.id.listRecipes);
        sSort = (Spinner) findViewById(R.id.sSort);
        recipes = new ArrayList<Dataholder>();

        fillWithExampleRecipes();

        sortOptions = new ArrayList<String>();
        sortOptions.add("Sort by name");
        sortOptions.add("Sort by brew time");
        sortOptions.add("sort by coffee bean");
        sortOptions.add("sort by favourites");

        //Create and populate sorting dropdown menu
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortOptions);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSort.setAdapter(sortAdapter);

        ArrayAdapter<Dataholder> recipeAdapter = new ArrayAdapter<Dataholder>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1, recipes){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) findViewById(android.R.id.text1);
                TextView text2 = (TextView) findViewById(android.R.id.text2);

                //text1.setText(recipes.get(position).getName());
                //text2.setText(recipes.get(position).getComment());

                return super.getView(position, convertView, parent);
            }
        };

    }

    /**
     * Fill list with dummy recipes. "ABC", "BCD", "CDE", and so on.
     */
    private void fillWithExampleRecipes() {
        for(int i = 0; i < 15; i++){
            String name = "" + (char) (65+i) + (char) (66+i) + (char) (67+i);
            String comment = name;
            Dataholder recipe = new Dataholder(name);
           // recipe.setComment(comment);
            recipes.add(recipe);
        }
    }


    //TODO - add menu buttons
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_recipe_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
