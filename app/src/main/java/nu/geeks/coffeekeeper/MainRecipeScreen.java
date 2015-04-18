package nu.geeks.coffeekeeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

    //used to detect swipe on listItem
    float xUp;
    float xPush;

    boolean deleteRecipe = false;

    private boolean hasPlayedSplash = false;

    // private final String savedRecipes = "saved_recipes";
    private final int splashRequestCode = 1;
    private final int recipeRequestCode = 2;

    private final String[] SORTOPTIONS = {
            "Sortera efter namn",
            "Sortera efter bryggtid"
    };

    private static final String TAG = MainRecipeScreen.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recipe_screen);

        if(!readSavedData()) {
            recipes = new ArrayList<Recipe>();
            ((InAppData) this.getApplication()).setRecipes(recipes);
        }

        initializeView();
        initializeButton();
    }


    /**
     * This is the method that is called when the app comes back alive after when app i being
     * reopened.
     * <p/>
     * Stuff is initialized here instead of in onCreate for this reason.
     */
    @Override
    protected void onResume() {
        super.onResume();

        //Start splash-screen
        if (!hasPlayedSplash) {
            Intent intent = new Intent(this, SplashScreen.class);
            startActivityForResult(intent, splashRequestCode);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        recipeAdapter.notifyDataSetChanged(); //Make sure recipe-list is up to date
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
                if (sortOptions.get(position).equals(SORTOPTIONS[0])) { //SORTOPTION[0] == "Sort by name"
                    recipeAdapter.sort(new RecipeNameComparator());
                    recipeAdapter.notifyDataSetChanged();
                }
                if (sortOptions.get(position).equals(SORTOPTIONS[1])) { // Sort by brewtime
                    recipeAdapter.sort(new RecipeTimeComparator());
                    recipeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /**Touch is called before click.
         * For this reason, it is possible to check for a swipe to the right here,
         * and if detected, deleteRecipe can be set to true before
         * the click of a recipe is registered.
         *
         */

        listRecipes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    xPush = event.getRawX();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    xUp = event.getRawX();
                    checkMovement();
                }

                return false;
            }
        });

        /**
         *
         */
        listRecipes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if(!deleteRecipe){
                    openRecipe(position);
                }
                else{
                    new AlertDialog.Builder(MainRecipeScreen.this)
                            .setTitle("Ta bort?")
                            .setMessage("Vill du ta bort receptet " + recipes.get(position).name +" från listan?")
                            .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    recipes.remove(position);
                                    recipeAdapter.notifyDataSetChanged();
                                    saveData();
                                }
                            })
                            .setNegativeButton("Nej", null)
                            .show();
                deleteRecipe = false;
                }
            }
        });
    }

    /**
     * Is called when the user has touched the listView.
     * Will set deleteRecipe to true if user swiped right
     */
    private void checkMovement() {
        if(xUp-xPush>100) deleteRecipe = true;
        else deleteRecipe = false;
    }

    /**
     * Method that opens up a recipe. Converts selected recipe to string and sends it
     * as an extra with the intent when starting the recipe activity.
     *
     * @param index index of the recipe to load in main recipelist.
     */
    private void openRecipe(int index) {

        Intent intent = new Intent(this, RecipeScreen.class);
        intent.putExtra("ReceivedRecipe", index);
        startActivity(intent);

    }

    private void createRecipe() {

        Intent intent = new Intent(this, CreateRecipe.class);
        startActivityForResult(intent, recipeRequestCode);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void initializeView() {
        bAddRecipe = (Button) findViewById(R.id.bNewRecipe);
        listRecipes = (ListView) findViewById(R.id.listRecipes);
        sSort = (Spinner) findViewById(R.id.sSort);

        sortOptions = new ArrayList<String>();

        //Add sortoptions
        for (int i = 0; i < SORTOPTIONS.length; i++) {
            sortOptions.add(SORTOPTIONS[i]);
        }

        //Create and populate sorting dropdown menu

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sortOptions);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sSort.setAdapter(sortAdapter);

        recipeAdapter = new ArrayAdapter<Recipe>(this, android.R.layout.simple_list_item_2,
                android.R.id.text1, recipes) {

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
        if (requestCode == splashRequestCode) {
            hasPlayedSplash = true;
        }
        if (requestCode == recipeRequestCode) {
           recipes = ((InAppData) this.getApplication()).getRecipes();
           recipeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }


    /**
     * Saves the recipe list on the internal storage of the phone.
     * If there are no recipes (for example, user has deleted all recipes
     * that was there from before), the file will be deleted to make sure
     * that the readSavedData() get's a null file when trying to read.
     * That's one of the ways it tells that there are no current recipes, and
     * a new recipelist needs to be created.
     *
     */
    public void saveData() {
        //Convert recipe to string with RecipeParser.
        String recipeString = RecipeParser.listToString(recipes);

        try {
            //Create a file coffee.txt in the root of the internal storage for this app.

            File gpxfile = new File(getApplicationContext().getFilesDir(), "coffee.txt");
            if(recipeString == null){
                gpxfile.delete();
            }else {

                FileWriter writer = new FileWriter(gpxfile, false); //False is weather or not to append to file. Delete it! KILL IT WITH FIRE!
                //Write recipe string to the file. Close and flush outputstream.

                writer.append(recipeString);
                writer.flush();
                writer.close();
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error writing to file", Toast
                    .LENGTH_LONG).show();
        }
    }

    /**
     * Reads the saved data from internal storage. Returns true if the read was successful.
     * Successful in this instance means both that it was able to find and read the file,
     * and the file contained at least one recipe.
     *
     * Returns false otherwise
     *
     * @return
     */
    public boolean readSavedData() {
        String inputString;
        try {
            BufferedReader inputReader = new BufferedReader(new InputStreamReader(
                    openFileInput("coffee.txt")
            ));
            inputString = inputReader.readLine();

        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return false; //False, file not found.
        } catch (IOException e) {
            e.printStackTrace(); //False, couldn't read file
            return false;
        }

        if(inputString != null) recipes = RecipeParser.readListString(inputString);
        else return false; //False, file found and readable, but empty.
        ((InAppData) this.getApplication()).setRecipes(recipes);
        return true;
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
