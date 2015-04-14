package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class MainRecipeScreen extends Activity {

    private int CREATE_RECIPE = 1234;
    private Button bAddRecipe;
    private ListView listRecipes;
    private Spinner sSort;
    private ArrayList<String> sortOptions;
    private ArrayList<Recipe> recipes;
    private ArrayAdapter<Recipe> recipeAdapter;

    private boolean hasPlayedSplash = false;

    private final String savedRecipes = "saved_recipes";
    private final int splashRequestCode = 1;

    private final String[] SORTOPTIONS = {
            "Sortera efter namn",
            "Sortera efter bryggtid"
    };

    private static final String TAG = MainRecipeScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_screen);


        //Check if there are any saved instances of the recipe-list. If not, create a new empty
        //list.
        if(savedInstanceState == null){
            recipes = ((InAppData) this.getApplication()).getRecipes();
        }else{
            //If there are a list saved, retrieve it as a string and convert it to a recipelist.
            recipes = RecipeParser.readListString(savedInstanceState.getString(savedRecipes));

        }

        initializeView();
        initializeButton();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        //Save the current recipelist when app closes. Convert it to a String and save it.
        outState.putString(savedRecipes, RecipeParser.saveList(recipes));

        super.onSaveInstanceState(outState);
    }

    /**
     * This is the method that is called when the app comes back alive after when app i being
     * reopened.
     *
     * Stuff is initialized here instead of in onCreate for this reason.
     */
    @Override
    protected void onResume() {
        super.onResume();

        if(recipes == null){
            Toast.makeText(getApplicationContext(), "I've missunderstood :(", Toast.LENGTH_LONG).show();
        }

        //Start splash-screen
        if(!hasPlayedSplash) {
            Intent intent = new Intent(this, SplashScreen.class);
            startActivityForResult(intent, splashRequestCode);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
        recipeAdapter.notifyDataSetChanged(); //Make sure recipe-list is up to date
    }

    public String readSavedData ( ) {
        StringBuffer datax = new StringBuffer("");
        try {
            FileInputStream fIn = openFileInput(savedRecipes) ;
            InputStreamReader isr = new InputStreamReader ( fIn ) ;
            BufferedReader buffreader = new BufferedReader ( isr ) ;

            String readString = buffreader.readLine ( ) ;
            while ( readString != null ) {
                datax.append(readString);
                readString = buffreader.readLine ( ) ;
            }

            isr.close ( ) ;
        } catch ( IOException ioe ) {
            Toast.makeText(getApplicationContext(), "Can't read internal storage", Toast.LENGTH_LONG).show();
            ioe.printStackTrace ( ) ;
        }
        String returnString = datax.toString();
        if(returnString.length() != 0) return returnString;
        else return null;

    }

    private void initializeButton() {

        bAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createRecipe();
            }
        });

        sSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sortOptions.get(position).equals(SORTOPTIONS[0])){ //SORTOPTION[0] == "Sort by name"
                    recipeAdapter.sort(new RecipeNameComparator());
                    recipeAdapter.notifyDataSetChanged();
                }
                if(sortOptions.get(position).equals(SORTOPTIONS[1])){ // Sort by brewtime
                    recipeAdapter.sort(new RecipeTimeComparator());
                    recipeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openRecipe(position);
            }
        });
    }

    /**
     * Method that opens up a recipe. Converts selected recipe to string and sends it
     * as an extra with the intent when starting the recipe activity.
     *
     * @param index index of the recipe to load in main recipelist.
     */
    private void openRecipe(int index){

        Intent intent = new Intent(this, RecipeScreen.class);
        intent.putExtra("ReceivedRecipe", index);
        startActivity(intent);

    }

    private void createRecipe() {

        Intent intent = new Intent(this, CreateRecipe.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void initializeView(){
        bAddRecipe = (Button) findViewById(R.id.bNewRecipe);
        listRecipes = (ListView) findViewById(R.id.listRecipes);
        sSort = (Spinner) findViewById(R.id.sSort);

        sortOptions = new ArrayList<String>();

        //Add sortoptions
        for(int i = 0; i < SORTOPTIONS.length; i++){
            sortOptions.add(SORTOPTIONS[i]);
        }

        //Create and populate sorting dropdown menu

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortOptions);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSort.setAdapter(sortAdapter);

        recipeAdapter = new ArrayAdapter<Recipe>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1, recipes){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(recipes.get(position).getName());
                text2.setText(recipes.get(position).getComments());


                return view;
            }
        };


        listRecipes.setAdapter(recipeAdapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == splashRequestCode){
            hasPlayedSplash = true;
        }
    }


    @Override
    protected void onStop() {
        saveData();
        super.onStop();
    }

    private void saveData() {
        //Save recipe list
        String recipeString = RecipeParser.saveList(recipes);

        try {
            FileOutputStream fos = openFileOutput(savedRecipes, Context.MODE_PRIVATE);
            fos.write(recipeString.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Can't find file", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Can't write to file", Toast.LENGTH_LONG).show();
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

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
