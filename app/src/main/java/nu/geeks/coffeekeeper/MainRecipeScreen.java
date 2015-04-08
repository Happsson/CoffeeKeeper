package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;


public class MainRecipeScreen extends Activity {

    private int CREATE_RECIPE = 1234;
    private Button bAddRecipe;
    private ListView listRecipes;
    private Spinner sSort;
    private ArrayList<String> sortOptions;
    private ArrayList<Dataholder> recipes;
    private ArrayAdapter<Dataholder> recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_screen);

        initializeView();



        initializeButton();

    }

    private void initializeButton() {

        bAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createRecipe();

            }
        });
    }

    private void createRecipe() {
        Intent intent = new Intent(this, CreateRecipe.class);
        startActivityForResult(intent, CREATE_RECIPE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == CREATE_RECIPE){
            if(resultCode == RESULT_OK){
                //Data recieved from CreateRecipe. Save it to recipies.
                Dataholder newRecipe = new Dataholder();
                String[] dataReceived = data.getStringArrayExtra("ReturnData");

                newRecipe.setName(dataReceived[0]);
                newRecipe.setComments(dataReceived[1]);
                newRecipe.setKindCoffe(dataReceived[2]);
                newRecipe.setAmountCoffe(Integer.parseInt(dataReceived[3]));
                newRecipe.setAmmountWater(Integer.parseInt(dataReceived[4]));
                newRecipe.setBrewTime(1, Integer.parseInt(dataReceived[5])); //TODO - this should be able to take more than one time.
                newRecipe.setGrind(Integer.parseInt(dataReceived[6]));
                newRecipe.setTemp(Integer.parseInt(dataReceived[7]));

                recipes.add(newRecipe);

                Toast.makeText(getApplicationContext(), "Recept sparat!", Toast.LENGTH_LONG).show();
            }
        }
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

        recipeAdapter = new ArrayAdapter<Dataholder>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1, recipes){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) findViewById(android.R.id.text1);
                TextView text2 = (TextView) findViewById(android.R.id.text2);

                text1.setText(recipes.get(position).getName());
                text2.setText(recipes.get(position).getComments());

                return view;
            }
        };

        listRecipes.setAdapter(recipeAdapter);

    }

    /**
     * Fill list with dummy recipes. "ABC", "BCD", "CDE", and so on.
     */
    private void fillWithExampleRecipes() {
        for(int i = 0; i < 15; i++){
            String name = "" + (char) (65+i) + (char) (66+i) + (char) (67+i);
            String comment = name;
            Dataholder recipe = new Dataholder(name);
            recipe.setComments(comment);
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
